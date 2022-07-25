package com.mindhub.homebanking2.dtos;


public class LoanApplicationDTO {
    private Long id;
    private double amount;
    private int payments;
    private String accountNum;


    public LoanApplicationDTO(Long id,  double amount, int payments,String accountNum) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.accountNum = accountNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    @Override
    public String toString() {
        return "LoanApplicationDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", payments=" + payments +
                ", accountNum='" + accountNum + '\'' +
                '}';
    }
}

