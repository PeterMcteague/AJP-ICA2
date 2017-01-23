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
public class NodeMonitorTest {
    
    public NodeMonitorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of stop method, of class NodeMonitor.
     */
    @Test
    public void testStop() {
        System.out.println("stop");
        NodeMonitor instance = null;
        instance.stop();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recieveMessage method, of class NodeMonitor.
     */
    @Test
    public void testRecieveMessage() {
        System.out.println("recieveMessage");
        NodeMonitor instance = null;
        boolean expResult = false;
        boolean result = instance.recieveMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resume method, of class NodeMonitor.
     */
    @Test
    public void testResume() {
        System.out.println("resume");
        NodeMonitor instance = null;
        instance.resume();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
