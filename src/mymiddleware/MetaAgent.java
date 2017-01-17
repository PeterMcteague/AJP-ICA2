/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.concurrent.LinkedBlockingQueue;

/**MetaAgent - A class to create MetaAgents from.
 * 
 * Extend MetaAgent to create a middleware agent.
 * Middleware agents are entities representing systems
 * that messages are sent to, to tell the system something.
 * 
 * @author Peter
 */
public abstract class MetaAgent extends LinkedBlockingQueue implements Runnable
{
    /**A thread that the agent runs on. Allows agents to always run*/
    Thread agentThread;
    /**A name for the agent. Used as a key in routing tables.*/
    String name;
    
    /**A method that gives the agent something to do whilst its running.
     * This should involve handling the message queue (Linked blocking queue).
     */
    @Override
    public abstract void run();
    
    /**recieveMessage - Receive and handle the message on the end of the queue.
     * 
     * @return Returns whether the handling was successful.
     */
    public abstract boolean recieveMessage(); 
}
