public class BlackJack {
    private Hand playerHand;
    private HouseHand houseHand;
    private DeckOfCards mainDeck;
    private int bank;

    BlackJack() {
        playerHand = new Hand();
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
            playerHand.addCard(mainDeck.drawCard());
            houseHand.addCard(mainDeck.drawCard());
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
                reveal();
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
        houseHand.clearHand();
    }

    private void pause() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        } 
    }
}
