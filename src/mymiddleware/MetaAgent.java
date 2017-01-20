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
    private Thread agentThread;
    /**A name for the agent. Used as a key in routing tables.*/
    private String name;
    /**Whether the runnable has been suspended or not**/
    private Boolean suspended;
    
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
    
    /**start() - Starts the metaagent (thread).*/
    public void start () {
        System.out.println("Starting " + name);
        if (agentThread == null) {
            agentThread = new Thread (this);
            agentThread.start ();
        }
    }
   
    /**resume() - Resumes the metaagent from a suspended state.*/
    public synchronized void resume() {
        System.out.println(name + " has resumed.");
        suspended = false;
        notify();
        run();
   }
    
    /**getName() - Gets the name attribute of a metaagent.
     * 
     * @return name - Returns the name of the metaagent.
     */
    public String getName()
    {
        return name;
    }
    
    /**setName() - Sets the name attribute of a metaagent.
     * NOTE: There's no check for it, but giving an agent the same name
     * as another one in a portal will mean that it cannot be added to the portal.
     * 
     * @param nameIn - The name to give to the metaagent.
     */
    public void setName(String nameIn)
    {
        name = nameIn;
    }
    
    /**getSuspended() - Returns the value of suspended.
     * 
     * @return suspended - Whether the agent is suspended.
     */
    public boolean getSuspended()
    {
        return suspended;
    }
    
    /**setSuspended() - Sets the value of suspended.
     * 
     * @param suspendIn - The value for suspended to be set to.
     */
    public void setSuspended(boolean suspendIn)
    {
        suspended = suspendIn;
    }
    
    /**getThread() - Gets the thread object that the agent is running on.
     * 
     * @return - The thread.
     */
    public Thread getThread()
    {
        return agentThread;
    }
    
    /**setThread - For setting a thread for the agent to run on.
     * 
     * @param threadIn - The thread for the agent to run on.
     */
    public void setThread(Thread threadIn)
    {
        agentThread = threadIn;
    }
    
    /**interruptThread() - Interrupts the agents thread.
     * 
     */
    public void interruptThread()
    {
        agentThread.interrupt();
    }
}
