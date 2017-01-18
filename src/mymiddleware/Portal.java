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
    protected NodeMonitor nodeMonitor;
    private static AgentRegisterer updater;
    protected final Hashtable<String,MetaAgent> routingTable;//May be Obsolete but hashtables are synchronised whereas hashmaps are not by default. Should function the same.
    
    public Portal(String portalName)
    {
        name = portalName;
        updater = new AgentRegisterer();
        routingTable = new Hashtable<String,MetaAgent>(); 
    }
    
    public boolean attach(MetaAgent agentIn)
    {
        if (routingTable.get(agentIn.name)==null)//If not already in table
        {
            System.out.println(agentIn.name + " is not in " + this.name +" and is being added.");
            routingTable.put(agentIn.name, agentIn);
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
            if (!routingTable.containsKey(agentIn.name))
            {
                this.routingTable.put(agentIn.name, agentIn);
                return true;
            }
        }
        return false;
    }
       
    @Override    
    public void run()
    {
        System.out.println(this.name + " is running.");
        while (!agentThread.isInterrupted())
        {
            if (!this.isEmpty())
            {
                System.out.println(this.name + " has detected a message.");
                recieveMessage();
            }
            else
            {
                suspended = true;
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
            if (nodeMonitor != null)
            {
                nodeMonitor.add(incomingMessage);
                nodeMonitor.resume();
            }
            //If the message is not for the portal, but for it to relay.
            if (!incomingMessage.destination.equals(name))
            {
                System.out.println("This is not for " + name + "but is instead for " + incomingMessage.destination + ". Sending.");
                sendMessage(incomingMessage);
            }
            return true;
        }
        return false;
    }
    
    public void sendMessage(Message message)
    {
        //If it has a direct link
        if (routingTable.get(message.destination) != null)
        {
            System.out.println("There is a key in " + name + "'s routing table for " + message.destination);
            System.out.println("The value is " + routingTable.get(message.destination).name);
            (routingTable.get(message.destination)).add(message);//Offer message to 
            (routingTable.get(message.destination)).resume();
            System.out.println("Message offered to " + routingTable.get(message.destination) + " by " + name);
            return;
        }      
        for (MetaAgent a: routingTable.values())
        {
            if (a instanceof Portal)
            {
                if (((Portal) a).routingTable.containsKey(message.destination))
                {
                    System.out.println(((Portal) a).name + " has a route to the destination. Sending...");
                    a.offer(message);
                    a.resume();
                    return;
                }
            }
        }
        System.out.println(name + " could not find a way to send the message.");
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
            nodeMonitor = new NodeMonitor(this.name);
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
        System.out.println("Starting " + name);
        if (agentThread == null) {
            agentThread = new Thread (this);
        agentThread.start ();
        }
    }
   
    @Override
    public void suspend() {
      suspended = true;
    }
   
    @Override
    public synchronized void resume() {
        System.out.println(name + " has resumed.");
        suspended = false;
        notify();
        run();
   }
}

