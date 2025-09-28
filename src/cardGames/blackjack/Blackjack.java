package cardGames.blackjack;

import cardGames.cards.Card;
import cardGames.cards.Deck;
import cardGames.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Blackjack {
    public void blackjackGameLoop() {
        Deck deck = new Deck();
        Scanner scanner = new Scanner(System.in);
        Player[] players = getPlayers(scanner);
        boolean keepPlaying = true;
        while (keepPlaying) {
            turn(players, deck, scanner);
            System.out.println("Keep playing?");
            keepPlaying = confirm(scanner);
            if (!keepPlaying) {
                System.out.println("Final scores:");
                for (Player player : players) {
                    System.out.println(player.toString());
                }
            }
        }


    }

    public void turn(Player[] players, Deck deck, Scanner scanner) {
        deck.shuffle();
        BlackjackHand dealerHand = dealerHand(deck);
        int maxValidPlayerScore = 0;
        for (Player player : players) {
            if (player.getChips().getTotal() > 0) {
                System.out.printf("%s's turn!\n", player.getName());
                player.setHand(new BlackjackHand());
                player.getHand().addCard(deck.deal());
                player.getHand().addCard(deck.deal());
                placePlayerBet(player, scanner);
                doubleDown(player, scanner);
                if (player.getHand().getValue() == 21) {
                    int blackjackWinnings = (int) (player.getHand().getBet() * 1.5);
                    System.out.printf("Blackjack! %s wins %d chips!\n", player.getName(), blackjackWinnings);
                    player.getChips().add(blackjackWinnings);
                    player.getHand().resetBet();
                    continue;
                }
                hitOrStand(player, scanner, deck);
                if (player.getHand().getValue() > 21) {
                    System.out.println("Bust! You lose!");
                    player.getHand().resetBet();
                }
                if (player.getHand().getValue() > maxValidPlayerScore && player.getHand().getValue() <= 21) {
                    maxValidPlayerScore = player.getHand().getValue();
                }
            }
        }
        int dealerScore = dealerTurn(dealerHand, maxValidPlayerScore, deck);
        for (Player player : players) {
            if (player.getHand().getBet() == 0) {
                continue;
            }
            if (dealerScore > 21) {
                System.out.printf("Dealer bust! %s wins %d chips!\n", player.getName(), player.getHand().getBet() * 2);
                player.getChips().add(player.getHand().getBet() * 2);
                player.getHand().resetBet();
            } else if (player.getHand().getValue() == dealerScore) {
                System.out.println("Draw! Bet is refunded.");
                player.getChips().add(player.getHand().getBet());
                player.getHand().resetBet();
            } else if (player.getHand().getValue() > dealerScore) {
                System.out.printf("You win! %s wins %d chips!\n", player.getName(), player.getHand().getBet() * 2);
                player.getChips().add(player.getHand().getBet() * 2);
                player.getHand().resetBet();
            } else {
                System.out.printf("%s loses: %d to dealer's %d\n", player.getName(), player.getHand().getValue(), dealerScore);
            }
        }

    }

    public int chooseChips(Scanner scanner) {
        while (true) {
            try {
                String line = scanner.nextLine();
                int amount = Integer.parseInt(line);
                if (amount > 0) {
                    return amount;
                }
                System.out.println("Please enter a positive amount.");
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number. Please try again.");
            }
        }
    }

    private void placePlayerBet(Player player, Scanner scanner) {
        int bet;
        do {
            System.out.printf("Please enter your bet. You have %d chips to bet: \n", player.getTotalChips());
            bet = chooseChips(scanner);
            while (bet > player.getTotalChips()) {
                System.out.printf("Invalid bet. Please enter an amount between 1 and %d:\n", player.getTotalChips());
                chooseChips(scanner);
            }
            System.out.printf("Are you happy to bet %d?\n", bet);
        } while (!confirm(scanner));
        player.getHand().placeBet(bet);
        player.getChips().remove(bet);
    }

    private BlackjackHand dealerHand(Deck deck) {
        BlackjackHand dealerHand = new BlackjackHand();
        dealerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());
        System.out.println("Dealer is showing: " + dealerHand.showFirstCard());
        return dealerHand;
    }

    private int dealerTurn(BlackjackHand dealerHand, int maxValidPlayerScore, Deck deck) {
        while (dealerHand.getValue() <= maxValidPlayerScore && dealerHand.getValue() < 21) {
            dealerHand.addCard(deck.deal());
        }
        System.out.printf("Dealer's hand: %s\n", dealerHand);
        return dealerHand.getValue();
    }

    private Player[] getPlayers(Scanner scanner){
        List<Player> players = new ArrayList<>();
        do {
            String name = chooseName(scanner);
            do {
                System.out.printf("Are you happy with the name '%s'?\n", name);
                boolean confirmation = confirm(scanner);
                if (confirmation) {
                    break;
                }
                name = chooseName(scanner);
            } while (true);
            int chips = chooseStartingChipsAmount(scanner);
            do {
                System.out.printf("Start with %d chips?\n", chips);
                boolean confirmation = confirm(scanner);
                if (confirmation) {
                    break;
                }
                chips = chooseStartingChipsAmount(scanner);
            } while (true);
            Player newPlayer = new Player(name, chips);
            newPlayer.setHand(new BlackjackHand());
            players.add(newPlayer);
            System.out.println("Would you like to add another player?");
        } while (confirm(scanner));
        return players.toArray(new Player[0]);
    }

    private String chooseName(Scanner scanner){
        System.out.println("Please enter player name: ");
        String name = scanner.nextLine();
        while (name.isEmpty()) {
            System.out.println("Please enter a valid name: ");
            name = scanner.nextLine();
        }

        return name;
    }

    private int chooseStartingChipsAmount(Scanner scanner){
        System.out.println("Please enter player starting chips: ");
        return chooseChips(scanner);
    }

    public boolean confirm(Scanner scanner){
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
            return true;
        } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
            return false;
        } else {
            System.out.println("Please enter a valid input - either y/yes or n/no.");
            return confirm(scanner);
        }
    }

    private void doubleDown(Player player, Scanner scanner) {
        if (player.getHand().getBet() * 2 <= player.getTotalChips()) {
            System.out.print("Your first card is: ");
            System.out.println(player.getHand().showFirstCard());
            System.out.println("Would you like to double down?");
            if (confirm(scanner)) {
                player.getChips().remove(player.getHand().getBet());
                player.getHand().doubleDown();
            }
        }
    }

    private void hitOrStand(Player player, Scanner scanner, Deck deck) {
        String answer = "";
        while (!answer.equalsIgnoreCase("stand") && player.getHand().getValue() < 21) {
            System.out.printf("Your current hand: %s\nHit or stand?\n", player.getHand().toString());
            answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("hit")) {
                Card newCard = deck.deal();
                player.getHand().addCard(newCard);
                System.out.printf("You are dealt a %s.\n", newCard.toString(), player.getHand().toString());
            } else if  (answer.equalsIgnoreCase("stand")) {
                System.out.printf("Your final hand total: %d\n", player.getHand().getValue());
            } else {
                System.out.println("Please enter either 'hit' or 'stand' to continue.");
            }
        }
    }
}
