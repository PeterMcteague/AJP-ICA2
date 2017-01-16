/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import static java.lang.Thread.sleep;
import java.util.Scanner;

/**
 *
 * @author Peter
 */
public class MyMiddleware {

    
    /**The driver class for the middleware to show it working.
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("This is a demonstration of the middleware meeting "
                + "the various objectives.");
        System.out.println("Press enter to begin the demonstration of how the "
                + "middleware supports a network of portals and useragents.");
        sc.nextLine();
        Portal testPortal = new Portal("TestPortal");
        UserAgent testAgent1 = new UserAgent("TestAgent1");
        UserAgent testAgent2 = new UserAgent("TestAgent2");
        NodeMonitor testMonitor = new NodeMonitor("TestMonitor");
        testPortal.AddMonitor(testMonitor);
        testPortal.Register(testAgent1);
        testPortal.Register(testAgent2);
        UserMessage newMessage = new UserMessage("TestAgent2","TestAgent1","This is a message");
        testAgent1.sendMessage(newMessage);
        System.out.println("This shows portal and useragent working together to "
                + "deliver a message..");
        sc.nextLine();
        System.out.println("It also shows a nodemonitor being hooked to a portal.");
        sc.nextLine();
        System.out.println("If you take a look at the agent classes you will see "
                + "that this is achieved by using uni and bi directional links..");
        sc.nextLine();
        System.out.println("Nodes being scoped up and down is currently not implemented"
                + " due to deadlines being missed on the creation of the classes.");
        sc.nextLine();
        System.out.println("Rescoping of nodes would be done by system messages.");
        System.out.println("These can be sent between nodes to modify the network.");
        //Use a system message to do something here.
    }
    
}
