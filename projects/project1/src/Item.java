/**
 * Item class represents an object with a name, weight, value, and also a boolean if the item is included
 * Includes a deep copy constructor
 */
public class Item {
    private final String name;
    private final double weight;
    private final int value;
    private boolean included;
    
    /**
     * Constructor that sets a new item with the param given, sets included to boolean
     * 
     * @param name - String that represents items name
     * @param weight - double that represents the items weight
     * @param value - int that represents the items value
    */
    public Item(String name, double weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        included = false;
    }

    /**
     * Copy constructor that makes a deep copy of the param given
     * 
     * @param other - Item that is the one that is being copied
     */
    public Item(Item other) {
        this.name = other.name;
        this.weight = other.weight;
        this.value = other.value;
        this.included = other.included;
    }

    /**
     * Gets weight of Item
     * @return the Item's weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the value of the Item
     * @return the Item's value
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the value of included boolean
     * @return true if true, else false
     */
    public boolean isIncluded() {
        return included;
    }

    /**
     * Sets the included value to the boolean param
     * @param included - boolean, true if included, false otherwise
     */
    public void setIncluded(boolean included) {
        this.included = included;
    }

    /**
     * Returns a String representation of Item
     * @return - String that is in the format of "'name' ('weight' lbs, $ 'value')"
     */
    @Override
    public String toString() {
        return name + " (" + weight + " lbs, $" + value + ")";
    }
}

