/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.ArrayList;
import java.util.List;

/**A class for proper registering of agents across all agents.
 * A list of all portals is kept and existing portals are updated with ways to 
 * find new agents that are added.
 * @author 07mct
 */
public class AgentRegisterer 
{
    private List<Portal> portals;//A list of every portal in the system.
    
    public AgentRegisterer()
    {
        portals = new ArrayList<>();
    }
    
    /**registerAgent - Registers an agent with all portals.
     * 
     * @param agentIn - The agent to be registerd.
     * @param attachedPortal - The portal the agent is attached to.
     */
    public void registerAgent(MetaAgent agentIn, Portal attachedPortal)
    {
        for (Portal p : portals)
        {
            if (p != attachedPortal && p != agentIn)
            {
                System.out.println("REGISTERING AGENT");
                p.routingTable.put(agentIn.name, p.routingTable.get(attachedPortal.name));
            }
        }
    }
    
    /**For unregistering agents from the system.
     * 
     * @param agentIn - The agent that we want to unregister from the system.
     */
    public void unregisterAgent(MetaAgent agentIn)
    {
        for (Portal p : portals)
        {
            p.routingTable.remove(agentIn.name);
        }
    }
    
    /**registerPortal - For when a portal has been added and attached to another
     * portal. 
     * Attaches other portals by using the reference to ones that the new portal
     * is already attached to.
     * 
     * @param agentIn - The portal that has been added to the system.
     */
    public void registerPortal(Portal agentIn)
    {
        for (Portal p : portals)
        {
            if (!p.routingTable.containsValue(agentIn))
            {
               Portal referencePortal = registerPortalSearch(p,agentIn);
               if (referencePortal != null)
               {
                   /*Put the route to the portal attached to the new portal instead.
                   * As it will know about a portal attached to the new portal
                   * we look up its route to that in the routing table instead.
                   */
                   p.routingTable.put(agentIn.name, p.routingTable.get(referencePortal.name));
               }
            }
        }
        if (!portals.contains(agentIn))
        {
            portals.add((Portal) agentIn);//Add new portal to list of portals.
        }
        System.out.println("New list of portals: " + portals);
    }
    
    /**For finding a reference for a portal that is not attached to a new portal.
     * 
     * @param distantPortal - The portal that cannot find the new portal.
     * @param agentIn - The mew portal.
     * @return Returns a portal for the distantPortal to use as a reference to 
     * get to the new one.
     */
    public Portal registerPortalSearch(Portal distantPortal,Portal agentIn)
    {
        //For each portal in p's routing table (p2)
        for (MetaAgent p2 : distantPortal.routingTable.values())
        {
            if (p2 instanceof Portal)
            {
                //If p2 knows about agentIn, give p reference to p2
                if (((Portal) p2).routingTable.containsValue(agentIn))
                {
                    return (Portal) p2;
                }
            }
        }
        return null;
    }
    
    public void addPortal(Portal in)
    {
        if (!portals.contains(in))
        {
            portals.add(in);
            System.out.println("Updater added portal " + in.name);
        }
        else
        {
            System.out.println("Already in updater.");
        }
    }
}
