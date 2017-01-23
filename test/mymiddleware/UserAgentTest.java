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
public class UserAgentTest {
    
    public UserAgentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of sendMessage method, of class UserAgent.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        String destination = "";
        String message = "";
        UserAgent instance = null;
        instance.sendMessage(destination, message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAttached method, of class UserAgent.
     */
    @Test
    public void testIsAttached() {
        System.out.println("isAttached");
        UserAgent instance = null;
        boolean expResult = false;
        boolean result = instance.isAttached();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of attach method, of class UserAgent.
     */
    @Test
    public void testAttach() {
        System.out.println("attach");
        Portal portalIn = null;
        UserAgent instance = null;
        boolean expResult = false;
        boolean result = instance.attach(portalIn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detach method, of class UserAgent.
     */
    @Test
    public void testDetach() {
        System.out.println("detach");
        UserAgent instance = null;
        boolean expResult = false;
        boolean result = instance.detach();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPortal method, of class UserAgent.
     */
    @Test
    public void testGetPortal() {
        System.out.println("getPortal");
        UserAgent instance = null;
        Portal expResult = null;
        Portal result = instance.getPortal();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
