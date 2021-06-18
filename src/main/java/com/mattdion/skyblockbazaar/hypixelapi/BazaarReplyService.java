package com.mattdion.skyblockbazaar.hypixelapi;

import com.mattdion.skyblockbazaar.apiutils.HypixelAPIUtil;
import net.hypixel.api.reply.skyblock.BazaarReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BazaarReplyService {
    private final HypixelAPIUtil hypixelAPIUtil;

    @Autowired
    public BazaarReplyService(HypixelAPIUtil hypixelAPIUtil) {
        this.hypixelAPIUtil = hypixelAPIUtil;
    }

    public BazaarReply getBazaarReply() {
        hypixelAPIUtil.requestUpdateProducts();
        return hypixelAPIUtil.getBazaarReply();
    }
}
