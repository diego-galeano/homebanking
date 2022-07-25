package com.mindhub.homebanking2.dtos;

import com.mindhub.homebanking2.models.Account;
import com.mindhub.homebanking2.models.AccountType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private AccountType type;
    private String number;
    private LocalDateTime creationData;
    private double balance;
    private boolean active;
    private Set<TransactionDTO> transactions = new HashSet<>();

    protected AccountDTO(){}

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.type = account.getType();
        this.number = account.getNumber();
        this.creationData = account.getCreationData();
        this.balance = account.getBalance();
        this.active = account.isActive();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationData() {
        return creationData;
    }

    public void setCreationData(LocalDateTime creationData) {
        this.creationData = creationData;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
