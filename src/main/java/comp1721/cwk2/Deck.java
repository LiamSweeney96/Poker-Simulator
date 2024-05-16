package comp1721.cwk2;


import java.util.Collections;

import comp1721.cwk2.Card.Rank;
import comp1721.cwk2.Card.Suit;

/**
 * 
 * The Deck class creates a deck which will be
 * used to retrieve cards from for the hand. 
 * Inherits from the CardCollection class.
 * 
 * @author Liam Sweeney
 * @version 1.0
 * @since 18-05-2022
 * 
 */

public class Deck extends CardCollection {

    /**
     * The Deck constructor builds a full deck using
     * the Suit and Rank methods in the CardCollection
     * class.
     */
    public Deck() {

        for (Suit suit : Suit.values())
        {
            for (Rank rank : Rank.values())
            {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }
    }

    /**
     * Size method returns the size of the deck
     */
    public int size () {
        return cards.size();
    }

    /**
     * isEmpty method returns true if the deck is 
     * empty and false if not.
     */
    public boolean isEmpty () {
        return cards.isEmpty();
    }

    /** 
     * Contains method returns true if the deck contains
     * the card passed as a parameter, and false otherwise.
     * 
     * @param card
     */
    public boolean contains (Card card) {
        return cards.contains(card);
    }


    /**
     * Discard method discards the cards in the deck.
     * Throws CardException if attempting to discard
     * from an empty deck.
     * 
     * @throws CardException
     */
    public void discard () throws CardException {
        if (cards.isEmpty())
        {
            throw new CardException("Discarding Empty Deck"); 
        }
        cards.clear(); 
    }

    /**
     * The deal method retrieves the next card in the deck
     * and returns it. Throws CardException if attempting to
     * deal from an empty deck.
     * 
     * @throws CardException
     */
    public Card deal () throws CardException {
        if (cards.isEmpty())
        {
            throw new CardException("Dealing from empty deck");
        }
        Card nextCard = cards.get(0);
        cards.remove(0);
        return nextCard;
    }

    /**
     * The shuffle method shuffles the deck into a random
     * order.
     */
    public void shuffle () {
        Collections.shuffle(cards);
    }

    /**
     * Method to add card to the deck. Throws CardException
     * if attempting to add a duplicate card or if too many
     * cards have been added.     
     */
    public void add(Card card) throws CardException {
        if (cards.contains(card))
        {
            throw new CardException("Duplicate Card");  
        }
        cards.add(card);
    }
}
