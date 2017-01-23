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
public class UserMessageTest {

    @Test
    public void testGetters() 
    {
        System.out.println("getters : Check that all the getters return the "
                + " correct" + " data.");
        
        UserMessage test = new UserMessage("someDestination", "someSender", "test");
        
        assertEquals("someDestination",test.getDestination());
        assertEquals("someSender",test.getSender());
        assertEquals("test",test.getMessage());
    }
    
}
