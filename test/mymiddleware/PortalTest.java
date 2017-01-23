/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author 07mct
 */
public class PortalTest {
    
    Portal firstPortal;
    Portal secondPortal;
    
    @Before
    public void setUp() 
    {
        firstPortal = new Portal("firstPortal");
        secondPortal = new Portal("secondPortal");
    }
    
    @After
    public void tearDown()
    {
        firstPortal = null;
        secondPortal = null;
    }

    /**
     * Test of attach method, of class Portal.
     */
    @Test
    public void testAttach() {
        System.out.println("attach : To test this we will attatch a second portal"
                + " to the first and see whether it is in its routing table.");
        firstPortal.attach(secondPortal);
        boolean result = firstPortal.tableContainsValue(secondPortal);
        assertEquals(true,result);
    }

    /**
     * Test of addMonitor method, of class Portal.
     */
    @Test
    public void testAddMonitor_NodeMonitor() 
    {
        System.out.println("addMonitor : To test we will compare getMonitor before"
                + " and after calling addmonitor.");
        boolean before = firstPortal.getMonitor() != null;
        NodeMonitor monitorIn = new NodeMonitor("Testmonitor");
        firstPortal.addMonitor(monitorIn);
        boolean after = firstPortal.getMonitor() != null;
        assertEquals(true , !before && after);
    }
    
    /**
     * Test of addMonitor method, of class Portal.
     */
    @Test
    public void testAddMonitor_0args() {
        System.out.println("addMonitor : To test we will compare getMonitor before"
                + " and after calling addmonitor.");
        boolean before = firstPortal.getMonitor() != null;
        firstPortal.addMonitor();
        boolean after = firstPortal.getMonitor() != null;
        assertEquals(true , !before && after);
    }

    /**
     * Test of getMonitor method, of class Portal.
     */
    @Test
    public void testGetMonitor() {
        System.out.println("getMonitor : Test that a value is returned when nodemonitor"
                + " is added and getmonitor called.");
        boolean before = firstPortal.getMonitor() != null;
        firstPortal.addMonitor();
        boolean after = firstPortal.getMonitor() != null;
        assertEquals(true , !before && after);
    }

    /**
     * Test of attachUserAgent method, of class Portal.
     */
    @Test
    public void testAttachUserAgent() {
        System.out.println("attachUserAgent : Test that a useragent is attatched"
                + " when attachUserAgent is called on a valid agent.");
                
        UserAgent firstAgent = new UserAgent("TestAgent1");
        
        boolean before = firstPortal.tableGet("TestAgent1") == null;
        firstAgent.attach(firstPortal);
        boolean after = firstPortal.tableGet("TestAgent1") != null;
        assertEquals(true,before && after);   
    }

    /**
     * Test of recieveMessage method, of class Portal.
     */
    @Test
    public void testRecieveMessage() {
        System.out.println("recieveMessage : Test that the portal can recieve "
                + "a message.");
        boolean cantReceive = firstPortal.recieveMessage() == false;
        firstPortal.add(new UserMessage("TestMonitor","Tester","Hi"));
        boolean canReceive = firstPortal.recieveMessage() == true;
        assertEquals(true, cantReceive && canReceive);
    }

    /**
     * Test of sendMessage method, of class Portal.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage : Test that one portal can send to "
                + "another provided it has knowledge of it.");
        UserMessage message = new UserMessage("secondPortal",firstPortal.getName(),"Test");
        boolean sendWithoutRoute = firstPortal.sendMessage(message);
        firstPortal.attach(secondPortal);
        boolean sendWithRoute = firstPortal.sendMessage(message);
        assertEquals(true, !sendWithoutRoute && sendWithRoute);
    }

    /**
     * Test of removeMonitor method, of class Portal.
     */
    @Test
    public void testRemoveMonitor() {
        System.out.println("removeMonitor : add a monitor and then check if it"
                + " is removed by removeMonitor().");
        firstPortal.addMonitor();
        assertEquals(true,firstPortal.removeMonitor());
    }
    
    /**
     * Test of registerAgent method, of class Portal.
     */
    @Test
    public void testRegisterAgent() {
        System.out.println("registerAgent : To test attach two portals together,"
                + " then check to see if the second knows about a useragent "
                + "attached to the first, then call.");
        UserAgent agent = new UserAgent("test");
        agent.attach(firstPortal);
        firstPortal.attach(secondPortal);
        secondPortal.attach(firstPortal);
        boolean before = secondPortal.tableGet("test") != null;
        firstPortal.registerAgent(agent);
        boolean after = secondPortal.tableGet("test") != null;
        assertEquals(true,!before && after);
    }

    /**
     * Test of removeAgent method, of class Portal.
     */
    @Test
    public void testRemoveAgent() {
        System.out.println("removeAgent : To test call remove and see if "
                + "still in table.");
        UserAgent agent = new UserAgent("test");
        agent.attach(firstPortal);
        boolean before = firstPortal.tableContainsValue(agent);
        firstPortal.removeAgent(agent);
        boolean after = firstPortal.tableContainsValue(agent);
        assertEquals(true,before && !after);
    }

    /**
     * Test of registerPortal method, tested in agentregister, as it calls that.
     */


    /**
     * Test of tableAdd method, of class Portal.
     */
    @Test
    public void testTableAdd() {
        UserAgent agent = new UserAgent("test");
        
        boolean before = firstPortal.tableContainsValue(agent);
        firstPortal.tableAdd(agent.getName(),agent);
        boolean after = firstPortal.tableContainsValue(agent);
        assertEquals(true,!before && after);
    }

    /**
     * Test of tableRemove method, of class Portal.
     */
    @Test
    public void testTableRemove() {
        System.out.println("tableRemove : To test, use table add, the compare"
                + " to the values after tableremove");
        UserAgent agent = new UserAgent("test");
        
        firstPortal.tableAdd(agent.getName(),agent);
        boolean before = firstPortal.tableContainsValue(agent);
        firstPortal.tableRemove(agent.getName());
        boolean after = firstPortal.tableContainsValue(agent);
        assertEquals(true,before && !after);
    }

    /**
     * Test of tableGet method, of class Portal.
     */
    @Test
    public void testTableGet() {
        System.out.println("tableGet : Add a agent and then call get to test.");
        UserAgent agent = new UserAgent("test");
        
        boolean before = firstPortal.tableGet(agent.getName()) == null;
        firstPortal.tableAdd(agent.getName(),agent);
        boolean after = firstPortal.tableGet(agent.getName()) == null;
        assertEquals(true,before && !after);
    }

    /**
     * Test of tableGetValues method, of class Portal.
     */
    @Test
    public void testTableGetValues() {
        System.out.println("tableGetValues : Use get values on a portal with"
                + " an empty table and then one with a non empty one. Compare"
                + " values using size.");
        UserAgent agent = new UserAgent("test");
        
        boolean before = firstPortal.tableGetValues().isEmpty();
        firstPortal.tableAdd(agent.getName(),agent);
        boolean after = firstPortal.tableGetValues().size() == 1;
        assertEquals(true,before && after);
    }

    /**
     * Test of tableContainsValue method, of class Portal.
     */
    @Test
    public void testTableContainsValue() {
        System.out.println("tableContainsValue : Add a agent and then use"
                + " tablecontainsvalue to check if its in there.");
        UserAgent agent = new UserAgent("test");
        
        boolean before = firstPortal.tableContainsValue(agent);
        firstPortal.tableAdd(agent.getName(),agent);
        boolean after = firstPortal.tableContainsValue(agent);
        assertEquals(true, !before && after);
    }
    
}
