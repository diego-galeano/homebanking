package com.mindhub.homebanking2.services.implementations;

import com.mindhub.homebanking2.dtos.CardDTO;
import com.mindhub.homebanking2.models.Card;
import com.mindhub.homebanking2.models.CardType;
import com.mindhub.homebanking2.models.Client;
import com.mindhub.homebanking2.repository.CardRepository;
import com.mindhub.homebanking2.repository.ClientRepository;
import com.mindhub.homebanking2.services.CardService;
import com.mindhub.homebanking2.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;

    @Override
    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    @Override
    public List<CardDTO> getCardsDto() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }


    @Override
    public void deleteCard(String cardNumber) {
            Card card = cardRepository.findByNumber(cardNumber);
            card.setActive(false);
            cardRepository.save(card);
    }

}
