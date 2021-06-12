package com.mattdion.skyblockbazaar.hypixelapi;

import com.mattdion.skyblockbazaar.apiutils.HypixelAPIUtil;
import net.hypixel.api.reply.skyblock.BazaarReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

@Service
public class BazaarReplyService {
    private final HypixelAPIUtil hypixelAPIUtil;

    @Autowired
    public BazaarReplyService(HypixelAPIUtil hypixelAPIUtil) {
        this.hypixelAPIUtil = hypixelAPIUtil;
    }

    public BazaarReply getBazaarReply() throws TimeoutException {
        hypixelAPIUtil.requestUpdateProducts();
        return hypixelAPIUtil.getBazaarReply();
    }
}
