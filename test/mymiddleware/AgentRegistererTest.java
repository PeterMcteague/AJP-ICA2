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

/**
 *
 * @author 07mct
 */
public class AgentRegistererTest {

    /**
     * Test of registerAgent method, of class AgentRegisterer.
     */
    @Test
    public void testRegisterAgent() 
    {
        System.out.println("registerAgent: Checking if agents on one portal can be"
                + " announced to other portals using registerAgent().");
        UserAgent agentIn = new UserAgent("TestAgent");
        Portal firstPortal = new Portal("TestPortal1");
        Portal secondPortal = new Portal("TestPortal2");
        
        firstPortal.attach(secondPortal);
        secondPortal.attach(firstPortal);
        
        AgentRegisterer instance = firstPortal.getUpdater();
        agentIn.attach(firstPortal);
        instance.registerAgent(agentIn, firstPortal);
        assertEquals(firstPortal, secondPortal.tableGet(agentIn.getName()));
    }

    /**
     * Test of unregisterAgent method, of class AgentRegisterer.
     */
    @Test
    public void testUnregisterAgent() {
        System.out.println("unregisterAgent");
        MetaAgent agentIn = null;
        AgentRegisterer instance = new AgentRegisterer();
        instance.unregisterAgent(agentIn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerPortal method, of class AgentRegisterer.
     */
    @Test
    public void testRegisterPortal() {
        System.out.println("registerPortal");
        Portal agentIn = null;
        AgentRegisterer instance = new AgentRegisterer();
        instance.registerPortal(agentIn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerPortalSearch method, of class AgentRegisterer.
     */
    @Test
    public void testRegisterPortalSearch() {
        System.out.println("registerPortalSearch");
        Portal distantPortal = null;
        Portal agentIn = null;
        AgentRegisterer instance = new AgentRegisterer();
        Portal expResult = null;
        Portal result = instance.registerPortalSearch(distantPortal, agentIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPortal method, of class AgentRegisterer.
     */
    @Test
    public void testAddPortal() {
        System.out.println("addPortal");
        Portal in = null;
        AgentRegisterer instance = new AgentRegisterer();
        boolean expResult = false;
        boolean result = instance.addPortal(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePortal method, of class AgentRegisterer.
     */
    @Test
    public void testRemovePortal() {
        System.out.println("removePortal");
        Portal in = null;
        AgentRegisterer instance = new AgentRegisterer();
        boolean expResult = false;
        boolean result = instance.removePortal(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scopeDown method, of class AgentRegisterer.
     */
    @Test
    public void testScopeDown() {
        System.out.println("scopeDown");
        MetaAgent agent = null;
        int steps = 0;
        AgentRegisterer instance = new AgentRegisterer();
        instance.scopeDown(agent, steps);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scopeUp method, of class AgentRegisterer.
     */
    @Test
    public void testScopeUp() {
        System.out.println("scopeUp");
        MetaAgent agent = null;
        int steps = 0;
        AgentRegisterer instance = new AgentRegisterer();
        instance.scopeUp(agent, steps);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
