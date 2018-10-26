/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bcn.cardsystem.api.card;

import com.bcn.cardsystem.api.card.entities.Card;
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
     * Test of getInstance method, of class CardService.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        CardService expResult = null;
        CardService result = CardService.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validate_checksum method, of class CardService.
     */
    @Test
    public void testValidate_checksum() {
        System.out.println("validate_checksum");
        Card entity = null;
        CardService instance = new CardService();
        instance.validate_checksum(entity);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
