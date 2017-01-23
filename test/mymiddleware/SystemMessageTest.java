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
public class SystemMessageTest {
    
    /**
     * Test of getData method, of class SystemMessage.
     */
    @Test
    public void testGetData() {
        System.out.println("getData : Return the data field from a system message.");
        
        UserAgent testAgent = new UserAgent("test");
        SystemMessage test = new SystemMessage("someDestination", "someSender", "test", testAgent);
        
        assertEquals(test.getData(),testAgent);
    }
    
}
