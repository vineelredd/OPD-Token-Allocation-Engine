package com.MEDOC.HEALTH.sweinternbackend.controller;

import com.MEDOC.HEALTH.sweinternbackend.model.Token;
import com.MEDOC.HEALTH.sweinternbackend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/book")
    public String book(@RequestBody Token token) {
        token.setId("TK-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());
        token.setStatus(Token.Status.BOOKED);
        return tokenService.allocateToken(token);
    }

    @GetMapping("/view/{slotId}")
    public List<Token> viewSchedule(@PathVariable String slotId) {
        return tokenService.getPrioritizedView(slotId);
    }
}