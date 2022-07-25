package com.mindhub.homebanking2;

import com.mindhub.homebanking2.utils.AccountUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public final class AccountUtilsTest {

    @Test
    public void accountNumberIsCreated() {
        String accountNumber = AccountUtils.getRandomAccountNumber();
        assertThat(accountNumber,is(not(emptyOrNullString())));
    }
}
