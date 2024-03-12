public class Card {
    private final Rank rank;
    private final Suit suit;
    private final String imageFolderLocation = "src/CardImages/";
    private String fileLocation;
    private boolean faceUp;

    /**
     * Creates a Card object based of rank and suit. Also sets file location for card image to the top or face,
     * and not the bottom
     * 
     * @param rank enum that has the 13 different playing card names along with their values
     * @param suit enum that has the 4 playing card suits
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.fileLocation = getFaceUpFileLocation();
        faceUp = true;
    }

    /**
     * Gets the face up file location for the playing card
     * 
     * @return 
     */
    private String getFaceUpFileLocation() {
        return this.imageFolderLocation + this.rank + "_" + this.suit + ".png";
    }

    @Override
    public String toString() {
        // need to + "" to not need a typecast
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

    public boolean isfaceUp() {
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
