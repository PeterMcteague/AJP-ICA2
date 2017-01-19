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
    
    @Override
    public void run() {
        System.out.println(name + " is running.");
        while (!agentThread.isInterrupted())
        {
            if (!this.isEmpty())
            {
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

    @Override
    public void start () {
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
