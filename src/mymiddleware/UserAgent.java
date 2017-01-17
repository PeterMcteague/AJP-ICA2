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
    
    public void sendMessage(String destination, String message) 
    {
        portal.add(new Message(destination,name,message));
    }

    @Override
    public boolean recieveMessage() {
        if(!this.isEmpty())
        {
            Message incomingMessage = (Message) this.poll();
            System.out.println(name + " recieved message: " + incomingMessage.toString());
            return true;
        }
        return false;
    }
    
    public void userAgent(String nameIn) {
        name = nameIn;
        agentThread = new Thread();
        agentThread.start();
    }
    
    
    @Override
    public void run() {
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
                        Logger.getLogger(NodeMonitor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    public boolean isAttachedToPortal()
    {
        return portal != null;
    }
    
    public boolean attachToPortal(Portal portalIn)
    {
        if(portalIn != null)
        {
            portal = portalIn;
            portalIn.attachUserAgent(this);
            return true;
        }
        return false;
    }
    
    public boolean detachFromPortal(Portal portalIn)
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
//    }
    
//    public boolean decreaseScope(int scope) {
//        /*Needs implementing, use updater with some method for number of steps.*/
//    }
}
