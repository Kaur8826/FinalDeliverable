/*
    BlackjackPack.java

    Description
    [Description about the class]
*/
package ca.sheridancollege.project.model;

import java.util.ArrayList;


public class BlackjackPack extends GroupOfCards{

    public BlackjackPack(int numDecks) {
        super(numDecks * 52);
        //generate decks
        ArrayList<Card> newCards = new ArrayList();
        for (int i = 0; i < numDecks; i++) {
            //generate one whole deck
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    BlackjackCard card = new BlackjackCard(rank,suit);
                    newCards.add(card);
                }
            }
        }
        this.setCards(newCards);
        //shuffle whole pack
        this.shuffle();
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.showCards().size(); i++) {
            s += "["+ this.showCards().get(i) +"]"+"\t";
            if (i%8 == 0) {
                s += "\n";
            }
        }
        return s;
    }
    
    
    

}//end of class BlackjackPack()
