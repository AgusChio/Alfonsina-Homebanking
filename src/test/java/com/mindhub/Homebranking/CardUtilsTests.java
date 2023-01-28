package com.mindhub.Homebranking;


import com.mindhub.Homebranking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

//    @Test
//    public void cardNumberIsCreated(){
//        String cardNumber = CardUtils.getCardNumber();
//        assertThat(cardNumber, is(not(emptyOrNullString())));
//    }
//
//    @Test
//    public void cardCVVIsCreated(){
//        int cardCVV = CardUtils.getNumberCVV();
//        assertThat(cardCVV,notNullValue());
//    }
//
//    @Test
//    public void existCardCVV(){
//        int cardCVV = CardUtils.getNumberCVV();
//        assertThat(cardCVV,lessThan(1000));
//    }
//
//    @Test
//    public void randomNumberIsCreated(){
//        int randomNumber = CardUtils.getRandomNumber(1000, 9999);
//        assertThat(randomNumber, notNullValue());
//    }
}
