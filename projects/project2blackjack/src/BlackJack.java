import java.util.ArrayList;

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
    private boolean stand;
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
        stand = false;
        bank = 300;
    }

    public void play() {
        deal();

        GUI.setHitAndStandButton(true);
        while (GUI.standButton() == true) {
            if (playerHand.value() > 21) {
                break;
            }
        }
        GUI.setHitAndStandButton(false);

        houseReveal();
        // houseBet();
        // System.out.println(winner());
        // pause();
        // clearHand();
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
                if (playerHand.getBust()) {
                    System.out.println("Bust");
                }
            handObserver.handChange();
        }
    }

    private void houseReveal() {
        houseHand.getCard(0).setFaceUp(true);
        househandObserver.houseHandChange();
    }

    //     System.out.print(": total " + playerHand.totalHandValue() + "     |     House ");
    //     for (Card card : houseHand.returnHand()) {
    //         System.out.print(card + " ");
    //     }
    //     System.out.println(" : total " + houseHand.totalHandValue());
    // }

    // private void houseBet() {
    //     while (houseHand.totalHandValue() < 17) {
    //         houseHand.addCard(mainDeck.drawCard());
    //         houseReveal();
    //         if (houseHand.getBust()) {
    //             System.out.println(" House Bust");
    //         }
    //     }
    // }

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

    // private void clearHand() {
    //     playerHand.clearHand();
    //     playerHandObserver.handChange();
    //     houseHand.clearHand();
    // }

    private void pause() {
        try {
            Thread.sleep(3000);
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
