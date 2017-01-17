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
    private NodeMonitor nodeMonitor;
    private static AgentRegisterer updater;
    protected final Hashtable<String,MetaAgent> routingTable;//May be Obsolete but hashtables are synchronised whereas hashmaps are not by default. Should function the same.
    
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

    public boolean attachMonitor(String nameIn)
    {
        if (nodeMonitor == null)
        {
            String monitorName = nameIn;
            NodeMonitor  monitor = new NodeMonitor(monitorName);
            nodeMonitor = monitor;
            return true;
        }
        return false;
    }
    
    public boolean attachMonitor(NodeMonitor monitorIn)
    {
        if (nodeMonitor == null)
        {
            nodeMonitor = monitorIn;
            return true;
        }
        return false;
    }
    
    public boolean removeMonitor()
    {
        if (nodeMonitor != null)
        {
            nodeMonitor.stop(); // need to find way of removing monitor with requested name.
            nodeMonitor = null;
            return true;
        }
        return false;
    }
    
    public boolean attachUserAgent(UserAgent agentIn)
    {
        if (agentIn.isAttachedToPortal() == true)
        {
            if (!routingTable.containsKey(agentIn.name))
            {
                agentIn.attachToPortal(this);
                this.routingTable.put(agentIn.name, agentIn);
                return true;
            }
        }
        return false;
    }
    
    public boolean attachPortal(Portal portalIn)
    {
        if (routingTable.get(portalIn.name)==null)//If not already in table
        {
            routingTable.put(portalIn.name, portalIn);
            return true;
        }
        return false;
    }
    
    public boolean registerAgent(String agentName)
    {
        //If the agent belongs to this portal.
        if (routingTable.containsKey(agentName) && routingTable.get(agentName).equals(this))
        {
           updater.registerAgent(routingTable.get(agentName), this);
           return true;
        }
        return false;
    }

    public void registerPortal(String portalName)
    {
        updater.registerPortal(this);
    }
    
    public boolean removeAgent(String name)
    {
        if (routingTable.containsKey(name))
        {
            MetaAgent agent = routingTable.get(name);
            updater.unregisterAgent(agent);
            return true;
        }
        return false;
    }
}

