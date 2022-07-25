package com.mindhub.homebanking2;

import com.mindhub.homebanking2.models.Loan;
import com.mindhub.homebanking2.repository.LoanRepository;
import com.mindhub.homebanking2.services.LoanService;
import com.mindhub.homebanking2.utils.LoanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
public class LoanUtilsTest {
    @Autowired
    LoanRepository loanRepository;
    @Test
    public void isInterest(){
       Loan loan = loanRepository.findByName("Hipotecario");
       double interest = LoanUtils.getInterest(loan);
        assertThat(interest, is(notNullValue()));
    }


}
