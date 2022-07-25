package com.mindhub.homebanking2.dtos;

import com.mindhub.homebanking2.models.Account;
import com.mindhub.homebanking2.models.Transaction;
import com.mindhub.homebanking2.models.TransactionType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;
    private boolean active;

    public TransactionDTO(){}

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.active = transaction.isActive();
    }



    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
