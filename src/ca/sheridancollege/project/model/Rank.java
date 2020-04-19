
package ca.sheridancollege.project.model;

/**
 * Selection of possible values for BlackjackCards
* @author Parneet Kaur
 * @author Aaqib Jamili
 */
public enum Rank {
	TWO     ("Two",     "2",    2),
	THREE   ("Three",   "3",    3),
	FOUR    ("Four",    "4",    4),
	FIVE    ("Five",    "5",    5),
	SIX     ("Six",     "6",    6),
	SEVEN   ("Seven",   "7",    7),
	EIGHT   ("Eight",   "8",    8),
	NINE    ("Nine",    "9",    9),
	TEN     ("Ten",     "10",   10),
	JACK    ("Jack",    "J",    10),
	QUEEN   ("Queen",   "Q",    10),
	KING    ("King",    "K",    10),
	ACE     ("Ace",     "A",    1);

    private String longName;
    private String shortName;
    private int value;
    
    /**
     * Accessor for the longName property.
     * @return The friendly name of the rank, as String.
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Accessor for the shortName property.
     * @return A one of two character String representing the rank.
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Accessor for the value property.
     * @return The numerical value of the rank, as integer primitive.
     */
    public int getValue() {
        return value;
    }

    /**
     * Constructor for enum.
     * @param longName 
     * @param shortName
     * @param value
     */
    private Rank(String longName, String shortName, int value) {
        this.longName = longName;
        this.shortName = shortName;
        this.value = value;
    }

    /**
     * Default String representation of Rank enum.
     * @return The suit's long name, as String.
     */
    @Override
    public String toString() {
        return this.longName;
    }
}