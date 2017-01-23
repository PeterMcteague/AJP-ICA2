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
public class MetaAgentTest {
    
    public MetaAgentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of run method, of class MetaAgent.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        MetaAgent instance = new MetaAgentImpl();
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recieveMessage method, of class MetaAgent.
     */
    @Test
    public void testRecieveMessage() {
        System.out.println("recieveMessage");
        MetaAgent instance = new MetaAgentImpl();
        boolean expResult = false;
        boolean result = instance.recieveMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class MetaAgent.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        MetaAgent instance = new MetaAgentImpl();
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resume method, of class MetaAgent.
     */
    @Test
    public void testResume() {
        System.out.println("resume");
        MetaAgent instance = new MetaAgentImpl();
        instance.resume();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class MetaAgent.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        MetaAgent instance = new MetaAgentImpl();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class MetaAgent.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String nameIn = "";
        MetaAgent instance = new MetaAgentImpl();
        instance.setName(nameIn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuspended method, of class MetaAgent.
     */
    @Test
    public void testGetSuspended() {
        System.out.println("getSuspended");
        MetaAgent instance = new MetaAgentImpl();
        boolean expResult = false;
        boolean result = instance.getSuspended();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSuspended method, of class MetaAgent.
     */
    @Test
    public void testSetSuspended() {
        System.out.println("setSuspended");
        boolean suspendIn = false;
        MetaAgent instance = new MetaAgentImpl();
        instance.setSuspended(suspendIn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getThread method, of class MetaAgent.
     */
    @Test
    public void testGetThread() {
        System.out.println("getThread");
        MetaAgent instance = new MetaAgentImpl();
        Thread expResult = null;
        Thread result = instance.getThread();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setThread method, of class MetaAgent.
     */
    @Test
    public void testSetThread() {
        System.out.println("setThread");
        Thread threadIn = null;
        MetaAgent instance = new MetaAgentImpl();
        instance.setThread(threadIn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of interruptThread method, of class MetaAgent.
     */
    @Test
    public void testInterruptThread() {
        System.out.println("interruptThread");
        MetaAgent instance = new MetaAgentImpl();
        instance.interruptThread();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scopeDown method, of class MetaAgent.
     */
    @Test
    public void testScopeDown() {
        System.out.println("scopeDown");
        int scopeSteps = 0;
        MetaAgent instance = new MetaAgentImpl();
        instance.scopeDown(scopeSteps);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scopeUp method, of class MetaAgent.
     */
    @Test
    public void testScopeUp() {
        System.out.println("scopeUp");
        int scopeSteps = 0;
        MetaAgent instance = new MetaAgentImpl();
        instance.scopeUp(scopeSteps);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUpdater method, of class MetaAgent.
     */
    @Test
    public void testGetUpdater() {
        System.out.println("getUpdater");
        MetaAgent instance = new MetaAgentImpl();
        AgentRegisterer expResult = null;
        AgentRegisterer result = instance.getUpdater();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpdater method, of class MetaAgent.
     */
    @Test
    public void testSetUpdater() {
        System.out.println("setUpdater");
        AgentRegisterer in = null;
        MetaAgent instance = new MetaAgentImpl();
        boolean expResult = false;
        boolean result = instance.setUpdater(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class MetaAgentImpl extends MetaAgent {
    }
    
}
