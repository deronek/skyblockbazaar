package com.mattdion.skyblockbazaar.hypixelapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mattdion.skyblockbazaar.Utils;
import com.mattdion.skyblockbazaar.apiutils.HypixelAPIUtil;
import com.mattdion.skyblockbazaar.minions.MinionID;
import com.mattdion.skyblockbazaar.player.Player;
import net.hypixel.api.reply.skyblock.SkyBlockProfileReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlayerReplyService {
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

    public JsonArray getCraftedGenerators(Player player) {
        SkyBlockProfileReply skyBlockProfileReply = getSkyblockProfileReply(player);
        return skyBlockProfileReply
                .getProfile()
                .getAsJsonObject("members")
                .getAsJsonObject(Utils.deleteDashes(player.getId().toString()))
                .getAsJsonArray("crafted_generators");
    }

    public Map<MinionID, Integer> getMinionLevelsMap(Player player) {
        JsonArray craftedGenerators = getCraftedGenerators(player);

        MinionLevels minionLevels = new MinionLevels();
        minionLevels.initializeWithCraftedGenerators(craftedGenerators);

        return minionLevels;
    }

    private class MinionLevels extends HashMap<MinionID, Integer> {
        private MinionLevels() {
            for (MinionID minionID : MinionID.values()) {
                put(minionID, 0);
            }
        }

        private void initializeWithCraftedGenerators(JsonArray craftedGenerators) {
            for (int i = 0; i < craftedGenerators.size(); ++i) {
                JsonElement element = craftedGenerators.get(i);
                String generator = element.getAsString();
                int delimiter = generator.lastIndexOf("_");

                String generatorName = generator.substring(0, delimiter);
                int generatorLevel = Integer.parseInt(generator.substring(delimiter + 1));

                MinionID minionID = MinionID.valueOf(generatorName);
                int actualGeneratorLevel = get(minionID);
                if (generatorLevel > actualGeneratorLevel) {
                    put(minionID, generatorLevel);
                }
            }
        }
    }
}
