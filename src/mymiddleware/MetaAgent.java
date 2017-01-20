/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    /**Whether the runnable has been suspended or not**/
    Boolean suspended;
    
    /**A method that gives the agent something to do whilst its running.
     * This should involve handling the message queue (Linked blocking queue).
     */
    @Override
    public void run() 
    {
        System.out.println(name + " is running..");
        //While the thread hasn't been interrupted.
        while (!agentThread.isInterrupted())
        {
            //If the queue isn't empty, recieve the message
            if (!this.isEmpty())
            {
                recieveMessage();
            }
            //Otherwise we should sleep until there's something in the queue.
            else
            {
                suspended = true;
                //We synchronize this, to own the monitor (Makes wait possible).
                synchronized(this)
                {
                    try{
                        while(suspended) 
                        {
                            System.out.println(name + " is waiting..");
                            wait();
                        }
                    } 
                    catch (InterruptedException ex){
                        Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, "Node monitor" + name + " has stopped.", ex);
                    }
                }
            }
        }
    }
    
    /**recieveMessage - Receive and handle the message on the end of the queue.
     * 
     * @return Returns whether the handling was successful.
     */
    public boolean recieveMessage() {
        if(!this.isEmpty())
        {
            Message incomingMessage = (Message) this.poll();
            System.out.println(name + " recieved message: " + incomingMessage.toString());
            return true;
        }
        return false;
    } 
    
    public void start () {
        System.out.println("Starting " + name);
        if (agentThread == null) {
            agentThread = new Thread (this);
            agentThread.start ();
        }
    }
   
    public void suspend() {
        suspended = true;
    }
   
    public synchronized void resume() {
        System.out.println(name + " has resumed.");
        suspended = false;
        notify();
        run();
   }
}
