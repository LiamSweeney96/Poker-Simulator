package comp1721.cwk2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import comp1721.cwk2.Card.Rank;

/**
 * 
 * The PokerHand class creates a pokerhand which
 * is then tested to see what the value of the 
 * hand is. The value of the hand is whether 
 * there is a pair, 2 pair, flush etc. present.
 * Inherits from the CardCollection class.
 * 
 * @author Liam Sweeney
 * @version 1.0
 * @since 18-05-2022
 * 
 */

public class PokerHand extends CardCollection {

    public static final int FULL_SIZE = 5;

    /**
     * PokerHand constructor with no parameters calls super() 
     * to ensure anything inherited is initialised properly.
     */

    public PokerHand () {

        super();
    
    }

    /**
     * PokerHand constructor with one parameter splits the 
     * String parameter and adds it to the hand. 
     * In the even of attempting to add too many cards to 
     * the hand, a  CardException is thrown.
     * @param addCards
     * @throws CardException
     */

    public PokerHand (String addCards) throws CardException {

        String[] cardArray = addCards.split(" ");
        int cardCount = 0;

        for (int i = 0; i < cardArray.length; i++)
        {
            Card card = new Card(cardArray[i]);
            cards.add(card);
            cardCount++;
        }
        if (cardCount > FULL_SIZE)
        {
            throw new CardException("Too many cards added to hand");
        }

    }

    /**
     * Method to override the toString method, enabling 
     * the hand to be printed as a String with a series of 
     * 2 chars containing rank and suit.
     */
    @Override
    public String toString() {

        StringBuffer newString = new StringBuffer();

        for (int i = 0; i < cards.size(); i++)
        {
            newString.append(cards.get(i));
            if (i != cards.size() - 1)
            {
                newString.append(" ");
            }
        }
        return newString.toString();
    }

    /**
     * Method to return the size of the hand
     */
    public int size () {
        return cards.size();
    }

    /** 
     * Method to discard the cards in the hand. Throws
     * a CardException if attempting to discard an empty
     * hand
     * @throws CardException
     */
    public void discard () throws CardException {
        if (cards.isEmpty())
        {
            throw new CardException("Cannot discard empty hand");
        }
        cards.clear(); 
    }

    /**
     * Method to discard cards back to the deck specified
     * in the given parameter. Throws CardException if
     * attempting to discard from an empty hand.
     * 
     * @param deck
     * @throws CardException
     */
    public void discardTo (Deck deck) throws CardException{
        if (cards.isEmpty())
        {
            throw new CardException("Cannot discard empty hand");
        }
        // Add cards back to deck before removing to ensure they aren't lost.
        for (int i = 0; i < cards.size(); i++)
        {
            deck.add(cards.get(i));
            cards.remove(i);
        }
        deck.add(cards.get(0));
        cards.remove(0);
    }

    /**
     * Method to check whether a pair is present in 
     * the dealt hand. Returns true if so.
     */
    public boolean isPair () {

        if (cards.size() != FULL_SIZE)
        {
            return false;
        }
        
        int pairCount = 0;

        // Iterate through card ranks to find a match
        for (int i = 0; i < cards.size(); i++)
        {
            for (int j = 0; j < cards.size(); j++)
            {
                if (j != i)
                {
                    if (cards.get(i).getRank().equals(cards.get(j).getRank()))
                    {
                        pairCount++;
                    }
                }
            }
        }
        // If a pair is found, return true
        if (pairCount == 2)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Method to check whether two pairs are present in 
     * the dealt hand. Returns true if so.
     */
    public boolean isTwoPairs () {

        if (cards.size() != FULL_SIZE)
        {
            return false;
        }
        
        int pairCount = 0;
        
        // Iterate through card ranks to find two matches
        for (int i = 0; i < cards.size(); i++)
        {
            for (int j = 0; j < cards.size(); j++)
            {
                if (j != i)
                {
                    if (cards.get(i).getRank().equals(cards.get(j).getRank()))
                    {
                        pairCount++;
                    }
                
                }
            }
        }
        // If two matches found, return true
        if (pairCount == 4)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Method to check whether 3 of a kind is present in 
     * the dealt hand. Returns true if so.
     */
    public boolean isThreeOfAKind () {

        if (cards.size() != FULL_SIZE)
        {
            return false;
        }
        if (isFourOfAKind())
        {
            return false;
        }
        if (isFullHouse())
        {
            return false;
        }
        
        // Create a hashmap which will be used to count occurrences
        HashMap<Character, Integer> countMap = new HashMap<Character, Integer>();

        char[] charArray = {cards.get(0).getRank().toString().charAt(0), cards.get(1).getRank().toString().charAt(0), 
                            cards.get(2).getRank().toString().charAt(0), cards.get(3).getRank().toString().charAt(0), 
                            cards.get(4).getRank().toString().charAt(0)};

        // For every rank, add 1 to the count for that specific rank
        for (char rank : charArray)
        {
            if (countMap.containsKey(rank))
            {
                countMap.put(rank, countMap.get(rank) + 1);
            }
            else
            {
                countMap.put(rank, 1);
            }
        }
        // If the map contains 3 of one value and 1 of one of the others, return true
        if (countMap.values().contains(3) && countMap.values().contains(1))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * Method to check whether 4 of a kind is present in 
     * the dealt hand. Returns true if so.
     */
    public boolean isFourOfAKind () {

        if (cards.size() != FULL_SIZE)
        {
            return false;
        }
        
        int cardCount = 0;

        // Iterate through ranks to find matching values        
        for (int i = 0; i < cards.size(); i++)
        {
            cardCount = 0;
            for (int j = 0; j < cards.size(); j++)
            {
                if (cards.get(i).getRank().equals(cards.get(j).getRank()))
                {
                    cardCount++;
                }
                // If 4 of the same rank, return true
                if (cardCount == 4)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to check whether a full house is present in 
     * the dealt hand. Returns true if so.
     */
    public boolean isFullHouse () {

        if (cards.size() != FULL_SIZE)
        {
            return false;
        }

        // Create a hashmap which will be used to count occurrences
        HashMap<Character, Integer> countMap = new HashMap<Character, Integer>();

        char[] charArray = {cards.get(0).getRank().toString().charAt(0), cards.get(1).getRank().toString().charAt(0), 
                            cards.get(2).getRank().toString().charAt(0), cards.get(3).getRank().toString().charAt(0), 
                            cards.get(4).getRank().toString().charAt(0)};

        // For every rank, add 1 to the count for that specific rank
        for (char rank : charArray)
        {
            if (countMap.containsKey(rank))
            {
                countMap.put(rank, countMap.get(rank) + 1);
            }
            else
            {
                countMap.put(rank, 1);
            }
        }

        // If one value has occurred 3 times, and another value has occured twice, return true
        if (countMap.values().contains(3) && countMap.values().contains(2))
        {
            return true;
        }
        
        return false;
    }

    /**
     * Method to check whether a flush is present in 
     * the dealt hand. Returns true if so.
     */
    public boolean isFlush () {

        if (cards.size() != FULL_SIZE)
        {
            return false;
        }

        int suitCount = 0;

        // Iterate through suits
        for (int i = 0; i < cards.size(); i++)
        {
            suitCount = 0;
            for (int j = 0; j < cards.size(); j++)
            {
                if (cards.get(i).getSuit().equals(cards.get(j).getSuit()))
                {
                    suitCount++;
                }
                // If all suits are the same, return true
                if (suitCount == 5)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to check whether a straight is present in 
     * the dealt hand. Returns true if so.
     */
    public boolean isStraight () {  

        if (cards.size() != FULL_SIZE)
        {
            return false;
        }
        if (isFlush())
        {
            return false;
        }
        // Create a new list to store ranks
        List<Rank> newList = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++)
        {
            newList.add(cards.get(i).getRank());
        }

        Collections.sort(newList);
        Card.useFancySymbols(false);

        // If statements to determine if a straight is present, return true if. (Ran out of time so the simple option was taken)
        if (newList.get(0).equals(Card.Rank.ACE) && newList.get(1).equals(Card.Rank.TWO) && newList.get(2).equals(Card.Rank.THREE) && newList.get(3).equals(Card.Rank.FOUR) && newList.get(4).equals(Card.Rank.FIVE))
        {
            return true;
        }
        if (newList.get(0).equals(Card.Rank.TWO) && newList.get(1).equals(Card.Rank.THREE) && newList.get(2).equals(Card.Rank.FOUR) && newList.get(3).equals(Card.Rank.FIVE) && newList.get(4).equals(Card.Rank.SIX))
        {
            return true;
        }
        if (newList.get(0).equals(Card.Rank.THREE) && newList.get(1).equals(Card.Rank.FOUR) && newList.get(2).equals(Card.Rank.FIVE) && newList.get(3).equals(Card.Rank.SIX) && newList.get(4).equals(Card.Rank.SEVEN))
        {
            return true;
        }
        if (newList.get(0).equals(Card.Rank.FOUR) && newList.get(1).equals(Card.Rank.FIVE) && newList.get(2).equals(Card.Rank.SIX) && newList.get(3).equals(Card.Rank.SEVEN) && newList.get(4).equals(Card.Rank.EIGHT))
        {
            return true;
        }
        if (newList.get(0).equals(Card.Rank.FIVE) && newList.get(1).equals(Card.Rank.SIX) && newList.get(2).equals(Card.Rank.SEVEN) && newList.get(3).equals(Card.Rank.EIGHT) && newList.get(4).equals(Card.Rank.NINE))
        {
            return true;
        }
        if (newList.get(0).equals(Card.Rank.SIX) && newList.get(1).equals(Card.Rank.SEVEN) && newList.get(2).equals(Card.Rank.EIGHT) && newList.get(3).equals(Card.Rank.NINE) && newList.get(4).equals(Card.Rank.TEN))
        {
            return true;
        }
        if (newList.get(0).equals(Card.Rank.SEVEN) && newList.get(1).equals(Card.Rank.EIGHT) && newList.get(2).equals(Card.Rank.NINE) && newList.get(3).equals(Card.Rank.TEN) && newList.get(4).equals(Card.Rank.JACK))
        {
            return true;
        }
        if (newList.get(0).equals(Card.Rank.EIGHT) && newList.get(1).equals(Card.Rank.NINE) && newList.get(2).equals(Card.Rank.TEN) && newList.get(3).equals(Card.Rank.JACK) && newList.get(4).equals(Card.Rank.QUEEN))
        {
            return true;
        }
        if (newList.get(0).equals(Card.Rank.NINE) && newList.get(1).equals(Card.Rank.TEN) && newList.get(2).equals(Card.Rank.JACK) && newList.get(3).equals(Card.Rank.QUEEN) && newList.get(4).equals(Card.Rank.KING))
        {
            return true;
        }
        if (newList.get(0).equals(Card.Rank.ACE) && newList.get(1).equals(Card.Rank.TEN) && newList.get(2).equals(Card.Rank.JACK) && newList.get(3).equals(Card.Rank.QUEEN) && newList.get(4).equals(Card.Rank.KING))
        {
            return true;
        }

        return false;
    }

    /**
     * Method to add card to the hand. Throws CardException
     * if attempting to add a duplicate card or if too many
     * cards have been added.     
     */
    public void add(Card deal) {

        if (cards.contains(deal))
        {
            throw new CardException("Duplicate card");
        }
        if (cards.size() == 5)
        {
            throw new CardException("Too many cards added");
        }
        cards.add(deal);
    }
}
