/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bcn.cardsystem.api.card;

import com.bcn.cardsystem.api.card.entities.Card;
import com.bcn.cardsystem.helper.exceptions.BadRequestException;

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

    public void validate_checksum(Card entity) {
        //validate to see that the card is not empty 
        entity.validate();
        String[] cardNumberSets = validateCardNumber(entity);
        //todo: proceede  :: 
       for(String set : cardNumberSets){           
           String firstFourCharacters = getCharacters(set,4);
           convertFromBaseToBase(firstFourCharacters, 0, 0);
       }
        

    }

 
    public String[] validateCardNumber(Card entity) throws BadRequestException {
        //todo: validate the card has four parts and bring out the  digits
        String[] cardNumberSets = getCardSetsOfDigits(entity.getNumber());
        if (cardNumberSets.length != 4) {
            throw new BadRequestException("Card number is in a wrong format");
        }
        return cardNumberSets;
    }

    public String getCharacters(String set,Integer num_of_characters) {
        String firstFourCharacters = set.substring(0, Math.min(set.length(), num_of_characters));
        return firstFourCharacters;
    }

    public String[] getCardSetsOfDigits(String card_number) {
        String[] wordSplit = card_number.split("[-\\s]");
        return wordSplit;
    }

    public String convertFromBaseToBase(String str, int fromBase, int toBase) {
        return Integer.toString(Integer.parseInt(str, fromBase), toBase);
    }

}
