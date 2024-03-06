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
        houseReveal();
        pause(.75);
        System.out.println("paused");
        // houseBet includes pauses
        houseBet();
        revealWinner();
        pause(1.5);
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
            pause(.5);
            if (playerHand.value() >= 21) {
                stand();
            }
            return;
        }
    }

    public void stand() {
        GUI.setHitAndStandButton(false);
        houseTurn();
    }

    private void houseReveal() {
        houseHand.getCard(0).setFaceUp(true);
        househandObserver.houseHandChange();
        pause(1);
    }

    private void houseBet() {
        while (houseHand.value() < 17) {
            pause(.5);
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

        GUI.setBank(bank);
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

    private <T extends Number> void pause(T value) {
        int millisecond = (int)(value.doubleValue() * 1000);
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        } 
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
