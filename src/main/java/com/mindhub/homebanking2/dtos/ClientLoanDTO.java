package com.mindhub.homebanking2.dtos;

import com.mindhub.homebanking2.models.ClientLoan;

public class ClientLoanDTO {
    private long id;
    private long loanId;
    private String name;
    private int payments;
    private double amounts;


    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientloan) {
        this.id=clientloan.getId();
        this.loanId=clientloan.getLoan().getId();
        this.name = clientloan.getLoan().getName();
        this.amounts =clientloan.getAmount();
        this.payments = clientloan.getPayments();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getAmounts() {
        return amounts;
    }

    public void setAmounts(double amounts) {
        this.amounts = amounts;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }
    @Override
    public String toString() {
        return "ClientLoanDTO{" +
                "id=" + id +
                ", loanId=" + loanId +
                ", name='" + name + '\'' +
                ", payments=" + payments +
                ", amounts=" + amounts +
                '}';
    }
}
