import javax.swing.SwingUtilities;

interface HandObserver {
    void handChange();
}

interface HouseHandObserver {
    void houseHandChange();
}

interface EndOfRoundObserver {
    void endOfRound();
}

interface BankObserver {
    void bankChange();
}

interface HighScoreObserver {
    void newHighScore();
}

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
    private GameWindow GUI;
    private int betAmount;
    private String roundStat;

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

    public void play() {
        clearHand();
        bank -= betAmount;
        bankObserver.bankChange();
        deal();
        getPlayerInput();
    }

    private void getPlayerInput() {
        if (playerHand.value() >= 21) {
            stand();
            return;
        }
        GUI.setHitAndStandButton(true);
    }

    public void stand() {
        GUI.setHitAndStandButton(false);
        SwingUtilities.invokeLater(() -> houseTurn());
    }

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

    //'https://www.codecademy.com/resources/docs/java/threading' had to create a new thread to have the game pause, without a new thread it would cause the thread
    // with Swing to stop too which caused glitches
    public void houseTurn() {
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

    private void deal() {
        houseHand.addCard(mainDeck.drawCardFaceDown());
        
        playerHand.addCard(mainDeck.drawCard());
        houseHand.addCard(mainDeck.drawCard());
        playerHand.addCard(mainDeck.drawCard());

        househandObserver.houseHandChange();
        handObserver.handChange();
    }

    private void houseReveal() {
        houseHand.getCard(0).setFaceUp(true);
        househandObserver.houseHandChange();
    }

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

    private void clearHand() {
        playerHand.clearHand();
        houseHand.clearHand();
        handObserver.handChange();
        househandObserver.houseHandChange();
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getHouseHand() {
        return houseHand;
    }

    public int getBank() {
        return bank;
    }

    public void addGUI(GameWindow GUI) {
        this.GUI = GUI;
    }

    public void setBetAmount(int value) {
        if (betAmount < bank) {
            betAmount = value;
        } else {
            betAmount = bank;
        }
    }

    public int getBetAmount() {
        return betAmount;
    }

    private <T extends Number> void pause(T value) {
        int millisecond = (int)(value.doubleValue() * 1000);
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public String getRoudStat() {
        return roundStat;
    }

    public void newGame() {
        bank = 300;
        bankObserver.bankChange();
    }

    public int getHighScore() {
        return highScore;
    }
}
