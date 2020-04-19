/*
    BlackjackHand.java
     Description
    [Description about the class]
 */
package ca.sheridancollege.project.model;

import java.util.ArrayList;


public class BlackjackHand extends GroupOfCards {

    private double bet;
    private double insuranceBet;

    /**
     *
     * @param bet
     */
    public BlackjackHand(double bet) {
        super(0);
        setBet(bet);
    }

    /**
     * Add one card to the cards list
     *
     * @param newCard
     */
    public void addCard(Card newCard) {
        this.showCards().add(newCard);
    }

    /**
     *
     * @return
     */
    public double getBet() {
        return bet;
    }

    /**
     *
     * @param bet
     */
    public void setBet(double bet) {
        bet = (int) (bet * 100) / 100.0;
        if (bet < 0) {
            throw new IllegalArgumentException("ERROR bet amount cannot be negative");
        } else {
            this.bet = bet;
        }
    }

    /**
     *
     * @return
     */
    public double getInsuranceBet() {
        return insuranceBet;
    }

    /**
     *
     * @param insuranceBet
     */
    public void setInsuranceBet(double insuranceBet) {
        insuranceBet = (int) (insuranceBet * 100) / 100.0;
        if (insuranceBet < 0) {
            throw new IllegalArgumentException("ERROR insurance bet amount cannot be negative");
        } else {
            this.insuranceBet = insuranceBet;
        }
    }

    /**
     * Calculates the total value of the hand, including high/low ACEs.
     *
     * @return The total value of the hand currently, as int.
     */
    public int getValue() {
        //running total for the hand
        int total = 0;
        //count the number of ACEs for later
        int numberOfAces = 0;
        //sum the (minimum) value of all cards
        try {
            for (Card card : this.showCards()) {
                total += ((BlackjackCard) card).getRank().getValue();
                //count number of ACEs
                if (((BlackjackCard) card).getRank() == Rank.ACE) {
                    numberOfAces++;
                }
            }
        } catch (Exception e) {
            //catch exception for empty arraylist
        }

        //check the ACEs
        for (int i = 0; i < numberOfAces; i++) {
            //for each ACE in hand, check if the total is < 12
            if (total < 12) {
                //convert ACE from 1 to 11
                total += 10;
            }
        }
        //return the total
        return total;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        for (Card card : this.showCards()) {
            s += "[" + card + "]";
        }
        return s;
    }

}//end of class BlackjackHand()
