/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;


/**A userAgent is a type of metaagent that represents a system. 
 * Attach them to portals to sent messages between them.
 *
 * @author Adam
 */
public class UserAgent extends MetaAgent implements Runnable{
    
    /**The portal that the agent is attached to.*/
    private Portal portal;
    
    /**UserAgent(nameIn) - A constructor for a useragent.
     * 
     * @param nameIn - The name to give to the useragent
     */
    public UserAgent(String nameIn) {
        setName(nameIn);
        this.start();
    }
    
    /**sendMessage(destination,message) - Creates a usermessage to sent to 
     * another agent.
     * 
     * @param destination - The messages destination
     * @param message - The message.
     */
    public synchronized void sendMessage(String destination, String message) 
    {
        portal.offer(new UserMessage(destination,getName(),message));
        portal.start();
        System.out.println(getName() + " has added a message to the queue of " + portal.getName() + ".");
        System.out.println("");
    }

    /**isAttached - whether the useragent is attached to a portal.
     * 
     * @return - Whether the useragent is attached to a portal.
     */
    public boolean isAttached()
    {
        return portal != null;
    }
    
    /**attach(portalIn) - For attaching a useragent to a portal so it can send
     * messages. Note a useragent can only be attached to one portal.
     * 
     * @param portalIn - The portal to attach it to.
     * @return - Whether it was succesful.
     */
    public boolean attach(Portal portalIn)
    {
        if(portalIn != null)
        {
            portal = portalIn;
            portalIn.attachUserAgent(this);
            return true;
        }
        return false;
    }
    
    /**detach() - Detach the useragent from a portal to move it to another one,
     * or to remove it from the system.
     * 
     * @return Whether it was able to be detached.
     */
    public boolean detach()
    {
        if(portal != null)
        {
            portal.removeAgent(this);
            portal = null;
            return true;
        }
        return false;
    }
    
    /**getPortal() - Gets the portal that the agent is attached to.
     * 
     * @return - The attached portal object.
     */
    public Portal getPortal()
    {
        return portal;
    }
}
