/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 07mct
 */
public class PortalTest {
    
    public PortalTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of attach method, of class Portal.
     */
    @Test
    public void testAttach() {
        System.out.println("attach");
        MetaAgent agentIn = null;
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.attach(agentIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMonitor method, of class Portal.
     */
    @Test
    public void testAddMonitor_NodeMonitor() {
        System.out.println("addMonitor");
        NodeMonitor monitorIn = null;
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.addMonitor(monitorIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMonitor method, of class Portal.
     */
    @Test
    public void testGetMonitor() {
        System.out.println("getMonitor");
        Portal instance = null;
        NodeMonitor expResult = null;
        NodeMonitor result = instance.getMonitor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of attachUserAgent method, of class Portal.
     */
    @Test
    public void testAttachUserAgent() {
        System.out.println("attachUserAgent");
        UserAgent agentIn = null;
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.attachUserAgent(agentIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recieveMessage method, of class Portal.
     */
    @Test
    public void testRecieveMessage() {
        System.out.println("recieveMessage");
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.recieveMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessage method, of class Portal.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        Message message = null;
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.sendMessage(message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeMonitor method, of class Portal.
     */
    @Test
    public void testRemoveMonitor() {
        System.out.println("removeMonitor");
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.removeMonitor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMonitor method, of class Portal.
     */
    @Test
    public void testAddMonitor_0args() {
        System.out.println("addMonitor");
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.addMonitor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerAgent method, of class Portal.
     */
    @Test
    public void testRegisterAgent() {
        System.out.println("registerAgent");
        String agentName = "";
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.registerAgent(agentName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAgent method, of class Portal.
     */
    @Test
    public void testRemoveAgent() {
        System.out.println("removeAgent");
        String name = "";
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.removeAgent(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerPortal method, of class Portal.
     */
    @Test
    public void testRegisterPortal() {
        System.out.println("registerPortal");
        Portal instance = null;
        instance.registerPortal();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tableAdd method, of class Portal.
     */
    @Test
    public void testTableAdd() {
        System.out.println("tableAdd");
        String nameIn = "";
        MetaAgent valueIn = null;
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.tableAdd(nameIn, valueIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tableRemove method, of class Portal.
     */
    @Test
    public void testTableRemove() {
        System.out.println("tableRemove");
        String nameIn = "";
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.tableRemove(nameIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tableGet method, of class Portal.
     */
    @Test
    public void testTableGet() {
        System.out.println("tableGet");
        String nameIn = "";
        Portal instance = null;
        MetaAgent expResult = null;
        MetaAgent result = instance.tableGet(nameIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tableGetValues method, of class Portal.
     */
    @Test
    public void testTableGetValues() {
        System.out.println("tableGetValues");
        Portal instance = null;
        Collection<MetaAgent> expResult = null;
        Collection<MetaAgent> result = instance.tableGetValues();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tableContainsValue method, of class Portal.
     */
    @Test
    public void testTableContainsValue() {
        System.out.println("tableContainsValue");
        MetaAgent agentIn = null;
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.tableContainsValue(agentIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
