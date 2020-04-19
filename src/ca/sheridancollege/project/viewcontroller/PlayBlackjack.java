package ca.sheridancollege.project.viewcontroller;

import ca.sheridancollege.project.model.*;
import java.util.*;

/**
 * 
 * @author Aaqib and Parneet
 */
public class PlayBlackjack {

    // width of the CLI interface (monospace characters)
    private final int SCREEN_WIDTH = 80;
    
    /**
     * 
     * @param args 
     */
    public static void main(String args[]) {
        printIntro();
        //create game object
        BlackjackGame blackjack = new BlackjackGame();
        play(blackjack);
    }//end of main()
    
    
    
    /**
     * Prints a graphic-like title card for the game
     */
    private static void printIntro() {
        
        System.out.println(""
        +"  oooooooooo.  oooo                      oooo         "+"\n"
        +"  `888'   `Y8b `888                      `888         "+"\n"
        +"   888     888  888   .oooo.    .ooooo.   888  oooo   "+"\n"
        +"   888oooo888'  888  `P  )88b  d88' `'Y8  888 .8P'    "+"\n"
        +"   888    `88b  888   .oP'888  888        888888.     "+"\n"
        +"   888    .88P  888  d8(  888  888   .o8  888 `88b.   "+"\n"
        +"  o888bood8P'  o888o `Y888''8o `Y8bod8P' o888o o888o  "+"\n"
        +"\n"
        +"                 o8o                     oooo         "+"\n"
        +"         ♠       ```                     `888         "+"\n"
        +"    ♠           oooo  .oooo.    .ooooo.   888  oooo   "+"\n"
        +"         ♣      `888 `P  )88b  d88' `'Y8  888 .8P'    "+"\n"
        +"    ♣            888  .oP'888  888        888888.     "+"\n"
        +"         ♥       888 d8(  888  888   .o8  888 `88b.   "+"\n"
        +"    ♥            888 `Y888''8o `Y8bod8P' o888o o888o  "+"\n"
        +"         ♦       888                                  "+"\n"
        +"    ♦        .o. 88P        Swaranjit Singh  and      "+"\n"
        +"             `Y888P         Desire Richards-Campbell  "+"\n"
        +"\n");
    }
    
    
    /**
     * Initiates the bulk of the game logic/order
     * @param blackjack The current game object
     */
    private static void play(BlackjackGame blackjack) {
        setup(blackjack);
        getBets(blackjack);
        dealNewHands(blackjack);
        checkForDealerNatural(blackjack);
        playerTurns(blackjack);
        settlement(blackjack);
        endGame(blackjack);
    }


    
    /**
     * 
     * @param blackjack The current game object
     */
    private static void setup(BlackjackGame blackjack) {
        // SET UP PLAYERS //////////////////////////////////////////////////////
        
        //prompt for number of players:
        int numPlayers = readUserInt("How many players for this game: ");
        if (numPlayers < 1) {
            System.exit(0);
        }
        
        //get player names:
        ArrayList<Player> players = new ArrayList();
        for (int i = 0; i < numPlayers; i++) {
            //prompt user:
            String name = readUserLine("Player "+ (i+1) +"'s name: ");
            //catch exception here:
            try {
                Player newPlayer = new BlackjackPlayer(name);
                players.add(newPlayer);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                //try same player again
                i--;
            }
        }
        //Create dealer //add to blackjack.players in last position
        players.add(new BlackjackDealer());
        //set new list of players to the blackjack object
        blackjack.setPlayers(players);
    }

    /**
     * 
     * @param blackjack The current game object
     */
    private static void getBets(BlackjackGame blackjack) {
        // NEW HAND BETTING ////////////////////////////////////////////////////
        
        //loop through all players //bet amount? //decrease balance //create empty hand
        for (Player p : blackjack.getPlayers()) {
            if(p instanceof BlackjackDealer){
                //dealer doesn't have a real bet
                BlackjackDealer dealer = (BlackjackDealer)p;
                BlackjackHand newHand = new BlackjackHand(0.0);
                dealer.addHand(newHand);
            }else{
                //downcast player object
                BlackjackPlayer player = (BlackjackPlayer)p;
                //prompt player for bet amount:
                String prompt = player.getPlayerID() +" ($"+ player.getBalance() +"), how much to bet on this hand: ";
                double bet = readUserDouble(prompt);
                //decrease balance:
                try {
                    double newBalance = player.getBalance() - bet;    
                    player.setBalance(newBalance);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //set bet to hand:
                BlackjackHand newHand = new BlackjackHand(bet);
                //System.out.println(newHand);
                player.addHand(newHand);
            }
        }
    }

    /**
     * 
     * @param blackjack The current game object
     */
    private static void dealNewHands(BlackjackGame blackjack) {
        // NEW HAND DEALING ////////////////////////////////////////////////////
        
        for (int i = 0; i < 2; i++) {
            for (Player p : blackjack.getPlayers()) {
                //downcast player
                BlackjackPlayer player = (BlackjackPlayer)p;
                //players only have one hand each, hit once
                blackjack.hit(player.getHands().get(0));
            }
        }
    }

    /**
     * 
     * @param blackjack The current game object
     */
    private static void checkForDealerNatural(BlackjackGame blackjack) {
        // BEFORE FIRST TURN ///////////////////////////////////////////////////

        //check dealer hand for possible natural

        //segregate dealer object for easier use
        BlackjackDealer dealer = null;
        BlackjackCard visibleCard = null;
        try {
            dealer = blackjack.getDealer();
            visibleCard = (BlackjackCard)dealer.getHands().get(0).showCards().get(0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //if we don't have a dealer, end the game
            System.exit(0);
        }

        //check dealer's hand's first card (second card should be face down)
        boolean isInsurance = false;
        if (visibleCard.getRank() == Rank.ACE) {
            isInsurance = true;
            insuranceRound(blackjack);
        }//end of if


        //check daler hand for natural
        if (dealer.getHands().get(0).getValue() == 21) {
            //dealer natural -> move to settlement
            System.out.println(dealer.getPlayerID() +" has a natural 21, moving to settlement. ");
            settlement(blackjack);
            endGame(blackjack);
        }else if (isInsurance) {
            System.out.println(dealer.getPlayerID() +" does not have a natural 21, insurance bets are lost and player turns continue. ");
        }
    }
    
    /**
     * 
     * @param blackjack The current game object
     */
    private static void insuranceRound(BlackjackGame blackjack) {
        //prompt players for insurance bets
        System.out.println("Dealer has an ACE, and might have a natural 21. Do you want to place an insurance bet?");
        for (Player p : blackjack.getPlayers()) {
            if(p instanceof BlackjackDealer){
                //dealer does nothing
            }else{
                //downcast player object
                BlackjackPlayer player = (BlackjackPlayer)p;
                BlackjackHand hand = player.getHands().get(0);
                double bet = hand.getBet();
                double halfBet = roundToCent(bet / 2);
                //prompt player for bet amount:
                String prompt = String.format("%s ($%.2f), place an insurance bet of $%.2f (y/n): ",
                        player.getPlayerID(), player.getBalance(), halfBet);
                if(readUserBoolean(prompt)){
                    try {
                        //decrease balance:
                        double newBalance = player.getBalance() - halfBet;    
                        player.setBalance(newBalance);
                        //set insurance bet
                        hand.setInsuranceBet(halfBet);
                    } catch (Exception e) {
                        System.out.println(e.getMessage()); //gives error for negative balance, TODO change to specific message
                    }
                }
            }
            //ask next player
        }
    }
    
    /**
     * 
     * @param blackjack The current game object
     */
    private static void playerTurns(BlackjackGame blackjack) {
        // PLAYER TURNS ////////////////////////////////////////////////////////


        //segregate dealer object for easier use
        BlackjackDealer dealer = null;
        try {
            dealer = blackjack.getDealer();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //if we don't have a dealer, end the game
            System.exit(0);
        }

        //loop through every player
        for (Player o : blackjack.getPlayers()) {
            if (o instanceof BlackjackDealer) {
                //dealer plays
                //downcast dealer
                dealer = (BlackjackDealer)o;
                BlackjackHand hand = dealer.getHands().get(0);

                //flag to continue this hand
                boolean continueHand = true;

                while(continueHand){
                //show dealer's hand
                System.out.println(dealer.getPlayerID() + hand);
                if (hand.getValue() > 21) {
                        //bust
                        System.out.println(dealer.getPlayerID() +" busts!");
                        continueHand = false;
                    }else if (hand.getValue() >= 17) {
                        //force stand
                        System.out.println(dealer.getPlayerID() +" stands.");
                        continueHand = false;
                    }else{
                        blackjack.hit(hand);
                        System.out.println(dealer.getPlayerID() +" hits!"); 
                    }
                }

            }else{
                //downcast to BlackjackPlayer
                BlackjackPlayer player = (BlackjackPlayer)o;
                //loop through every hand
                //for (BlackjackHand hand : player.getHands()) { 
                /* using a for each loop can lead to a ConcurrentModificationError 
                 * when splitPair() is called, because it changes the arrayList 
                 * that's being used for the iterator. Regular for loop resolves 
                 * this by checking list.size() before each loop.  */
                for (int i = 0; i < player.getHands().size(); i++) {
                    BlackjackHand hand = player.getHands().get(i);

                    //show player's current hand
                    //System.out.println(player.getPlayerID() +" ($"+ player.getBalance() +"): "+ hand);
printCurrentTable(blackjack.getPlayers(),hand); //print all players

                    //flag to continue this hand
                    boolean continueHand = true;

                    //prompt user for input
                    String prompt = player.getPlayerID() +", enter a command (stand, hit, double down, split pair): ";
                    while (continueHand) {

                        if (hand.getValue() > 21) {
                            //bust
                            System.out.println(player.getPlayerID() +" busts!");
                            continueHand = false;
                        }else if (hand.getValue() == 21) {
                            //force stand
                            System.out.println(player.getPlayerID() +" has 21 and stands.");
                            continueHand = false;
                        }else{
                            String userAction = readUserString(prompt).toLowerCase();
                            try {
                                switch (userAction) {
                                    //TODO add messages
                                    case "stand":  
                                        System.out.println(player.getPlayerID() +" stands."); 
                                        continueHand = false; 
                                        break;
                                    case "hit": blackjack.hit(hand); 
                                        System.out.println(player.getPlayerID() +" hits!"); 
                                        //show player's current hand
                                        //System.out.println(player.getPlayerID() +" ($"+ player.getBalance() +"): "+ hand);
                                        break;
                                    case "double": blackjack.doubleDown(player, hand); 
                                        System.out.println(player.getPlayerID() +" doubles down!"); 
                                        //show player's current hand
                                        //System.out.println(player.getPlayerID() +" ($"+ player.getBalance() +"): "+ hand);
                                        continueHand = false; 
                                        break;
                                    case "split": blackjack.splitPair(player, hand); 
                                        System.out.println(player.getPlayerID() +" splits their pair!"); 
                                        //show player's current hand
                                        //System.out.println(player.getPlayerID() +" ($"+ player.getBalance() +"): "+ hand);
                                        break;
                                    default: System.out.println("Command not recognised. ");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
printCurrentTable(blackjack.getPlayers(),hand); //print all players
                        }
                    }//end of while-loop - player has stood or busted
                    //next hand, or next player
                    System.out.println("");
                }//end of hands loop
            }//end of if-dealer
        }//end of players loop
    }

    
    
    
    private static void settlement(BlackjackGame blackjack) {
        // SETTLEMENT //////////////////////////////////////////////////////////

        BlackjackDealer dealer = null;
        try {
            dealer = blackjack.getDealer();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //if we don't have a dealer, end the game
            System.exit(0);
        }
        BlackjackHand dealerHand = dealer.getHands().get(0);

        //loop through every player
        for (Player p : blackjack.getPlayers()) {
            if (p instanceof BlackjackDealer) {
            //do nothing for dealer
            }else{
                //downcast to BlackjackPlayer
                BlackjackPlayer player = (BlackjackPlayer)p;
                //loop through every hand
                for (BlackjackHand hand : player.getHands()) {

                    double winnings = 0;

                    if (dealerHand.getValue() == 21 && dealerHand.showCards().size() == 2) {
                        //dealer had natural 21 -> payout insurance bet
                        player.setBalance(player.getBalance() + hand.getInsuranceBet() + hand.getInsuranceBet() );
                        winnings += hand.getInsuranceBet();

                        if (hand.getValue() == 21) {
                            //player has natural too -> standoff, player breaks even
                            player.setBalance(player.getBalance() + hand.getBet() );
                            winnings += 0;
                        }else{
                            //else -> no payout
                            winnings -= hand.getBet();
                        }

                    }else if (hand.getValue() == 21 && hand.showCards().size() == 2) {
                        //player has natural 21, dealer does not -> payout 1.5x bet
                        player.setBalance(player.getBalance() + hand.getBet() + (hand.getBet() * 1.5) );
                        winnings += hand.getBet() * 1.5;
                    }else if(dealerHand.getValue() <= 21){
                        //dealer stood
                        if (hand.getValue() <= 21 && hand.getValue() > dealerHand.getValue()) {
                            //dealer total < player total, neither busted -> payout bet
                            player.setBalance(player.getBalance() + hand.getBet() + hand.getBet() );
                            winnings += hand.getBet();
                        }else if(hand.getValue() == dealerHand.getValue()){
                            //dealer total == player total, neither busted, player breaks even
                            player.setBalance(player.getBalance() + hand.getBet() );
                            winnings += 0;
                        }
                    }else if(dealerHand.getValue() > 21){
                        if (hand.getValue() <= 21) {
                            //dealer busted, player did not -> payout bet
                            player.setBalance(player.getBalance() + hand.getBet() + hand.getBet() );
                            winnings += hand.getBet();
                        }
                    }else{
                        //else, player busted -> no payout
                        winnings -= hand.getBet();
                    }

                    System.out.printf("%s wins $%.2f \n",player.getPlayerID(),winnings);

                }//end of hands loop
            }//end of if-dealer
        }//end of players loop
    }

    
        
    /**
     * 
     * @param blackjack 
     */
    private static void endGame(BlackjackGame blackjack) {
        //remove all hands from player
        for (Player p : blackjack.getPlayers()) {
            ((BlackjackPlayer)p).getHands().clear();
        }
        //prompt to play again
        if(readUserBoolean("Play again (y/n)?")){
            
        }else{
            System.exit(0);
        }
    }
    
    
    
    
    
    
    
   
    /**
     * prints a formatted table of all players' hands, with an indicator of the 
     * hand being played currently
     * @param players
     * @param currentHand 
     */
    private static void printCurrentTable(ArrayList<Player> players, BlackjackHand currentHand) {
        //60 char wide horizontal rule
        String hr = "------------------------------------------------------------";
        String s = "\n\n\n\n"+ hr +"\n";

        for (Player p : players) {
            //downcast to BlackjackPlayer
            BlackjackPlayer player = (BlackjackPlayer)p;

            //create Player Header
            String stringBal = "";
            if (!(player instanceof BlackjackDealer)) {
                stringBal = String.format("($%.2f)", player.getBalance() );
            }
            s += String.format("%-12s %11s \n", player.getPlayerID(), stringBal);
            
            //create rows of Hands
            for (BlackjackHand hand : player.getHands()) {
                //check if this hand is the hand currently being played
                String currentFlag = "";
                if (hand == currentHand) {
                    currentFlag += "[playing]";
                }
                
                //get the hand's bet
                String stringBet = String.format("", hand.getBet() );
                if (!(player instanceof BlackjackDealer)) {
                    stringBet = String.format("$%.2f", hand.getBet() );
                }
                
                //get hand value, or "BUST" if >21
                String stringValue = "";
                if (hand.getValue() > 21) {
                    stringValue = "BUST";
                }else{
                    stringValue = String.format("%d", hand.getValue() );
                }

                //flag + bet + value
                s += String.format("%-12s %10s %4s ", currentFlag, stringBet, stringValue);

                 // + all cards
                 for (Card card : hand.showCards()) {
                     s += "["+ card +"]";
                 }
                 s += "\n";
             }//end of hand loop
             s += hr +"\n";
        }//end of player loop
        
        //print fully concatenated string
        System.out.println(s);
    }
    
    /**
     * Removes fractions of a cent in given value. 
     * @param value the value to be rounded down to 1/100 place
     * @return given value truncated to two decimal places
     */
    private static double roundToCent(double value) {
        return (int)(value * 100) / 100.0;
    }

    /**
     * Methods for getting input from user. Bad inputs are handled, user is 
     * prompted repeatedly until good data is given.
     * @param prompt
     * @return 
     */
    private static String readUserLine(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        try {
            return input.nextLine().trim();
        } catch (Exception e) {
            System.out.println("ERROR reading input");
            System.out.println(e.getMessage());
            return readUserLine(prompt);
        }
    }
    
    /**
     * 
     * @param prompt
     * @return 
     */
    private static String readUserString(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        try {
            return input.next().trim();
        } catch (Exception e) {
            System.out.println("ERROR reading input");
            System.out.println(e.getMessage());
            return readUserString(prompt);
        }        
    }

    /**
     * 
     * @param prompt
     * @return 
     */
    private static int readUserInt(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        try {
            return input.nextInt();
        } catch (Exception e) {
            System.out.println("ERROR reading input");
            System.out.println(e.getMessage());
            return readUserInt(prompt);
        }
    }

    /**
     * 
     * @param prompt
     * @return 
     */
    private static Double readUserDouble(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        try {
            return input.nextDouble();
        } catch (Exception e) {
            System.out.println("ERROR reading input");
            System.out.println(e.getMessage());
            return readUserDouble(prompt);
        }
    }
    
    /**
     * 
     * @param prompt
     * @return 
     */
    private static Boolean readUserBoolean(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        try {
            char c = input.next().trim().toLowerCase().charAt(0);
            boolean b;
            switch (c) {
                //all possible "yes" responses
                case 'y': b = true; break;
                case '1': b = true; break;
                //otherwise assume "no" response
                default:  b = false;
            }
            return b;
        } catch (Exception e) {
            System.out.println("ERROR reading input");
            System.out.println(e.getMessage());
            return readUserBoolean(prompt);
        }
    } 

}