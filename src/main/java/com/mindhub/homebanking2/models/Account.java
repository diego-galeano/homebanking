package com.mindhub.homebanking2.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    private AccountType type;
    private String number;
    private LocalDateTime creationData;
    private double balance;
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    protected Account(){}



    public Account(AccountType type, String number, LocalDateTime creationData, double balance, boolean active, Client client) {
        this.type = type;
        this.number = number;
        this.creationData = creationData;
        this.balance = balance;
        this.client = client;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
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
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationData=" + creationData +
                ", balance=" + balance +
                '}';
    }


}
