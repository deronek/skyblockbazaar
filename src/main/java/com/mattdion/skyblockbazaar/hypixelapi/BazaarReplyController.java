package com.mattdion.skyblockbazaar.hypixelapi;

import net.hypixel.api.reply.skyblock.BazaarReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
public class BazaarReplyController {
    private final BazaarReplyService bazaarReplyService;

    @Autowired
    public BazaarReplyController(BazaarReplyService bazaarReplyService) {
        this.bazaarReplyService = bazaarReplyService;
    }

    @GetMapping("api/v1/products")
    public BazaarReply getBazaarReply() throws TimeoutException {
        return bazaarReplyService.getBazaarReply();
    }
}
