package com.mindhub.homebanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id ;
    private String cardHolder, number;
    private CardType cardType;
    private CardColor cardColor;
    private int cvv;
    private LocalDate fromDate, thruDate;
    private boolean active;


// private String number;
// private LocalDate thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;


    public Card() {
    }

    public Card(CardType cardType, CardColor cardColor, String number, int cvv, LocalDate fromDate, LocalDate thruDate, Client client, boolean active) {
        this.id = id;
        this.cardHolder = String.join(" ",client.getFirstName(),client.getLastName());
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.client = client;
        this.active = active;
    }

    public long getId() {
        return id;
    }


    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardHolder='" + cardHolder + '\'' +
                ", cardType=" + cardType +
                ", cardColor=" + cardColor +
                ", number='" + number + '\'' +
                ", cvv=" + cvv +
                ", fromDate=" + fromDate +
                ", thruDate=" + thruDate +
                ", client=" + client +
                '}';
    }
}
