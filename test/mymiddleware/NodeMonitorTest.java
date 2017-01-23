/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import static java.lang.Thread.sleep;
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
public class NodeMonitorTest {
    
    protected NodeMonitor monitor;
    
    @Before
    public void setUpClass() 
    {
        System.out.println("b4");
        monitor = new NodeMonitor("TestMonitor");
    }
    
    @After
    public void tearDownClass() 
    {
        System.out.println("after");
        monitor = null;
    }

    /**
     * Test of stop method, of class NodeMonitor. 
     * Sometimes it wakes up on its own which will cause the test to fail,
     * should go back to sleep again in a real scenario.
     */
    @Test
    public void testStop()
    {
        System.out.println("testStop : We will test by checking whether the"
                + " nodemonitor thread is interrupted.");
        
        boolean wasntInterrupted = monitor.getThread().isInterrupted() == false;
        System.out.println(monitor.getThread().isInterrupted());
        monitor.stop();
        System.out.println(monitor.getThread().isInterrupted());
        boolean isInterrupted = monitor.getThread().isInterrupted() == true;
        assertEquals(true,wasntInterrupted && isInterrupted);
    }

    /**
     * Test of recieveMessage method, of class NodeMonitor.
     */
    @Test
    public void testRecieveMessage() {
        System.out.println("recieveMessage : To test we will check that recieve"
                + " message returns true after a message is added but not before.");
        
        boolean cantReceive = monitor.recieveMessage() == false;
        monitor.add(new UserMessage("TestMonitor","Tester","Hi"));
        boolean canReceive = monitor.recieveMessage() == true;
        assertEquals(true, cantReceive && canReceive);
    }

    /**
     * Test of resume method, of class NodeMonitor.
     */
    @Test
    public void testResume() {
        System.out.println("resume : To test this we will suspend the monitor,"
                + " resume it and check that the value has changed.");
        monitor.setSuspended(true);
        monitor.resume();
        assertEquals(false, monitor.getSuspended());
    }
    
}
