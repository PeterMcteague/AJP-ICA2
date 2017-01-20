/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;


/**
 *
 * @author Adam
 */
public class UserAgent extends MetaAgent implements Runnable{
    
    private Portal portal;
    
    public UserAgent(String nameIn) {
        setName(nameIn);
        setThread(new Thread(this));
        this.start();
    }
    
    public void sendMessage(String destination, String message) 
    {
        synchronized(this)
        {
            portal.add(new Message(destination,getName(),message));
            portal.resume();
            System.out.println(getName() + " has added a message to the queue of " + portal.getName() + ".");
        }
    }

    public boolean isAttached()
    {
        return portal != null;
    }
    
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
    
    public boolean detach()
    {
        if(portal != null)
        {
            portal.removeAgent(getName());
            portal = null;
            return true;
        }
        return false;
    }
    
//    public boolean increaseScope(int scope) {
//        /*Needs implementing, use updater with some method for number of steps.*/
//        
//    }
//    
//    public boolean decreaseScope(int scope) {
//        /*Needs implementing, use updater with some method for number of steps.*/
//    }
}
