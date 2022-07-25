package com.mindhub.homebanking2.utils;

import com.mindhub.homebanking2.models.Card;
import com.mindhub.homebanking2.models.CardType;
import com.mindhub.homebanking2.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public final class CardUtils {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getCardNumber(){
        String cardNumber = getRandomNumber(4000, 4999) +" "+getRandomNumber(1000,9999) +" "+getRandomNumber(1000,9999) +" "+getRandomNumber(1000,9999);
        return cardNumber;
    }

    public static int getCVV(){
        int cvv = (int) ((Math.random() * (1_000 - 100)) + 100);
        return cvv;
    }
    public static boolean isCardAuth(Client client, String cardNumber){
        boolean isAuth = client.getCards().stream().anyMatch(card -> card.getNumber().equals(cardNumber));
        return isAuth;
    }

    public static int countByCardType(List<Card> cardList, String cardType){
        int totalCards = (int) cardList.stream().filter(card -> card.getCardType().equals(cardType)).count();
        return totalCards;
    }
}
