package com.mindhub.homebanking2.utils;

import com.mindhub.homebanking2.models.Loan;

public final class LoanUtils {

//    public static double getInterest(Loan loan) {
//        double interest = 0d;
//        switch (loan.getName()){
//            case "Hipetario":
//                interest = 10;
//                break;
//            case "Automotriz":
//                interest =  19;
//                break;
//            case "Personal":
//                interest = 20;
//                break;
//        }
//        return interest;
//    }
    public static double calcInterest(double interestRate, double amount) {
        double result = amount * (interestRate / 100) + amount;
        return result ;
    }
}

