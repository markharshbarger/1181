public class Card {
    private final Rank rank;
    private final Suit suit;
    private String fileLocation = "src/CardImages/";

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.fileLocation += this.rank + "_" + this.suit + ".png";
    }

    @Override
    public String toString() {
        return this.rank + "";
    }

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public String getFileLocation() {
        return this.fileLocation;
    }
}
