package cardGames.blackjack;

import cardGames.cards.Card;
import cardGames.cards.Deck;
import cardGames.player.Hand;
import cardGames.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Blackjack {

    public void blackjackGameLoop() {
        Deck deck = new Deck();
        Scanner scanner = new Scanner(System.in);
        Player[] players = getPlayers(scanner);



    }

    public void turn(Player[] players, Deck deck, Scanner scanner) {
        Hand dealerHand = dealerHand(deck);
        for (Player player : players) {
            player.setHand(new BlackjackHand());
            placePlayerBet(player, scanner);
        }
    }

    private void placePlayerBet(Player player, Scanner scanner) {
        int bet;
        do {
            System.out.printf("Please enter your bet. You have %d chips to bet: \n", player.getTotalChips());
            bet = scanner.nextInt();
            while (bet <= 0 || bet > player.getTotalChips()) {
                System.out.printf("Invalid bet. Please enter an amount between 1 and %d:\n", player.getTotalChips());
                bet = scanner.nextInt();
            }
            System.out.printf("Are you happy to bet %d?\n", bet);
        } while (!confirm(scanner));
        player.getHand().placeBet(bet);
    }

    private Hand dealerHand(Deck deck) {
        Hand dealerHand = new BlackjackHand();
        dealerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());
        System.out.println("Dealer is showing: " + dealerHand.showFirstCard());
        return dealerHand;
    }

    private Player[] getPlayers(Scanner scanner){
        List<Player> players = new ArrayList<>();
        do {
            String name = chooseName(scanner);
            do {
                System.out.printf("Are you happy with the name '%s'?", name);
                boolean confirmation = confirm(scanner);
                if (confirmation) {
                    break;
                }
                name = chooseName(scanner);
            } while (true);
            int chips = chooseStartingChipsAmount(scanner);
            do {
                System.out.printf("Start with %d chips?", chips);
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
        int amount = scanner.nextInt();
        while (amount <= 0) {
            System.out.println("Please enter a valid amount: ");
            amount = scanner.nextInt();
        }

        return amount;
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
}
