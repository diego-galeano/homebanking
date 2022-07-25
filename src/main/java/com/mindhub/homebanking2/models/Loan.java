package com.mindhub.homebanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private String name;
    private double maxAmount, interestRate;


    @ElementCollection
    @Column(name="payments")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan() {
    }

    public Loan(String name, double maxAmount, List<Integer>  payments, double interestRate) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interestRate = interestRate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer>  payments) {
        this.payments = payments;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxAmount=" + maxAmount +
                ", interestRate=" + interestRate +
                ", payments=" + payments +
                '}';
    }
}
