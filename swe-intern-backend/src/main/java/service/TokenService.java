package com.MEDOC.HEALTH.sweinternbackend.service;

import com.MEDOC.HEALTH.sweinternbackend.model.Token;
import com.MEDOC.HEALTH.sweinternbackend.model.Doctor;
import com.MEDOC.HEALTH.sweinternbackend.repository.TokenRepository;
import com.MEDOC.HEALTH.sweinternbackend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public String allocateToken(Token token) {
        // Enforce per-slot hard limits
        Doctor doctor = doctorRepository.findById(Long.parseLong(token.getDoctorId()))
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        long currentActiveTokens = tokenRepository.findBySlotId(token.getSlotId()).stream()
                .filter(t -> t.getStatus() != Token.Status.CANCELLED)
                .count();

        // Elastic Capacity Logic: Emergency bypasses limits [cite: 3, 11]
        if (currentActiveTokens >= doctor.getMaxCapacityPerSlot() &&
                token.getSource() != Token.Source.EMERGENCY) {
            return "Slot capacity reached. Please choose another time.";
        }

        tokenRepository.save(token);
        return "Token " + token.getId() + " allocated successfully.";
    }

    public List<Token> getPrioritizedView(String slotId) {
        List<Token> tokens = tokenRepository.findBySlotId(slotId);
        // Prioritizes between different token sources via compareTo [cite: 17, 27]
        Collections.sort(tokens);
        return tokens;
    }
}