package ca.sheridancollege.project.model;

import java.util.ArrayList;

public class BlackjackPlayer extends Player {

    private ArrayList<BlackjackHand> hands;
    private double balance = 0.0;

    /**
     * Constructor for Blackjack Player
     *
     * @param name
     */
    public BlackjackPlayer(String name) {
        super(name);
        //new player alwasy start with default balance
        this.balance = 100.0;
        //initialize hands
        hands = new ArrayList<BlackjackHand>();

    }

    /**
     * Get all player hands as ArrayList of Hands
     *
     * @return
     */
    public ArrayList<BlackjackHand> getHands() {
        return this.hands;
    }

    /**
     * Add a new hand to the player
     *
     * @param hand
     */
    public void addHand(BlackjackHand hand) {
        if (hand == null) {
            throw new IllegalArgumentException("ERROR adding new hand to player, hand is null");
        }
        this.getHands().add(hand);
    }

    /**
     *
     * @return
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     *
     * @param balance
     */
    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("ERROR setting new balance for "
                    + "player " + this.getPlayerID() + ": balance cannot be "
                    + "negative");
        }
        //round DOWN to nearest cent
        balance = (int) (balance * 100) / 100.0;
        this.balance = balance;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s ($%.2f)", this.getPlayerID(), this.getBalance());
    }

    //moved to controller to segregate class duties
    @Override
    public void play() {

    }
}
