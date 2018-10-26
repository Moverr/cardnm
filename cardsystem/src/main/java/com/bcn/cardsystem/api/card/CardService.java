/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bcn.cardsystem.api.card;

/**
 *
 * @author mover
 */
public class CardService {

    private static CardService instance;

    public CardService() {
    }

    public static CardService getInstance() {
        if (instance == null) {
            instance = new CardService();
        }

        return instance;
    }

}
