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
    }

    public void play() {
        deal();
        getPlayerInput();
        // houseReveal();
        // houseBet();
        // // System.out.println(winner());
        // pause(1.7);
        // clearHand();
    }

    public void houseTurn() {
        houseReveal();
        pause(.75);
        houseBet();
    }

    private void deal() {
        houseHand.addCard(mainDeck.drawCardFaceDown());
        
        playerHand.addCard(mainDeck.drawCard());
        houseHand.addCard(mainDeck.drawCard());
        playerHand.addCard(mainDeck.drawCard());

        househandObserver.houseHandChange();
        handObserver.handChange();
    }


    public void bet() {
        if (playerHand.value() < 21) {
            playerHand.addCard(mainDeck.drawCard());
            handObserver.handChange();
            pause(.75);
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
    }

    private void houseBet() {
        while (houseHand.value() < 17) {
            pause(.5);
            houseHand.addCard(mainDeck.drawCard());
            househandObserver.houseHandChange();
            if (houseHand.getBust()) {
                System.out.println(" House Bust");
            }
            System.out.println(houseHand.value());
        }
    }

    // private String winner() {
    //     if (houseHand.getBust() && playerHand.getBust()) {
    //         return "Everyone lost";
    //     } else if (houseHand.totalHandValue() == playerHand.totalHandValue()) {
    //         return "Tie";
    //     } else if (houseHand.getBust() && !playerHand.getBust()) {
    //         return "You win";
    //     } else if (!houseHand.getBust() && playerHand.getBust()) {
    //         return "House wins";
    //     } else if (houseHand.totalHandValue() > playerHand.totalHandValue()) {
    //         return "House wins";
    //     } else {
    //         return "You win";
    //     }
    // }

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
}
