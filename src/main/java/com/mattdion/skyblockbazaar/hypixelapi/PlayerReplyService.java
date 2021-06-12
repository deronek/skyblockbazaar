package com.mattdion.skyblockbazaar.hypixelapi;

import com.mattdion.skyblockbazaar.apiutils.HypixelAPIUtil;
import net.hypixel.api.reply.skyblock.SkyBlockProfileReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerReplyService {
    private final HypixelAPIUtil hypixelAPIUtil;

    @Autowired
    public PlayerReplyService(HypixelAPIUtil hypixelAPIUtil) {
        this.hypixelAPIUtil = hypixelAPIUtil;
    }

    public SkyBlockProfileReply getSkyblockProfileReply(String uuid) {
        return hypixelAPIUtil.getSkyblockProfileReply(uuid);
    }
}
