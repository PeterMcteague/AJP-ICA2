/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Adam
 */
public class UserAgent extends MetaAgent implements Runnable{
    
    private Portal portal;
    
    public UserAgent(String nameIn) {
        name = nameIn;
        agentThread = new Thread(this);
        this.start();
    }
    
    public void sendMessage(String destination, String message) 
    {
        synchronized(this)
        {
            portal.add(new Message(destination,name,message));
            portal.resume();
            System.out.println(name + " has added a message to the queue of " + portal.name + ".");
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
            portal.removeAgent(name);
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
