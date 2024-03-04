import java.security.SecureRandom;
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
        Collections.shuffle(deck, new SecureRandom());
    }

    public Card drawCard() {
        if (!deck.isEmpty()) {
            return deck.remove(0);
        }
        
        System.out.println("New Deck");
        initializeDeck();
        return deck.remove(0);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public boolean empty() {
        return deck.size() == 0;
    }

    public Card peekCard() {
        return deck.get(0);
    }
}