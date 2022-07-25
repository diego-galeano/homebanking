package com.mindhub.homebanking2;


import com.mindhub.homebanking2.utils.Utilities;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
public class UtilitiesTests {


    @Test
    public void isBoolean(){
        boolean isNumber = Utilities.isNumeric("1");
        assertThat(isNumber, is(notNullValue()));
    }
}