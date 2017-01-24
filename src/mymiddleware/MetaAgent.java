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
    /**An agentregisterer shared between all portals. Is used for management 
     * of portals.*/
    private static AgentRegisterer updater;
    /**A thread that the agent runs on. Allows agents to always run*/
    private Thread agentThread;
    /**A name for the agent. Used as a key in routing tables.*/
    private String name;
    
    /**A method that gives the agent something to do whilst its running.
     * This should involve handling the message queue (Linked blocking queue).
     */
    @Override
    public void run() 
    {
        System.out.println(getName() + " is running..");
        System.out.println("");
        //While the thread hasn't been interrupted.
        while (getThread()!=null && !getThread().isInterrupted())
        {
            //If the queue isn't empty, recieve the message
            if (!this.isEmpty())
            {
                recieveMessage();
            }
            //Otherwise we should sleep until there's something in the queue.
            else
            {
                stop();
            }
        }
    }
    
    /**start() - Starts the metaagent (thread).*/
    public void start () {
        System.out.println("Starting " + name);
        System.out.println("");
        setThread(new Thread (this));
        getThread().start();
    }
    
    /**stop() - Stops the thread by dereferencing it.*/
    public void stop()
    {
        if (getThread() != null)
        {
            getThread().interrupt();
        }
        System.out.println("No messages in " + name + " , shutting down.");
        //Doesn't need to do anything if it has no messages to recieve
        setThread(null); 
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
            System.out.println("");
            return true;
        }
        return false;
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
        getThread().interrupt();
    }
    
    /**scopeDown(scopeSteps) - Decrease the scope of portals that can find this 
     * agent.
     * 
     * @param scopeSteps - The number of stages to decrease the scope by.
     */
    public void scopeDown(int scopeSteps)
    {
        this.getUpdater().scopeDown(this, scopeSteps);
    }
    
    /**scopeUp(scopeSteps) - Increase the scope of portals that can find this 
     * agent.
     * 
     * @param scopeSteps - The number of stages to decrease the scope by.
     */
    public void scopeUp(int scopeSteps)
    {
        this.getUpdater().scopeUp(this, scopeSteps);
    }
    
    /**getUpdater() - Gets the updater object. As it's a single static object,
     * perhaps this is a silly way to do it and it should just have a different
     * access modifier.
     * 
     * @return - the AgentRegisterer/Updater object
     */
    public AgentRegisterer getUpdater()
    {
        return updater;
    }
    
    /**setUpdater() - Sets the updater object. It's a single static object
     * so limited to only being done once.
     * 
     * @param in - The agentRegisterer object to set to.
     * @return - Whether it was successful.
     */
    public boolean setUpdater(AgentRegisterer in)
    {
        if (getUpdater() == null)
        {
            updater = in;
            return true;
        }
        return false;
    }
}
