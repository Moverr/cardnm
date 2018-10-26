/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bcn.cardsystem.api.card;

import com.bcn.cardsystem.api.card.entities.Card;
import com.bcn.cardsystem.helper.exceptions.BadRequestException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Manny
 */
public class CardServiceTest {

    public CardServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of validate_checksum method, of class CardService.
     */
    @Test
    public void test_validate_checksum_check_for_mandatory_fields() {
        try {
            System.out.println("validate_checksum");
            Card entity = new Card();
            CardService instance = new CardService();
            instance.validate_checksum(entity);
        } catch (BadRequestException badRequestException) {
            assertEquals(badRequestException.getResponse().getStatus(), 400);
        }

    }

    @Test
    public void test_card_split_with_wrong_card_format() {

        System.out.println("test_card_split_with_wrong_card_format");
        String card_number = "";
        CardService instance = new CardService();
        String[] st = instance.getCardSetsOfDigits(card_number);
        System.out.println(st.length);
        assertNotEquals(4, st.length);

    }

    @Test
    public void test_card_split_with_correct_card_format() {

        System.out.println("test_card_split_with_correct_card_format");
        String card_number = "10017-49092-04244-37262";
        CardService instance = new CardService();
        String[] st = instance.getCardSetsOfDigits(card_number);
        System.out.println(st.length);
        assertEquals(4, st.length);

    }

}
