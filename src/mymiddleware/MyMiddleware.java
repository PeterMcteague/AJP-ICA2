/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import static java.lang.Thread.sleep;
import java.util.Scanner;

/**A driver class for the middleware. 
 * Provides two demo cases and shows that they run in the system correctly.
 *
 * @author Peter
 */
public class MyMiddleware {

    /**A scanner for detecting user input.*/
    private static final Scanner SCANNER = new Scanner(System.in);
    
    /**testOne() - A demo that shows that two agents attatched to a portal
     * can send messages to eachother.
     * 
     * @throws InterruptedException 
     */
    private void testOne() throws InterruptedException
    {
        UserAgent agent1 = new UserAgent("agent1");
        UserAgent agent2 = new UserAgent("agent2");
        Portal testPortal1 = new Portal("testPortal1");
        agent1.attach(testPortal1);
        agent2.attach(testPortal1);
        testPortal1.addMonitor();
        NodeMonitor testMonitor = testPortal1.getMonitor();
        agent1.sendMessage("agent2", "First test");
        sleep(500); //So that press enter appears at a relevant time.
        System.out.println("Close the nodemonitor or stop the program to exit..");
        SCANNER.nextLine();
    }
    
    /**testTwo() - A demo that shows that a useragent can send a message to
     * another through two portals.
     * 
     * @throws InterruptedException 
     */
    private void testTwo() throws InterruptedException
    {
        UserAgent agent1 = new UserAgent("agent1");
        UserAgent agent2 = new UserAgent("agent2");
        Portal testPortal1 = new Portal("testPortal1");
        Portal testPortal2 = new Portal("testPortal2");
        agent1.attach(testPortal1);
        agent2.attach(testPortal2);
        testPortal1.addMonitor();
        testPortal2.addMonitor();
        NodeMonitor testMonitor1 = testPortal1.getMonitor();
        NodeMonitor testMonitor2 = testPortal2.getMonitor();
        testPortal1.attach(testPortal2);
        testPortal2.attach(testPortal1);
        testPortal2.registerAgent(agent2);
        agent1.sendMessage("agent2", "Second test");
        sleep(500); //So that press enter appears at a relevant time.
        System.out.println("Close a nodemonitor or stop the program to exit..");
        SCANNER.nextLine();
    }
    
    /**questionAsk() - A menu for demo purposes that asks the user to choose
     * a demo to run.
     * 
     * @return - Whether the user answered the question correctly or not.
     * @throws InterruptedException 
     */
    private boolean questionAsk() throws InterruptedException
    {
        System.out.println("Type a number to select a demo.");
        //Demonstrate basic operation by modelling increasingly complex systems.
        System.out.println("1. Two agents connected to one portal.");
        System.out.println("2. Two portals connected to eachother, each with an agent.");
        System.out.println("0. Exit");
        String input = SCANNER.nextLine();
        if (input != null)
        {
            switch (input)
            {
                case("0"):
                {
                    System.exit(0);
                }
                case("1"):
                {
                    System.out.println("\n");
                    testOne();
                    return true;
                }
                case("2"):
                {
                    System.out.println("\n");
                    testTwo();
                    return true;
                } 
                default:
                {
                    System.out.println("Please enter a valid input. \n");
                    return false;
                }
            }
        }
        return false;
    }
    
    /**main() - The driver class for the middleware to show it working.
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        MyMiddleware instance = new MyMiddleware();
        if (!instance.questionAsk())
        {
            instance.questionAsk();
        }
    }
}
