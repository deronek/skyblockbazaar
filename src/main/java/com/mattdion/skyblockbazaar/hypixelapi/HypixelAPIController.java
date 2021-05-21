package com.mattdion.skyblockbazaar.hypixelapi;

import net.hypixel.api.reply.skyblock.BazaarReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HypixelAPIController {
    private final HypixelAPIService hypixelAPIService;

    @Autowired
    public HypixelAPIController(HypixelAPIService hypixelAPIService) {
        this.hypixelAPIService = hypixelAPIService;
    }

    @GetMapping("api/v1/products")
    public BazaarReply getBazaarReply() {
        return hypixelAPIService.getBazaarReply();
    }
}
