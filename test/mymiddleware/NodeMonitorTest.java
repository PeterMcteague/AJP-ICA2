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
        /**
     * Test of NodeMonitor method, of class NodeMonitor.
     */
    @Test
    public void testNodeMonitor() {
        System.out.println("Testing construction of nodeMonitor.");
        String nameIn = "TestMonitor";
        NodeMonitor monitor = new NodeMonitor(nameIn);
        assertEquals(monitor.name,"TestMonitor");
    }

    /**
     * Test of run method, of class NodeMonitor.
     */
    @Test
    public void testRun() {
        System.out.println("Testing run. Please observe the printing of test "
                + "message to see if the test succeeds.");
        NodeMonitor monitor = new NodeMonitor("TestMonitor");
        UserMessage testMsg = new UserMessage("TestMonitor","Sender","This is a message.");
        monitor.add(testMsg);
        monitor.agentThread.start();
    }

    /**
     * Test of recieveMessage method, of class NodeMonitor.
     * 
     * We are calling methods manually here for testing. In reality, start the
     * thread and let it handle it.
     */
    @Test
    public void testRecieveMessage() 
    {
        System.out.println("Recieve message: Testing if the monitor can recieve a created message.");
        NodeMonitor monitor = new NodeMonitor("TestMonitor");
        UserMessage testMsg = new UserMessage("TestMonitor","Sender","This is a message.");
        monitor.add(testMsg);
        boolean result = monitor.recieveMessage();
        assertEquals(result,true);
    }
    
}
