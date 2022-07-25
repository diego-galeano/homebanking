package com.mindhub.homebanking2;

import com.mindhub.homebanking2.models.Client;
import com.mindhub.homebanking2.repository.ClientRepository;
import com.mindhub.homebanking2.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){

        String cardNumber = CardUtils.getCardNumber();

        assertThat(cardNumber,is(not(emptyOrNullString())));

    }
    @Test

    public void cardCvvNumberIsCreated(){
        int cardCVV = CardUtils.getCVV();
        assertThat(cardCVV, is(notNullValue()));
        assertThat(cardCVV, greaterThan(99));
        assertThat(cardCVV, lessThan(1000));
    }

    @Test
    public void isCardAuth(){
        ClientRepository clientRepository = null;
        Client client = clientRepository.findByEmail("melba@mindhub.com");
        boolean isCard = CardUtils.isCardAuth(client, "4040 5050 1234 5678");
        assertThat(isCard, is(notNullValue()));
    }

}
