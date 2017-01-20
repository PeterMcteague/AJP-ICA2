/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.Collection;
import java.util.Enumeration;
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
    private final Hashtable<String,MetaAgent> routingTable;//May be Obsolete but hashtables are synchronised whereas hashmaps are not by default. Should function the same.
    
    public Portal(String portalName)
    {
        setName(portalName);
        updater = new AgentRegisterer();
        routingTable = new Hashtable<String,MetaAgent>(); 
        this.start();
    }
    
    public boolean attach(MetaAgent agentIn)
    {
        if (routingTable.get(agentIn.getName())==null)//If not already in table
        {
            System.out.println(agentIn.getName() + " is not in " + this.getName() +" and is being added.");
            routingTable.put(agentIn.getName(), agentIn);
            updater.registerAgent(agentIn, this);
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
    
    public boolean attachUserAgent(UserAgent agentIn)
    {
        if (agentIn.isAttached())
        {
            if (!routingTable.containsKey(agentIn.getName()))
            {
                this.routingTable.put(agentIn.getName(), agentIn);
                return true;
            }
        }
        return false;
    }
       
    @Override
    public synchronized boolean recieveMessage() 
    {
        if(!this.isEmpty())
        {
            Message incomingMessage = (Message) this.poll();
            if (nodeMonitor != null)
            {
                nodeMonitor.add(incomingMessage);
                nodeMonitor.resume();
            }
            //If the message is not for the portal, but for it to relay.
            if (!incomingMessage.getDestination().equals(getName()))
            {
                System.out.println("This is not for " + getName() + "but is instead for " + incomingMessage.getDestination() + ". Sending.");
                sendMessage(incomingMessage);
            }
            return true;
        }
        return false;
    }
    
    public void sendMessage(Message message)
    {
        //If it has a direct link
        if (routingTable.get(message.getDestination()) != null)
        {
            System.out.println("There is a key in " + getName() + "'s routing table for " + message.getDestination());
            System.out.println("The value is " + routingTable.get(message.getDestination()).getName());
            routingTable.get(message.getDestination()).offer(message);//Offer message to 
            routingTable.get(message.getDestination()).resume();
            System.out.println("Message offered to " + routingTable.get(message.getDestination()) + " by " + getName());
            return;
        }      
        for (MetaAgent a: routingTable.values())
        {
            if (a instanceof Portal)
            {
                if (((Portal) a).routingTable.containsKey(message.getDestination()))
                {
                    System.out.println(((Portal) a).getName() + " has a route to the destination. Sending...");
                    a.offer(message);
                    a.resume();
                    return;
                }
            }
        }
        System.out.println(getName() + " could not find a way to send the message.");
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
    
    public NodeMonitor addNewMonitor()
    {
        if (nodeMonitor == null)
        {
            nodeMonitor = new NodeMonitor(this.getName() + "Monitor");
            return nodeMonitor;
        }
        return null;
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
    
    public boolean removeAgent(String name)
    {
        if (routingTable.containsKey(name) && routingTable.get(name).equals(this))
        {
            MetaAgent agent = routingTable.get(name);
            routingTable.remove(name);
            updater.unregisterAgent(agent);
            return true;
        }
        else if (routingTable.containsKey(name))
        {
            routingTable.remove(name);
            return true;
        }
        return false;
    }

    public void registerPortal()
    {
        updater.registerPortal(this);
    }

    @Override
    public void start () {
        updater.addPortal(this);
        super.start();
    }
    
    public boolean tableAdd(String nameIn,MetaAgent valueIn)
    {
        if (!routingTable.containsKey(nameIn) && !routingTable.containsValue(valueIn))
        {
            routingTable.put(nameIn, valueIn);
            return true;
        }
        return false;
    }
    
    public boolean tableRemove(String nameIn)
    {
        if (routingTable.containsKey(nameIn))
        {
            routingTable.remove(nameIn);
            return true;
        }
        return false;
    }
    
    public MetaAgent tableGet(String nameIn)
    {
        if (routingTable.containsKey(nameIn))
        {
            return routingTable.get(nameIn);
        }
        return null;
    }
    
    public Collection<MetaAgent> tableGetValues()
    {
        return routingTable.values();
    }
    
    public Enumeration<MetaAgent> tableGetContents()
    {
        return routingTable.elements();
    }
    
    public boolean tableContainsValue(MetaAgent agentIn)
    {
        return routingTable.containsValue(agentIn);
    }
}

