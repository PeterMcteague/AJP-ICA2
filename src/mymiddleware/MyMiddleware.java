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

    public static Scanner scanner = new Scanner(System.in);
    
    private void testOne() throws InterruptedException
    {
        UserAgent agent1 = new UserAgent("agent1");
        UserAgent agent2 = new UserAgent("agent2");
        agent1.start();
        agent2.start();
        Portal testPortal1 = new Portal("testPortal1");
        testPortal1.start();
        agent1.attach(testPortal1);
        agent2.attach(testPortal1);
        NodeMonitor testMonitor = testPortal1.addNewMonitor();
        testMonitor.start();
        agent1.sendMessage("agent2", "First test");
        sleep(500); //So that press enter appears at a relevant time.
        System.out.println("Close the nodemonitor or stop the program to exit..");
        scanner.nextLine();
    }
    
    private void testTwo() throws InterruptedException
    {
        UserAgent agent1 = new UserAgent("agent1");
        UserAgent agent2 = new UserAgent("agent2");
        Portal testPortal1 = new Portal("testPortal1");
        Portal testPortal2 = new Portal("testPortal2");
        testPortal1.start();
        testPortal2.start();
        agent1.attach(testPortal1);
        agent2.attach(testPortal2);
        NodeMonitor testMonitor1 = testPortal1.addNewMonitor();
        NodeMonitor testMonitor2 = testPortal2.addNewMonitor();
        testMonitor1.start();
        testMonitor2.start();
        System.out.println("ATTACHING PORTALS");
        testPortal1.attach(testPortal2);
        testPortal2.attach(testPortal1);
        System.out.println("Portal 1 table: " + testPortal1.routingTable.elements());
        System.out.println("Portal 2 table: " + testPortal2.routingTable.elements());
        agent1.sendMessage("agent2", "Second test");
        sleep(500); //So that press enter appears at a relevant time.
        System.out.println("Close a nodemonitor or stop the program to exit..");
        scanner.nextLine();
    }
    
    private boolean questionAsk() throws InterruptedException
    {
        System.out.println("Type a number to select a demo.");
        //Demonstrate basic operation by modelling increasingly complex systems.
        System.out.println("1. Two agents connected to one portal.");
        System.out.println("2. Two portals connected to eachother, each with an agent.");
        System.out.println("0. Exit");
        String input = scanner.nextLine();
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
    
    /**The driver class for the middleware to show it working.
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        MyMiddleware instance = new MyMiddleware();
        if (instance.questionAsk()== false)
        {
            instance.questionAsk();
        }
    }
}
