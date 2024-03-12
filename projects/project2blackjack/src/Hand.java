import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class represents a 'hand' of cards for a game of BlackJack, hand includes a variable 'bust' if the hand value is
 * over 21
 */
public class Hand {
    private ArrayList<Card> hand = new ArrayList<>();
    private boolean bust;
    public HashMap<Rank, Integer> cardValues = new HashMap<Rank, Integer>() {{
        put(Rank.TWO, 2);
        put(Rank.THREE, 3);
        put(Rank.FOUR, 4);
        put(Rank.FIVE, 5);
        put(Rank.SIX, 6);
        put(Rank.SEVEN, 7);
        put(Rank.EIGHT, 8);
        put(Rank.NINE, 9);
        put(Rank.TEN, 10);
        put(Rank.JACK, 10);
        put(Rank.QUEEN, 10);
        put(Rank.KING, 10);
        put(Rank.ACE, 11); // ACE can also be treated as 1, handled in totalHandValue method
    }};

    /**
     * Constructor of Hand class, sets bust to false
     */
    public Hand() {
        this.bust = false;
    }

    /**
     * Adds a card to the hand array
     * 
     * @param card the card to be added to hand array
     */
    public void addCard(Card card) {
        this.hand.add(card);
    }

    /**
     * Gets the number of cards in hand array
     * 
     * @return int - number of cards in hand array
     */
    public int numOfCards() {
        return this.hand.size();
    }

    /**
     * Gets the hand array
     * 
     * @return ArrayList<Card> - the hand
     */
    public ArrayList<Card> getHand() {
        return this.hand;
    }

    /**
     * Gets the card at the specified index
     * @param index
     * @return
     */
    public Card getCard(int index) {
        return this.hand.get(index);
    }

    /**
     * Gets the value of the hand array by adding up value of cards in hand, outputs the best score by letting
     * aces count as 11 unless the hand value goes over 21, then the ace in hand will count as 1
     * 
     * @return int - value of hand array of Cards
     */
    public int value() {
        int total = 0;
        int numOfAce = 0;
        for (Card card : hand) {
            // doesn't include facedown cards in hand value
            if (card.isFaceUp() == false) {
                continue;
            }
            total += cardValues.get(card.getRank());

            if (card.getRank() == Rank.ACE) {
                ++numOfAce;
            }
        }

        while (total > 21 && numOfAce > 0) {
            total -= 10; // treat ACE as 1
            --numOfAce;
        }

        return total;
    }

    /**
     * Clears the hand array
     */
    public void clearHand() {
        this.hand.clear();
        this.bust = false;
    }

    /**
     * Gets if all the cards in hand array add up to over 21
     * 
     * @return true if value of hand is over 21, false otherwise
     */
    public boolean getBust() {
        if (value() > 21) {
            this.bust = true;
        }
        return this.bust;
    }
}