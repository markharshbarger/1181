import java.util.ArrayList;

interface HandObserver {
    void handChange();
}

public class BlackJack {
    private Hand playerHand;
    private HouseHand houseHand;
    private DeckOfCards mainDeck;
    private int bank;
    private HandObserver handObserver;

    // set later
    public BlackJack() {

    }

    public BlackJack(HandObserver handObserver) {
        playerHand = new Hand();
        this.handObserver = handObserver;
        houseHand = new HouseHand();
        mainDeck = new DeckOfCards();
        bank = 300;
    }

    public void play() {
        deal();
        // reveal();
        // bet();
        // houseReveal();
        // houseBet();
        // System.out.println(winner());
        // pause();
        // clearHand();
    }

    private void deal() {
        for (int i = 0; i < 2; ++i) {
            houseHand.addCard(mainDeck.drawCard());
            playerHand.addCard(mainDeck.drawCard());
            handObserver.handChange();
        }
    }

    private void reveal() {
        
    }

    private void bet() {
        while (playerHand.totalHandValue() < 21) {
            System.out.println("Hit or Stand");
            String bet = userInput.nextLine();
            if (userValidateBet(bet)) {
                playerHand.addCard(mainDeck.drawCard());
                playerHandObserver.handChange();
                if (playerHand.getBust()) {
                    System.out.println("Bust");
                }
            } else {
                reveal();
                break;
            }
        }
    }

    private boolean userValidateBet(String bet) {
        while (true) {
            bet = bet.toLowerCase();
            if (bet.charAt(0) == 'h') {
                return true;
            } else if (bet.charAt(0) == 's') {
                return false;
            } else {
                System.out.println("Please type one");
                bet = userInput.nextLine(); // gets bet again
            }
        }
    }

    private void houseReveal() {
        clearScreen();
        System.out.print("You ");
        for (Card card : playerHand.returnHand()) {
            System.out.print(card + " ");
        }

        System.out.print(": total " + playerHand.totalHandValue() + "     |     House ");
        for (Card card : houseHand.returnHand()) {
            System.out.print(card + " ");
        }
        System.out.println(" : total " + houseHand.totalHandValue());
    }

    private void houseBet() {
        while (houseHand.totalHandValue() < 17) {
            houseHand.addCard(mainDeck.drawCard());
            houseReveal();
            if (houseHand.getBust()) {
                System.out.println(" House Bust");
            }
        }
    }

    private String winner() {
        if (houseHand.getBust() && playerHand.getBust()) {
            return "Everyone lost";
        } else if (houseHand.totalHandValue() == playerHand.totalHandValue()) {
            return "Tie";
        } else if (houseHand.getBust() && !playerHand.getBust()) {
            return "You win";
        } else if (!houseHand.getBust() && playerHand.getBust()) {
            return "House wins";
        } else if (houseHand.totalHandValue() > playerHand.totalHandValue()) {
            return "House wins";
        } else {
            return "You win";
        }
    }

    private void clearHand() {
        playerHand.clearHand();
        playerHandObserver.handChange();
        houseHand.clearHand();
    }

    private void pause() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        } 
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand.getHand();
    }
}
