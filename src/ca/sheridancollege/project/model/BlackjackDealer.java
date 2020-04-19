package ca.sheridancollege.project.model;

public class BlackjackDealer extends BlackjackPlayer {

	private BlackjackPack pack;
    
   
    public BlackjackDealer() {
        //always the same name
        super("the dealer");
        //generate the pack, six decks
        this.setPack(new BlackjackPack(6));
        
    }
    
    /* this will probably be unused / set to private   */
	public BlackjackPack getPack() {
		return this.pack;
	}

	/**
	 * 
     * @param pack
	 */
	public void setPack(BlackjackPack pack) {
		this.pack = pack;
	}
    
    /**
     * Takes the first card from the Pack and adds it to the specified players'
     * current hand.
     * @param player the player receiving the card
     */
    public BlackjackCard dealCard() {
        //remove the first card from the pack and return it.
        return (BlackjackCard)(this.pack.showCards().remove(0));
    }
    
    @Override
    public void play() {
        //check hand value
        //hit until hand value > 16
    }

    @Override
    public String toString() {
        //1234567890123456789012345678901234567890
        //player name ($000.00):  
        //00 [A♥][5♥][10♥][3♥] $000.00 ($00.00)
        //----------------------------------------

        //A Dealer doesn't need to show balance
        String s = this.getPlayerID() +":"+"\n";
        for (BlackjackHand hand : this.getHands()) {
            s += hand +"\n";
        }
        s += "----------------------------------------";
        return s;
    }
     

}