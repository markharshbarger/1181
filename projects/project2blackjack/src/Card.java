public class Card {
    private final Rank rank;
    private final Suit suit;
    private final String imageFolderLocation = "src/CardImages/"; // the folder that contains all the image files for the cards
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

    /**
     * Gets cards rank
     * 
     * @return cards rank
     */
    public Rank getRank() {
        return this.rank;
    }

    /**
     * Gets the cards suit
     * 
     * @return cards suit
     */
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Gets the cards file location for the image, if face down the image will be the back of card else it will be a picture
     * of the top of the card
     * 
     * @return file location String
     */
    public String getFileLocation() {
        return this.fileLocation;
    }

    /**
     * Gets if the card is face up or not, if the cards file location for the image is unique or if its a back of the card
     * 
     * @return true if card is face up, false if card is face down
     */
    public boolean isFaceUp() {
        return faceUp;
    }

    /**
     * Sets the card file location to either the unique card image file or an image of the back of the card
     * 
     * @param value - true for a unique card image file (face up), or false for image file of the back of the card
     */
    public void setFaceUp(boolean value) {
        this.faceUp = value;

        if (value == false) {
            fileLocation = this.imageFolderLocation + "BACK_CARD.png";
        } else {
            fileLocation = getFaceUpFileLocation();
        }
    }
}
