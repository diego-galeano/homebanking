package com.mindhub.homebanking2.services;

import com.mindhub.homebanking2.dtos.CardDTO;
import com.mindhub.homebanking2.models.Card;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {
    List<Card> getCards();
    List<CardDTO> getCardsDto();
    void saveCard(Card card);
    void deleteCard(String cardNumber);
}
