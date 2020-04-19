
package ca.sheridancollege.project.model;

import java.util.logging.Level;
import java.util.logging.Logger;


public class BlackjackGame extends Game{

   
    public BlackjackGame() {
        //set the name of the game in the parent object
        super("Blackjack");
        //
    }
    
    /**
     * Returns the Dealer player
     * @return the Dealer player, or null if no dealer found.
     * @throws Exception 
     */
    public BlackjackDealer getDealer() throws Exception {
        //The Dealer should be last player in the list, so we'll iterate 
        // backwards through the list until we find a Dealer object:
        for (int i = this.getPlayers().size()-1; i >= 0; i--) {
            if(this.getPlayers().get(i) instanceof BlackjackDealer){
                return (BlackjackDealer)(this.getPlayers().get(i));
            }
        }
        throw new Exception("No dealer in player list");
    }


//unused, moved to controller
    @Override
    public void play() {

    }
//unused, moved to controller
    @Override
    public void declareWinner() {
        
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        //A game is made up of players, so print all the players:
        //1234567890123456789012345678901234567890
        //player name ($000.00):  
        //00 [A♥][5♥][10♥][3♥] $000.00 ($00.00)
        //----------------------------------------
        String s = "";
        for (Player player : this.getPlayers()) {
            if (player instanceof BlackjackDealer) {
                s += (BlackjackDealer)player + "\n";
            }else{
                s += (BlackjackPlayer)player + "\n";
            }
        }
        return s;
    }
    
    
    
    /**
     * 
     * @param hand 
     */
    public void hit(BlackjackHand hand) {
        try {
            //Dealer gives next card from pack to player
            hand.addCard(this.getDealer().dealCard());
        } catch (Exception ex) {
            //no dealer found
        }
    }
    
    /**
     * 
     * @param player
     * @param hand
     * @throws Exception 
     */
    public void doubleDown(BlackjackPlayer player, BlackjackHand hand) throws Exception {
        if (hand.showCards().size() > 2) {
            //must have only two cards in hand
            throw new Exception(player.getPlayerID() +"'s hand has too many card to double down");
        }else if (player.getBalance() < hand.getBet()) {
            //must have enough money to double bet
            throw new Exception(player.getPlayerID() +"'s balance too low to double down");
        }else if (hand.getValue() < 9 || hand.getValue() > 11) {
            //hand value out of range
            throw new Exception(player.getPlayerID() +"'s hand total must be 9, 10, or 11 to double down");
        }else{
            //decrease player balance
            player.setBalance(player.getBalance() - hand.getBet());
            //double bet
            hand.setBet(hand.getBet() * 2);
            //hit
            this.hit(hand);
            //player should now stand
        }
    }
    
    /**
     * 
     * @param player
     * @param hand
     * @throws Exception 
     */
    public void splitPair(BlackjackPlayer player, BlackjackHand hand) throws Exception {
        //player splits hand into two hands
        if (hand.showCards().size() > 2) {
            //must have only two cards in hand
            throw new Exception(player.getPlayerID() +"'s hand has too many card to split pair");
        }else if (player.getBalance() < hand.getBet()) {
            //must have enough money to double bet
            throw new Exception(player.getPlayerID() +"'s balance too low to split pair");
        }else if( ((BlackjackCard)hand.showCards().get(0)).getRank() != ((BlackjackCard)hand.showCards().get(1)).getRank() ){
            //ranks do not match
            throw new Exception("Both cards in "+ player.getPlayerID() +"'s hand must be the same rank to split pair");
        }else{
            //decrease player balance
            player.setBalance(player.getBalance() - hand.getBet());
            //create new hand
            BlackjackHand newHand = new BlackjackHand(hand.getBet());
            //move card to new hand
            newHand.addCard(hand.showCards().remove(1));
            //give new hand to current player
            player.addHand(newHand);
            //now both hand and newHand have the same bet and one card each
            if (!hand.equals(newHand)) {
                throw new Exception("something failed during split pair: hand values aren't equal");
            }
            //get a new card for each hand right away
            this.hit(hand); System.out.println("new hand "+ hand +"   size: "+ hand.showCards().size() );
            this.hit(newHand); System.out.println("NEW new hand "+ newHand +"   size: "+ newHand.showCards().size() );
            //now both hands should have two cards each
            if (  (hand.showCards().size() != 2) || (newHand.showCards().size() != 2)  ) {
                throw new Exception("something failed during split pair: hands aren't both two cards each");
                //this exception is being thrown even when both hands size() == 2
            }
        }

    }

}//end of class BlackjackGame()
