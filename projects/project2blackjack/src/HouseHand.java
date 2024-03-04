public class HouseHand extends Hand {
    public Card revealOne() {
        if (numOfCards() > 0) {
            return getCard(0);
        }
        return null;
    }

    public int totalOfRevealed() {
        return cardValues.get(getCard(0).getRank());
    }
}
