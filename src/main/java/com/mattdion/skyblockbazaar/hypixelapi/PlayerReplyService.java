package com.mattdion.skyblockbazaar.hypixelapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mattdion.skyblockbazaar.Utils;
import com.mattdion.skyblockbazaar.apiutils.HypixelAPIUtil;
import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import com.mattdion.skyblockbazaar.minions.MinionID;
import com.mattdion.skyblockbazaar.player.Player;
import net.hypixel.api.reply.skyblock.SkyBlockProfileReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlayerReplyService {
    private static final String PLAYER_NOT_FOUND_HYPIXEL = "No player found in Hypixel API database";
    private final HypixelAPIUtil hypixelAPIUtil;

    @Autowired
    public PlayerReplyService(HypixelAPIUtil hypixelAPIUtil) {
        this.hypixelAPIUtil = hypixelAPIUtil;
    }

    public SkyBlockProfileReply getSkyblockProfileReply(Player player) {
        // for some reason Hypixel API library expects UUID without dashes, so here we go
        String fixedUuid = Utils.deleteDashes(player.getId().toString());

        return hypixelAPIUtil.getSkyblockProfileReply(fixedUuid);
    }

    public JsonArray getCraftedGenerators(Player player) throws NoPlayerFoundException {
        SkyBlockProfileReply skyBlockProfileReply = getSkyblockProfileReply(player);
        JsonObject profile = skyBlockProfileReply.getProfile();
        if (profile == null) throw new NoPlayerFoundException(PLAYER_NOT_FOUND_HYPIXEL);

        JsonArray craftedGenerators = profile
                .getAsJsonObject("members")
                .getAsJsonObject(Utils.deleteDashes(player.getId().toString()))
                .getAsJsonArray("crafted_generators");

        if (craftedGenerators == null) throw new NoPlayerFoundException(PLAYER_NOT_FOUND_HYPIXEL);
        else return craftedGenerators;
    }

    public Map<MinionID, Integer> getMinionLevelsMap(Player player) throws NoPlayerFoundException {
        JsonArray craftedGenerators = getCraftedGenerators(player);

        MinionLevels minionLevels = new MinionLevels();
        minionLevels.initializeWithCraftedGenerators(craftedGenerators);

        return minionLevels;
    }

    private class MinionLevels extends HashMap<MinionID, Integer> {
        // initialize Map with level 0 Minions
        private MinionLevels() {
            for (MinionID minionID : MinionID.values()) {
                put(minionID, 0);
            }
        }

        private void initializeWithCraftedGenerators(JsonArray craftedGenerators) {
            for (int i = 0; i < craftedGenerators.size(); ++i) {
                // get one element from craftedGenerators array
                JsonElement element = craftedGenerators.get(i);
                String generator = element.getAsString();
                int delimiter = generator.lastIndexOf("_");

                // parse Minion name and level
                String generatorName = generator.substring(0, delimiter);
                int generatorLevel = Integer.parseInt(generator.substring(delimiter + 1));

                // if there's no such minion in MinionID database, skip
                MinionID minionID = MinionID.getMinionId(generatorName);
                if (minionID == null) continue;

                // if Minion level is higher than existing, update it
                int actualGeneratorLevel = get(minionID);
                if (generatorLevel > actualGeneratorLevel) {
                    put(minionID, generatorLevel);
                }
            }
        }
    }
}
