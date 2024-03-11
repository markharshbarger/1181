import java.util.ArrayList;
import java.util.Collections;

public class DeckOfCards {
    private ArrayList<Card> deck = new ArrayList<Card>();
    
    public DeckOfCards() {
        initializeDeck();
    }

    private void initializeDeck() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card drawCard() {
        if (deck.isEmpty()) {
            initializeDeck();
        }
        System.out.println("New Deck");
        return deck.remove(0);
    }

    public Card drawCardFaceDown() {
        if (deck.isEmpty()) {
            initializeDeck();
        }

        System.out.println("New Deck");
        Card topCard = deck.get(0);
        topCard.setFaceUp(false);
        deck.remove(0);
        return topCard;
    }

    // remove maybe?
    public ArrayList<Card> getDeck() {
        return deck;
    }

    public boolean empty() {
        return deck.size() == 0;
    }
}
