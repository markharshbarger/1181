// 'https://www.baeldung.com/java-enum-values' used to understand more about enums
/**
 * Class represents the rank for playing cards with each enum containing the respective String name and int card value
 */
public enum Rank {

    TWO("Two", 2), 
    THREE("Three", 3), 
    FOUR("Four", 4), 
    FIVE("Five", 5), 
    SIX("Six", 6), 
    SEVEN("Seven", 7),
    EIGHT("Eight", 8), 
    NINE("Nine", 9), 
    TEN("Ten", 10), 
    JACK("Jack", 11), 
    QUEEN("Queen", 12), 
    KING("King", 13), 
    ACE("Ace", 14);

    private String name;
    private int faceValue;
    
    /**
     * Constructor of Rank class, sets the name and face value of cards
     * 
     * @param name String - name of card
     * @param faceValue int - card value
     */
    private Rank(String name, int faceValue) {
        this.name = name;
        this.faceValue = faceValue;
    }

    /**
     * Gets the face value of card
     * 
     * @return int - of card's face value
     */
    public int getFaceValue() {
        return this.faceValue;
    }

    /**
     * Gets the name of card
     * 
     * @return String - of card's name
     */
    public String getName() {
        return this.name;
    }
}
