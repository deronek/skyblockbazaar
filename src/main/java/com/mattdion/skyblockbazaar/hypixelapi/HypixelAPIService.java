package com.mattdion.skyblockbazaar.hypixelapi;

import net.hypixel.api.reply.skyblock.BazaarReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HypixelAPIService {
    private final HypixelAPIUtil hypixelAPIUtil;


    @Autowired
    public HypixelAPIService(HypixelAPIUtil hypixelAPIUtil) {
        this.hypixelAPIUtil = hypixelAPIUtil;
    }

    BazaarReply getBazaarReply() {
        hypixelAPIUtil.requestUpdateProducts();
        return hypixelAPIUtil.getBazaarReply();
    }
}
