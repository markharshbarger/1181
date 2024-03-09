import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

interface HandObserver {
    void handChange();
}

interface HouseHandObserver {
    void houseHandChange();
}

public class BlackJack {
    private Hand playerHand;
    private Hand houseHand;
    private DeckOfCards mainDeck;
    private int bank;
    private HandObserver handObserver;
    private HouseHandObserver househandObserver;
    private GameWindow GUI;
    private int betAmount;
    private Timer houseTimer;

    // set later
    public BlackJack() {

    }

    public BlackJack(HandObserver handObserver, HouseHandObserver houseHandObserver) {
        playerHand = new Hand();
        this.handObserver = handObserver;
        this.househandObserver = houseHandObserver;
        houseHand = new Hand();
        mainDeck = new DeckOfCards();
        bank = 300;
        betAmount = 30;

    }

    public void play() {
        clearHand();
        bank -= betAmount;
        GUI.setBank(bank);
        deal();
        getPlayerInput();
        // clearHand();
    }

    public void houseTurn() {

        pause(2, this::houseReveal);
        System.out.println("paused");
        // houseBet includes pauses
        houseBet();
        revealWinner();
        GUI.setBetAndSpinner(true);
    }

    private void deal() {
        houseHand.addCard(mainDeck.drawCardFaceDown());
        
        playerHand.addCard(mainDeck.drawCard());
        houseHand.addCard(mainDeck.drawCard());
        playerHand.addCard(mainDeck.drawCard());

        househandObserver.houseHandChange();
        handObserver.handChange();
    }


    public void hit() {
        if (playerHand.value() < 21) {
            playerHand.addCard(mainDeck.drawCard());
            handObserver.handChange();
            // pause(.5);
            // checks if hand is 21 or over after adding card
            if (playerHand.value() >= 21) {
                stand();
            }
            return;
        }
    }

    public void stand() {
        GUI.setHitAndStandButton(false);

        // find source
        pause(5, this::houseTurn);
    }

    private void houseReveal() {
        houseHand.getCard(0).setFaceUp(true);
        househandObserver.houseHandChange();
    }

    private void houseBet() {
        while (houseHand.value() < 17) {
            // pause(2);
            houseHand.addCard(mainDeck.drawCard());
            if (houseHand.getBust()) {
                System.out.println(" House Bust");
            }
            System.out.println(houseHand.value());
        }
        househandObserver.houseHandChange();
    }

    private void revealWinner() {
        // bet has already been subtracted from bank
        if (houseHand.getBust() && playerHand.getBust()) {
            System.out.println("Both lost");
        } else if (houseHand.value() == playerHand.value()) {
            bank += (betAmount);
            System.out.println("Tie");
        } else if (houseHand.getBust() && !playerHand.getBust()) {
            bank += (betAmount * 2);
            System.out.println("You win");
        } else if (!houseHand.getBust() && playerHand.getBust()) {
            System.out.println("House wins");
        } else if (houseHand.value() > playerHand.value()) {
            System.out.println("House wins");
        } else {
            bank += (betAmount * 2);
            System.out.println("You win");
        }
        System.out.println("hello world");

        GUI.setBank(bank);
        return;
        // add bet button next
        // pause(1.5);
        // clearHand();
        // pause(0.2);
        // play();
    }

    private void clearHand() {
        playerHand.clearHand();
        houseHand.clearHand();
        handObserver.handChange();
        househandObserver.houseHandChange();
    }

    private void getPlayerInput() {
        if (playerHand.value() >= 21) {
            stand();
            return;
        }
        GUI.setHitAndStandButton(true);
    }

    private void pause(int seconds, Runnable method) {
        // Timer houseTimer;
        int millisecond = (int)(seconds * 1000);
        houseTimer = new Timer(millisecond, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                houseTimer.stop();
                method.run();
            }
        });
        houseTimer.start();
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
}
