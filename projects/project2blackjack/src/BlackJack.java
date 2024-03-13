import javax.swing.SwingUtilities;

/**
 * Represents an interface for a hand observer that has one method
 */
interface HandObserver {
    void handChange();
}

/**
 * Represents an interface for a house hand observer that has one method
 */
interface HouseHandObserver {
    void houseHandChange();
}

/**
 * Represents an interface for the end of the round observer that has one method
 */
interface EndOfRoundObserver {
    void endOfRound();
}

/**
 * Represents an interface for a bank observer that has one method
 */
interface BankObserver {
    void bankChange();
}

/**
 * Represents an interface for a high score observer that has one method
 */
interface HighScoreObserver {
    void newHighScore();
}

/**
 * Clase represents the logic of the game Black Jack, to start a round invoke the play() method. From there have user invoke
 * the hit() or stand() method, unless the player hand equals 21 which will then switch it off to the house's turn. Class has
 * most logic and GUI seperated but still contains some for enabling/disabling buttons, would have to either get rid or implement
 * them if trying to use a different GUI.
 */
public class BlackJack {
    private Hand playerHand;
    private Hand houseHand;
    private DeckOfCards mainDeck;
    private int bank;
    private int highScore;
    private HandObserver handObserver;
    private HouseHandObserver househandObserver;
    private EndOfRoundObserver endOfRoundObserver;
    private BankObserver bankObserver;
    private HighScoreObserver highScoreObserver;
    private GameWindowGUI GUI;
    private int betAmount;
    private String roundStat;

    /**
     * Consturctor of BlackJack class, sets bank to 300, original bet amount to 30, and high score to bet amount. Includes
     * Observers that allows applications to notify any outside class that implements the interfaces when the respective
     * action occurs. Also initilizes a new deck of cards.
     * 
     * @param handObserver - an observer for the player hand
     * @param houseHandObserver - an observer for the house's hand
     * @param endOfRoundObserver - an observer for the end of round
     * @param bankObserver - an observer for the bank
     * @param highScoreObserver - an observer for the high score
     */
    public BlackJack(HandObserver handObserver, HouseHandObserver houseHandObserver, EndOfRoundObserver endOfRoundObserver,
                        BankObserver bankObserver, HighScoreObserver highScoreObserver) {
        playerHand = new Hand();
        this.handObserver = handObserver;
        this.househandObserver = houseHandObserver;
        this.endOfRoundObserver = endOfRoundObserver;
        this.bankObserver = bankObserver;
        this.highScoreObserver = highScoreObserver;
        houseHand = new Hand();
        mainDeck = new DeckOfCards();
        bank = 300;
        betAmount = 30;
        highScore = bank;
    }

    /**
     * Begins the round of Black Jack, subtracts the bet amount from the bank and notifies the respective observer.
     * Then calls for the player intput
     */
    public void play() {
        clearHands();
        bank -= betAmount;
        bankObserver.bankChange();
        deal();
        getPlayerInput();
    }

    /**
     * Gets player input, initilizes GUI buttons to enable input. If player hand is 21 or over it automatically calls the stand
     * method.
     */
    private void getPlayerInput() {
        if (playerHand.value() >= 21) {
            stand();
            return;
        }
        GUI.setHitAndStandButton(true);
    }

    /**
     * Initilizes GUI buttons to disable input and goes to houseTurn method. Uses SwingUtilities for a better experience
     */
    public void stand() {
        GUI.setHitAndStandButton(false);
        SwingUtilities.invokeLater(() -> houseTurn());
    }

    /**
     * Gives the player another card and notifies hand observer, if player hand is 21 or over after adding card to player
     * hand, calls the stand method
     */
    public void hit() {
        if (playerHand.value() < 21) {
            playerHand.addCard(mainDeck.drawCard());
            handObserver.handChange();

            // checks if hand is 21 or over after adding card
            if (playerHand.value() >= 21) {
                stand();
            }
        }
    }

    /**
     * Represents the house's turn. Creates a new thread for the house to perform its method in to enable pause in game logic.
     * House reveals its face down card, and keeps drawing cards until it doesn't need to. Reveal's the round winner. Includes
     * pauses inbetween house's actions for better user experience.
     */
    public void houseTurn() {
        //'https://www.codecademy.com/resources/docs/java/threading' had to create a new thread to have the game logic pause,
        // without a new thread it would cause the thread with Swing to pause too which caused glitches
        System.out.println("house turn");
        Thread houseTurnThread = new Thread(() -> {
            pause(.41);
            houseReveal();
            pause(.65);

            // keeps looping until the house doesn't need to hit
            while (houseHit()) {
                pause(.59);
            }

            revealWinner();
        });
        houseTurnThread.start();
    }

    /**
     * Deals two cards to player's hand and two card's to house's hand. One card to house is dealt face down.
     * Notifies house and hand observer
     */
    private void deal() {
        houseHand.addCard(mainDeck.drawCardFaceDown());
        
        playerHand.addCard(mainDeck.drawCard());
        houseHand.addCard(mainDeck.drawCard());
        playerHand.addCard(mainDeck.drawCard());

        househandObserver.houseHandChange();
        handObserver.handChange();
    }

    /**
     * Reveals the face down card in house's hand by flipping it face up. Notifies house hand observer
     */
    private void houseReveal() {
        houseHand.getCard(0).setFaceUp(true);
        househandObserver.houseHandChange();
    }

    /**
     * Draws a card from the deck, if the house's hand value is under 17. Notifies house hand observer
     * 
     * @return true if hand value before drawing card is under 17, false otherwise
     */
    private boolean houseHit() {
        if (houseHand.value() < 17) {
            houseHand.addCard(mainDeck.drawCard());
            househandObserver.houseHandChange();
            
            //print to terminal
            if (houseHand.getBust()) {
                System.out.println("House Bust");
            }
            return true;
        }
        return false;
    }

    /**
     * Reveals the winner and adds the respective amount of money to the player's bank, unless they don't get any.
     * Updates high score and notifies bank and end of round observers. Enables the GUI bet buttons
     */
    private void revealWinner() {
        // bet has already been subtracted from bank
        if (houseHand.getBust() && playerHand.getBust()) {
            roundStat = "Both Lose";
            System.out.println("Both lost");
        } else if (houseHand.value() == playerHand.value()) {
            bank += (betAmount);
            roundStat = "Tie";
            System.out.println("Tie");
        } else if (houseHand.getBust() && !playerHand.getBust()) {
            bank += (betAmount * 2);
            roundStat = "You Win";
            System.out.println("You win");
        } else if (!houseHand.getBust() && playerHand.getBust()) {
            roundStat = "House Wins";
            System.out.println("House wins");
        } else if (houseHand.value() > playerHand.value()) {
            roundStat = "House Wins";
            System.out.println("House wins");
        } else {
            roundStat = "You Win ";
            bank += (betAmount * 2);
            System.out.println("You win");
        }
        if (bank > highScore) {
            highScore = bank;
            highScoreObserver.newHighScore();
        }
        endOfRoundObserver.endOfRound();
        bankObserver.bankChange();
        GUI.setBetAndSpinner(true);
    }

    /**
     * Clears the hand for both the player and the house. Notifies player and house hand observers
     */
    private void clearHands() {
        playerHand.clearHand();
        houseHand.clearHand();
        handObserver.handChange();
        househandObserver.houseHandChange();
    }

    /**
     * Gets the player's hand of cards
     * 
     * @return Hand that contains cards
     */
    public Hand getPlayerHand() {
        return playerHand;
    }

    /**
     * Gets the house's hand of cards
     * 
     * @return Hand that contains cards
     */
    public Hand getHouseHand() {
        return houseHand;
    }

    /**
     * Gets the bank amount
     * 
     * @return int - bank
     */
    public int getBank() {
        return bank;
    }

    /**
     * Add a GUI to the game logic
     * 
     * @param GUI the GUI that contains the game GUI
     */
    public void addGUI(GameWindowGUI GUI) {
        this.GUI = GUI;
    }

    /**
     * Sets the bet amount
     * 
     * @param value int - the amount to bet
     */
    public void setBetAmount(int value) {
        if (betAmount < bank) {
            betAmount = value;
        } else {
            betAmount = bank;
        }
    }

    /**
     * Gets the bet amount
     * 
     * @return int - the amount being bet
     */
    public int getBetAmount() {
        return betAmount;
    }

    /**
     * Generic class that pauses the current thread that is being used for the amount requested (in seconds)
     *  
     * @param <T> - any value that extends Number
     * @param value - is in seconds
     */
    private <T extends Number> void pause(T value) {
        int millisecond = (int)(value.doubleValue() * 1000);
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Get's the result of the last round; who won or lost
     * 
     * @return
     */
    public String getRoudStat() {
        return roundStat;
    }

    /**
     * Initilizes a 'new game' by setting the bank to 300 and notifies bank observer
     */
    public void newGame() {
        bank = 300;
        bankObserver.bankChange();
    }

    /**
     * Gets the current high score of the game
     * 
     * @return int - the value of highScore
     */
    public int getHighScore() {
        return highScore;
    }
}
