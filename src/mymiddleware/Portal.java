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

/**Portal - A metaagent that is used to relay messages between other agents.
 *
 * @author Reece
 */
public class Portal extends MetaAgent
{
    /**An optional nodemonitor for visual monitoring of the portal.*/
    private NodeMonitor nodeMonitor;
    /**A map of agents used for routing messages to their intended location.*/
    private final Hashtable<String,MetaAgent> routingTable;//May be Obsolete but hashtables are synchronised whereas hashmaps are not by default. Should function the same.
    
    /**Portal(portalName) - A constructor used to create a portal.
     * 
     * @param portalName - The name to give to the portal.
     */
    public Portal(String portalName)
    {
        setName(portalName);
        if (getUpdater() == null)
        {
            setUpdater(new AgentRegisterer());
        }
        getUpdater().addPortal(this);
        routingTable = new Hashtable<String,MetaAgent>(); 
        this.start();
    }
    
    /**attach(agentIn) - To attach an agent directly to the portal.
     * 
     * @param agentIn - The agent to attach.
     * @return - Whether the attachment was successful.
     */
    public boolean attach(MetaAgent agentIn)
    {
        if (routingTable.get(agentIn.getName())==null)//If not already in table
        {
            System.out.println(agentIn.getName() + " is not in " + this.getName() +" and is being added.");
            routingTable.put(agentIn.getName(), agentIn);
            return true;
        }
        return false;
    }
    
    /**addMonitor(monitorIn) - For attaching a created monitor to the portal.
     * 
     * @param monitorIn - The monitor to attach.
     * @return - Whether the attachment was successful.
     */
    public boolean addMonitor(NodeMonitor monitorIn)
    {
        if (nodeMonitor == null)
        {
            nodeMonitor = monitorIn;
            return true;
        }
        return false;
    }
    
    /**getMonitor() - Gets the nodemonitor attached to the portal.
     * 
     * @return The nodemonitor object.
     */
    public NodeMonitor getMonitor()
    {
        return nodeMonitor;
    }
    
    /**attachUserAgent(agentIn) - Attach a useragent to the portal.
     * 
     * @param agentIn - The agent to attach.
     * @return - Whether it was successful.
     */
    public boolean attachUserAgent(UserAgent agentIn)
    {
        if (agentIn.isAttached() && agentIn.getPortal() == this)
        {
            if (!routingTable.containsKey(agentIn.getName()))
            {
                this.routingTable.put(agentIn.getName(), agentIn);
                return true;
            }
        }
        return false;
    }
       
    /**recieveMessage() - Reads the message from the front of the queue.
     * 
     * @return - Whether it was read successfully.
     */
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
                System.out.println("This is not for " + getName() + " but is instead for " + incomingMessage.getDestination() + ". Sending.");
                sendMessage(incomingMessage);
            }
            return true;
        }
        return false;
    }
    
    /**sendMessage(message) - Attempt to send a message from the portal.
     * 
     * @param message - The message to send.
     * @return - Whether the attempt was successful.
     */
    public boolean sendMessage(Message message)
    {
        //If it has a direct link
        if (routingTable.get(message.getDestination()) != null)
        {
            System.out.println("There is a key in " + getName() + "'s routing table for " + message.getDestination());
            System.out.println("The value is " + routingTable.get(message.getDestination()).getName());
            routingTable.get(message.getDestination()).offer(message);//Offer message to 
            routingTable.get(message.getDestination()).resume();
            System.out.println("Message offered to " + routingTable.get(message.getDestination()) + " by " + getName());
            return true;
        }      
        System.out.println(getName() + " could not find a way to send the message to " + message.getDestination());
        return false;
    }
   
    /**removeMonitor() - Attempt to remove the attached nodemonitor from the portal.
     * 
     * @return Whether it was successful.
     */
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
    
    /**addMonitor() - Add a new node monitor to the portal.
     * 
     * @return Whether the nodemonitor was able to be added.
     */
    public boolean addMonitor()
    {
        if (nodeMonitor == null)
        {
            nodeMonitor = new NodeMonitor(this.getName() + "Monitor");
            return true;
        }
        return false;
    }
    
    /**registerAgent(agentName) - Tells other agents that the attached agent is 
     * here.
     * @param agent - The attached agent.
     * @return whether the registration was successful.
     */
    public boolean registerAgent(UserAgent agent)
    {
        //If the agent belongs to this portal.
        if (routingTable.containsKey(agent.getName()) && agent.getPortal() == this)
        {
           getUpdater().registerAgent(routingTable.get(agent.getName()), this);
           return true;
        }
        return false;
    }
    
    /**removeAgent(name) - Removes a named agent from the portal
     * 
     * @param agentIn - The agent to remove.
     * @return - Whether it was succesfully removed.
     */
    public boolean removeAgent(MetaAgent agentIn)
    {
        //If the agent is attached to this portal.
        if (routingTable.containsKey(agentIn.getName()) && 
                routingTable.get(agentIn.getName()) == agentIn)
        {
            routingTable.remove(agentIn.getName());
            getUpdater().unregisterAgent(agentIn);
            return true;
        }
        //Or if it's remote.
        else if (routingTable.containsKey(agentIn.getName()))
        {
            routingTable.remove(agentIn.getName());
            return true;
        }
        return false;
    }

    /**Register the portal with the updater (Optional as we may want the 
     portal off the grid, so to speak.*/
    public void registerPortal()
    {
        getUpdater().registerPortal(this);
    }

    /**tableAdd(nameIn,valueIn) - Adds a key value pair to the table.
     * 
     * @param nameIn - The name of the agent/key
     * @param valueIn - The agent object/value
     * @return - Whether it was added
     */
    public boolean tableAdd(String nameIn,MetaAgent valueIn)
    {
        if (!routingTable.containsKey(nameIn))
        {
            System.out.println("Key " + nameIn + " , value " + valueIn.getName() + " added to " + this.getName());
            routingTable.put(nameIn, valueIn);
            return true;
        }
        return false;
    }
    
    /**tableRemove(nameIn) - Removes a named metaagent from the routing table.
     * 
     * @param nameIn - The name of the agent to remove.
     * @return - Whether it was successful.
     */
    public boolean tableRemove(String nameIn)
    {
        if (routingTable.containsKey(nameIn))
        {
            routingTable.remove(nameIn);
            return true;
        }
        return false;
    }
    
    /**tableGet(nameIn) - Gets the value of a named agent from the routing table.
     * 
     * @param nameIn - The name of the agent to get.
     * @return - The value for the named agent.
     */
    public MetaAgent tableGet(String nameIn)
    {
        if (routingTable.containsKey(nameIn))
        {
            return routingTable.get(nameIn);
        }
        return null;
    }
    
    /**tableGetvalues() - get all metaagents from the routing table.
     * 
     * @return - A collection of metaagents.
     */
    public Collection<MetaAgent> tableGetValues()
    {
        return routingTable.values();
    }

    /**tableContainsValue(agentIn) - returns whether or not a metaagent is 
     * referenced in the table.
     * 
     * @param agentIn - the agent to check if the routing table contains.
     * @return - Whether the routing table contains it.
     */
    public boolean tableContainsValue(MetaAgent agentIn)
    {
        return routingTable.containsValue(agentIn);
    }
}

