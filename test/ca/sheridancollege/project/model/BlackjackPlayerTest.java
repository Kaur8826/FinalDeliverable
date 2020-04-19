/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.sheridancollege.project.model;

import java.util.ArrayList;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Muhammad
 */
public class BlackjackPlayerTest {
    
    public BlackjackPlayerTest() {
    }
   
    /**
     * Test of getHands method, of class BlackjackPlayer.
     */
    @Test
    public void testGetHands() {
        System.out.println("getHands");
        BlackjackPlayer instance = null;
        ArrayList<BlackjackHand> expResult = null;
        ArrayList<BlackjackHand> result = instance.getHands();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addHand method, of class BlackjackPlayer.
     */
    @Test
    public void testAddHand() {
        System.out.println("addHand");
        BlackjackHand hand = null;
        BlackjackPlayer instance = null;
        instance.addHand(hand);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBalance method, of class BlackjackPlayer.
     */
    @Test
    public void testGetBalance() {
        System.out.println("getBalance");
        BlackjackPlayer instance = null;
        double expResult = 0.0;
        double result = instance.getBalance();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBalance method, of class BlackjackPlayer.
     */
    @Test
    public void testSetBalance() {
        System.out.println("setBalance");
        double balance = 0.0;
        BlackjackPlayer instance = null;
        instance.setBalance(balance);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class BlackjackPlayer.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BlackjackPlayer instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of play method, of class BlackjackPlayer.
     */
    @Test
    public void testPlay() {
        System.out.println("play");
        BlackjackPlayer instance = null;
        instance.play();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private void assertEquals(ArrayList<BlackjackHand> expResult, ArrayList<BlackjackHand> result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertEquals(double expResult, double result, double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertEquals(String expResult, String result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
