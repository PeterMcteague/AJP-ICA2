/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reece
 */
public class Portal extends MetaAgent
{
    protected String name;
    private NodeMonitor nodeMonitor;
    private final Hashtable<String,MetaAgent> routingTable;//May be Obsolete but hashtables are synchronised whereas hashmaps are not by default. Should function the same.
    
    public Portal(String portalName)
    {
        name = portalName;
        agentThread = new Thread();
        routingTable = new Hashtable<String,MetaAgent>(); 
    }
    
    @Override    
    public void run()
    {
        while (!agentThread.isInterrupted())
        {
                if (!this.isEmpty())
            {
                recieveMessage();
            }
            else
            {
                synchronized(this)
                {
                    try{
                        this.wait();
                    } 
                    catch (InterruptedException ex){
                        Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    @Override
    public synchronized boolean recieveMessage() 
    {
        if(!this.isEmpty())
        {
            Message incomingMessage = (Message) this.poll();
            //If the message is not for the portal, but for it to relay.
            if (!incomingMessage.destination.equals(name))
            {
                sendMessage(incomingMessage);
            }
            return true;
        }
        return false;
    }
    
    public void sendMessage(Message message)
    {
        if (routingTable.get(message.destination) != null)
        {
            routingTable.get(message.destination).offer(message);//Offer message to 
        }        
    }
    
    public void addAgent(MetaAgent agentIn)
    {
        if (routingTable.get(agentIn.name)==null)//If not already in table
        {
            /*If it's a userAgent and has a portal already, reject. Maybe call this
            from metaAgents, rather than start in here, so that checks can be done.*/
            INCOMPLETE
            /*Use systemMessages for adding, that way you can trace a route. Maybe
            add another method for directly attatching useragents.*/
        }
    }
    
    public void removeAgent(String name)
    {
        routingTable.remove(name); // may need to find a way of removing references to removed agent from other portals.
    }
    
    public void addMonitor(String nameIn)
    {
        String monitorName = nameIn;
        NodeMonitor  monitor = new NodeMonitor(monitorName);
        nodeMonitor = monitor;
    }
    
    public void removeMonitor(String name)
    {
        nodeMonitor.stop(); // need to find way of removing monitor with requested name.
        nodeMonitor = null;
    }
}

