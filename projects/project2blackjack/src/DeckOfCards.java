import java.util.ArrayList;
import java.util.Collections;

/**
 * Class represents a deck of playing cards which cwill start off with 52 cards, drawing a card will remove it from the deck.
 * Once the deck has no cards it creates another deck of 52 cards.
 */
public class DeckOfCards {
    private ArrayList<Card> deck = new ArrayList<Card>();
    
    /**
     * Creates a new Deck of Cards with 52 cards
     */
    public DeckOfCards() {
        initializeDeck();
    }

    /**
     * Initializes a deck of cards by creating adding 52 cards to the deck array 
     */
    private void initializeDeck() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        shuffle();
    }

    /**
     * Shuffles the deck array
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Draw a card from the deck and removes the card from the deck array
     * 
     * @return one removed Card from the deck
     */
    public Card drawCard() {
        if (deck.isEmpty()) {
            initializeDeck();
        }
        System.out.println("New Deck");
        return deck.remove(0);
    }

    /**
     * Draws a card 'face down' by using Card class method
     * 
     * @return a Card that is 'face down'
     */
    public Card drawCardFaceDown() {
        if (deck.isEmpty()) {
            initializeDeck();
            System.out.println("New Deck");
        }

        Card topCard = deck.get(0);
        topCard.setFaceUp(false);
        deck.remove(0);
        return topCard;
    }

    /**
     * Returns the current deck in use
     * 
     * @return ArrayList<Card> deck
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Method to get if current deck contains no cards
     * 
     * @return true if deck contains no cards, false otherwise
     */
    public boolean empty() {
        return deck.size() == 0;
    }
}
