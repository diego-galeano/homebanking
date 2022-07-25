package com.mindhub.homebanking2.controllers;

import com.mindhub.homebanking2.dtos.CardDTO;
import com.mindhub.homebanking2.models.Card;
import com.mindhub.homebanking2.models.CardColor;
import com.mindhub.homebanking2.models.CardType;
import com.mindhub.homebanking2.models.Client;
import com.mindhub.homebanking2.services.CardService;
import com.mindhub.homebanking2.services.ClientService;
import com.mindhub.homebanking2.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientService clientService;

    @Autowired
    CardService cardService;

    @GetMapping("/cards")
    List<CardDTO> getCards(){return cardService.getCardsDto();}


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam String color, @RequestParam String type,Authentication authentication){

        CardColor cardColor = CardColor.valueOf(color);
        CardType cardType =   CardType.valueOf(type);

        Client currentClient = clientService.findByEmail(authentication.getName());

        int totalCards = (int) currentClient.getCards().stream().filter(card -> card.getCardType().equals(cardType) && card.isActive() == true).count();
        if(totalCards > 2){
        return new ResponseEntity<>("Excedío la cantidad de 3 tarjetas",HttpStatus.FORBIDDEN);
        }

        //Verifico si el número de tarjeta creada ya existe en la base de datos
        String cardNumber = CardUtils.getCardNumber();
        List<Card> cardList = cardService.getCards();

        String finalCardNumber = cardNumber;

        boolean cardChecked = cardList.stream().anyMatch(card -> card.getNumber().equals(finalCardNumber));
        if (cardChecked){
            cardNumber = CardUtils.getCardNumber();
        }

        Card newCard = new Card(cardType, cardColor, cardNumber, CardUtils.getCVV(),  LocalDate.now(), LocalDate.now().plusYears(5), currentClient, true);

        cardService.saveCard(newCard);
        return new ResponseEntity<>("Tarjeta creada" , HttpStatus.CREATED);
    }
    @PutMapping("clients/current/cards")
    public ResponseEntity<Object> deleteCard(@RequestParam String cardNumber,Authentication authentication){

        Client client = clientService.findByEmail(authentication.getName());
        if(client == null){
           return new ResponseEntity<>("No se puede realizar la operación sin autenticación", HttpStatus.FORBIDDEN);
        }
        if (!CardUtils.isCardAuth(client, cardNumber)) {
            return new ResponseEntity<>("No se puede realizar la operación. No se encuentra la Tarjeta  ", HttpStatus.FORBIDDEN);
        }
        cardService.deleteCard(cardNumber);
        return new ResponseEntity<>("Tarjeta eliminada con éxito", HttpStatus.ACCEPTED);
    }
}