/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author 07mct
 */
public class UserAgentTest {
    
    protected UserAgent firstAgent,secondAgent;
    protected Portal firstPortal;
    
    
    @Before
    public void setUpClass() {
        firstAgent = new UserAgent("agent1");
        secondAgent = new UserAgent("agent2");
        firstPortal = new Portal("portal1");
    }
    
    /**
     * Test of sendMessage method, of class UserAgent.
     */
    @Test
    public void testSendRecieveMessage() 
    {
        System.out.println("sendMessage : To test send a message between two"
                + " agents via portal.");

        firstAgent.attach(firstPortal);
        secondAgent.attach(firstPortal);
        
        firstAgent.sendMessage("agent2", "Test");
        System.out.println("Observe that messages are sent and recieved.");
    }

    /**
     * Test of isAttached method, of class UserAgent.
     */
    @Test
    public void testAttachAndIsAttached() 
    {
        System.out.println("isAttached : Call before and after attaching to portal.");
        boolean before = firstAgent.isAttached();
        firstAgent.attach(firstPortal);
        boolean after = firstAgent.isAttached();
        assertEquals(true, !before && after);
    }

    /**
     * Test of detach method, of class UserAgent.
     */
    @Test
    public void testDetach() {
        System.out.println("detach : Compare isAttatched before and after calling"
                + " detach.");
        
        firstAgent.attach(firstPortal);
        boolean before = firstAgent.isAttached();
        firstAgent.detach();
        boolean after = firstAgent.isAttached();
    }

    /**
     * Test of getPortal method, of class UserAgent.
     */
    @Test
    public void testGetPortal() {
        System.out.println("getPortal : Call getPortal before and after "
                + "attaching.");
        
        boolean before = firstAgent.getPortal() == null;
        firstAgent.attach(firstPortal);
        boolean after = firstAgent.getPortal() != null;
        assertEquals(true, before && after);                
    }
    
}
