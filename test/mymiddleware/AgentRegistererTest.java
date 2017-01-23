/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 07mct
 */
public class AgentRegistererTest {

    protected Portal firstPortal,secondPortal,thirdPortal;
    protected UserAgent firstAgent,secondAgent,thirdAgent;
    
    @After
    public void cleanUp() 
    {
        firstPortal.getUpdater().removePortal(firstPortal);
        firstPortal.getUpdater().removePortal(secondPortal);
        firstPortal.getUpdater().removePortal(thirdPortal);
        firstPortal = null;
        secondPortal = null;
        thirdPortal = null;
        firstAgent = null;
        secondAgent = null;
        thirdAgent = null;
    }
    
    /**
     * Test of registerAgent method, of class AgentRegisterer.
     */
    @Test
    public void testRegisterAgent() 
    {
        System.out.println("registerAgent: Checking if agents on one portal can be"
                + " announced to other portals using registerAgent().");
        firstAgent = new UserAgent("TestAgent");
        firstPortal = new Portal("TestPortal1");
        secondPortal = new Portal("TestPortal2");
        
        firstPortal.attach(secondPortal);
        secondPortal.attach(firstPortal);
        
        AgentRegisterer instance = firstPortal.getUpdater();
        firstAgent.attach(firstPortal);
        instance.registerAgent(firstAgent, firstPortal);
        System.out.println("");
        assertEquals(firstPortal, secondPortal.tableGet(firstAgent.getName()));
    }

    /**
     * Test of unregisterAgent method, of class AgentRegisterer.
     */
    @Test
    public void testUnregisterAgent() {
        System.out.println("unregisterAgent: Checking if a registered agent is"
                + " unregistered properly when using unregisterAgent().");
        
        firstAgent = new UserAgent("TestAgent");
        firstPortal = new Portal("TestPortal1");
        secondPortal = new Portal("TestPortal2");
        
        firstPortal.attach(secondPortal);
        secondPortal.attach(firstPortal);
        
        AgentRegisterer instance = firstPortal.getUpdater();
        firstAgent.attach(firstPortal);
        instance.registerAgent(firstAgent, firstPortal);
        Boolean wasRegistered = secondPortal.tableGet(firstAgent.getName()) == firstPortal;
        System.out.println(wasRegistered);
        instance.unregisterAgent(firstAgent);
        Boolean isRegistered = secondPortal.tableGet(firstAgent.getName()) != null;
        System.out.println(isRegistered);
        System.out.println("");
        assertEquals(true,wasRegistered && !isRegistered);
    }

    /**
     * Test of registerPortal method, of class AgentRegisterer.
     */
    @Test
    public void testRegisterPortal() {
        System.out.println("testRegisterPortal() : Tests if a portal is registered on another portal"
                + " properly.");
        
        firstPortal = new Portal("TestPortal1");
        secondPortal = new Portal("TestPortal2");
        thirdPortal = new Portal("TestPortal3");
        
        firstPortal.attach(secondPortal);
        secondPortal.attach(firstPortal);
        secondPortal.attach(thirdPortal);
        thirdPortal.attach(secondPortal);
        
        Boolean wasRegistered = firstPortal.tableGet(thirdPortal.getName()) != null;
        firstPortal.registerPortal();
        thirdPortal.registerPortal();
        Boolean isRegistered = firstPortal.tableGet(thirdPortal.getName()) != null;
        System.out.println("Was registered: " + wasRegistered);
        System.out.println("Is registered: " + isRegistered);
        System.out.println("");
        assertEquals(true,!wasRegistered && isRegistered);
    }

    /*registerPortalSearch is in effect in registerPortal test as registerPortal()
    uses it.*/

    /**
     * Test of addPortal method, of class AgentRegisterer.
     */
    @Test
    public void testAddPortal() 
    {
        System.out.println("addPortal() : Is called in the constructor for portals."
                + " We will create a portal and then see if the list of portals"
                + " contains the portal.");
        firstPortal = new Portal("test1");
        
        List<Portal> listContainingFirst = new ArrayList<>();
        listContainingFirst.add(firstPortal);
        
        assertEquals(listContainingFirst,firstPortal.getUpdater().getPortals());
    }

    /**
     * Test of removePortal method, of class AgentRegisterer.
     */
    @Test
    public void testRemovePortal() {
        System.out.println("removePortal() : As the portal is added in its"
                + " constructor, we will create a portal, then call remove"
                + " and check if the list of portals is empty.");
        firstPortal = new Portal("test1");
        firstPortal.getUpdater().removePortal(firstPortal);
        
        assertEquals(0,firstPortal.getUpdater().getPortals().size());
    }

    /**
     * Test of scopeDown method, of class AgentRegisterer.
     */
    @Test
    public void testScopeDown() {
        System.out.println("scopeDown: To test we will have two portals, one"
                + " with an agent. The other will know about it, and when"
                + " we call scopeDown() with 1 step, it will no longer know"
                + " about it.");
        
        firstAgent = new UserAgent("TestAgent");
        firstPortal = new Portal("TestPortal1");
        secondPortal = new Portal("TestPortal2");
        
        firstPortal.attach(secondPortal);
        secondPortal.attach(firstPortal);
        
        AgentRegisterer instance = firstPortal.getUpdater();
        firstAgent.attach(firstPortal);
        instance.registerAgent(firstAgent, firstPortal);
        boolean wasRegistered = secondPortal.tableGet(firstAgent.getName()) != null;
        firstAgent.scopeDown(1);
        boolean notRegistered = secondPortal.tableGet(firstAgent.getName()) == null;
        System.out.println("");
        assertEquals(true, wasRegistered && notRegistered);
    }

    /**
     * Test of scopeUp method, of class AgentRegisterer.
     */
    @Test
    public void testScopeUp() {
        System.out.println("scopeUp : To test we will have two attached portals,"
                + " with only one knowing about the useragent, and will scope up.");
        
        firstAgent = new UserAgent("TestAgent");
        firstPortal = new Portal("TestPortal1");
        secondPortal = new Portal("TestPortal2");
        
        firstPortal.attach(secondPortal);
        secondPortal.attach(firstPortal);
        firstAgent.attach(firstPortal);
        boolean notRegistered = (secondPortal.tableGet(firstAgent.getName())==null);
        firstAgent.scopeUp(1);
        boolean isRegistered = (secondPortal.tableGet(firstAgent.getName())!=null);
        assertEquals(true, notRegistered && isRegistered);
    }
    
}
