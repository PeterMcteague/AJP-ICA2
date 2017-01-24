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
    
    /**AgentRegisterer() - A constructor for the AgentRegisterer.*/
    public AgentRegisterer()
    {
        portals = new ArrayList<>();
    }
    
    /**registerAgent - Registers an agent with all portals, based on their 
     * reference to an attachedportal.
     * 
     * @param agentIn - The agent to be registerd.
     * @param attachedPortal - The portal the agent is attached to.
     */
    public void registerAgent(MetaAgent agentIn, Portal attachedPortal)
    {
        for (Portal p : portals)
        {
            //If the portal isn't the agent or the portal.
            if (!p.getName().equals(attachedPortal.getName()) && !p.getName().equals(agentIn.getName()))
            {
                if (p.tableGet(attachedPortal.getName()) != null)
                {
                    System.out.println("Adding reference to " + agentIn.getName() + " to " + p.getName() + " with value " + attachedPortal.getName());
                    p.tableAdd(agentIn.getName(), p.tableGet(attachedPortal.getName()));
                }
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
            p.tableRemove(agentIn.getName());
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
            if (!p.tableContainsValue(agentIn) && !p.getName().equals(agentIn.getName()))
            {
                Portal referencePortal = registerPortalSearch(p,agentIn);
                System.out.println("Registering " + agentIn.getName() + " on " + p.getName() + " with reference " + referencePortal.getName() );
                if (referencePortal != null)
                {
                    /*Put the route to the portal attached to the new portal instead.
                    * As it will know about a portal attached to the new portal
                    * we look up its route to that in the routing table instead.
                    */
                    p.tableAdd(agentIn.getName(), p.tableGet(referencePortal.getName()));
                }
            }
        }
        if (!portals.contains(agentIn))
        {
            portals.add((Portal) agentIn);//Add new portal to list of portals.
        }
    }
    
    /**For finding a reference for a portal that is not attached to a new portal.
     * 
     * @param distantPortal - The portal that cannot find the new portal.
     * @param agentIn - The new portal.
     * @return Returns a portal for the distantPortal to use as a reference to 
     * get to the new one.
     */
    public Portal registerPortalSearch(Portal distantPortal,Portal agentIn)
    {
        //For each portal in p's routing table (p2)
        for (MetaAgent p2 : distantPortal.tableGetValues())
        {
            if (p2 instanceof Portal)
            {
                //If p2 knows about agentIn, give p reference to p2
                if (((Portal) p2).tableContainsValue(agentIn))
                {
                    return (Portal) p2;
                }
            }
        }
        return null;
    }
    
    /**addPortal() - Adds a portal to the list of portals known by the system.
     * 
     * @param in - The portal to add.
     * @return - Whether the operation was successful.
     */
    public boolean addPortal(Portal in)
    {
        if (!portals.contains(in))
        {
            portals.add(in);
            System.out.println("Updater added portal " + in.getName());
            return true;
        }
        else
        {
            System.out.println("Already in updater.");
            return false;
        }
    }
    
    public List<Portal> getPortals()
    {
        return portals;
    }
    
    /**removePortal() - Removes a portal to the list of portals known by the system.
     * 
     * @param in - The portal to remove.
     * @return - Whether the operation was successful.
     */
    public boolean removePortal(Portal in)
    {
        if (portals.contains(in))
        {
            portals.remove(in);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**scopeDown(agent,steps) - Scopes down the references to a given agent, by
     * X steps.
     * 
     * @param agent - The agent to scope down references to.
     * @param steps - The number of steps to scope down by.
     */
    public void scopeDown(MetaAgent agent, int steps)
    {
        //Getting the highest scope portals for the agent.
        List<Portal> listToRemove = new ArrayList<>();
        List<Portal> newList = portals;
        for (Portal p : portals)
        {
            for (Portal q : portals)
            {
                /*If a portal contains this portal as its route to the agent
                * remove it. This leaves the outermost portal from agent.*/
                if (q.tableGet(agent.getName()).equals(p))
                {
                    System.out.println("Removing " + p.getName() + " from templist as it's not the highest scope.");
                    listToRemove.add(p);
                }
            }
        }
        newList.removeAll(listToRemove);
        System.out.println("Finished finding scope.");
        /*Templist now contains the outermost portals from the agent.
          Now we need to scope back by removing references steps times.*/
        for (Portal p : newList)
        {
            //Take the outermost portals
            Portal q = p; 
            Portal r;
            //For the number of steps
            for(int i=steps; i>0; i--)
            {
                /*If it references another portal , remove the reference and
                switch to the referenced portal*/
                if (q.tableGet(agent.getName()) instanceof Portal)
                {
                    r = (Portal) q.tableGet(agent.getName());
                    q.removeAgent(agent);
                    q = r;
                    System.out.println("Removed " + q.getName() +"'s reference and swapped to " + r.getName());
                }
                /*Or if it directly references the agent, we remove the reference
                and stop.*/
                else
                {
                    q.removeAgent(agent);
                    break;
                }
            }
        }
    }
    
    /**scopeDown(agent,steps) - Scopes up the references to a given agent, by
     * X steps.
     * 
     * @param agent - The agent to increase the scope of references to.
     * @param steps - The number of nodes to scope up by.
     */
    public void scopeUp(MetaAgent agent, int steps)
    {
        //For the number of steps
        for(int i=steps; i>0; i--)
        {
            //For all portals
            for (Portal p : portals)
            {
                //If the portal contains a reference to the agent
                if (p.tableGet(agent.getName()) != null)
                {
                    //For the portals in the routing table of the portal
                    for (MetaAgent q : p.tableGetValues())
                    {
                        //If the portal is directly linked to this one.
                        if (q instanceof Portal && p.tableGet(q.getName()) == q)
                        {
                            /*If that portal doesn't have a reference to the agent
                            add one.*/
                            if (((Portal) q).tableGet(agent.getName()) == null)
                            {
                                ((Portal) q).tableAdd(agent.getName(), p);
                            }
                        }
                    }
                }
            }
        }
    }
}
