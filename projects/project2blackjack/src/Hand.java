import java.util.ArrayList;
import java.util.HashMap;

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

    public Hand() {
        this.bust = false;
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public int numOfCards() {
        return this.hand.size();
    }

    public ArrayList<Card> returnHand() {
        return this.hand;
    }

    public Card getCard(int index) {
        return this.hand.get(index);
    }

    public int totalHandValue() {
        int total = 0;
        int numOfAce = 0;
        for (Card card : hand) {
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

    public void clearHand() {
        this.hand.clear();
        this.bust = false;
    }

    public boolean getBust() {
        if (totalHandValue() > 21) {
            this.bust = true;
        }
        return this.bust;
    }
}