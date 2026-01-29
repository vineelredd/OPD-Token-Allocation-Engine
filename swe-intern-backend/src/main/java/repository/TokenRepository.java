package com.MEDOC.HEALTH.sweinternbackend.repository;

import com.MEDOC.HEALTH.sweinternbackend.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    // Used to retrieve and sort tokens for a specific doctor's slot
    List<Token> findBySlotId(String slotId);
}