public class Card {
    private final Rank rank;
    private final Suit suit;
    private final String imageFolderLocation = "src/CardImages/";
    private String fileLocation;
    private boolean faceUp;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.fileLocation = getFaceUpFileLocation();
        faceUp = true;
    }

    private String getFaceUpFileLocation() {
        return this.imageFolderLocation + this.rank + "_" + this.suit + ".png";
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

    public boolean getfaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean value) {
        this.faceUp = value;

        if (value == false) {
            fileLocation = this.imageFolderLocation + "BACK_CARD.png";
        } else {
            fileLocation = getFaceUpFileLocation();
        }

    }
}
