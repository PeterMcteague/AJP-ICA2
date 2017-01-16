/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reece
 */
public class Portal extends MetaAgent
{
    protected String name;
    List<NodeMonitor> nodeMonitors = new ArrayList<>();
    Hashtable routingTable = new Hashtable(); // didn't read much into it but I think Hashtables are synchronised whereas hashmaps are not by default. regardless, they function more or less the same.
    
    public Portal(String portalName)
    {
        name = portalName;
        agentThread = new Thread();
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
            return true;
        }
        return false;
    }
    
    public void sendMessage(Message message)
    {
        (routingTable.get(message.destination))//.offer(message);
    }
    
    public void addAgent(String name)
    {
        routingTable.put(name, path); // not sure how we want to show mappings in the table. it will depend on how we name our agents.
    }
    
    public void removeAgent(String name)
    {
        routingTable.remove(name); // may need to find a way of removing references to removed agent from other portals.
    }
    
    public void addMonitor(String nameIn)
    {
        String monitorName = nameIn;
        NodeMonitor  monitor = new NodeMonitor(monitorName);
        nodeMonitors.add(monitor);
    }
    
    public void removeMonitor(String name)
    {
        nodeMonitors.remove(monitor); // need to find way of removing monitor with requested name.
    }
}

