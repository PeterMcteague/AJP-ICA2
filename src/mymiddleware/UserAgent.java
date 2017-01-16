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
    
    protected String name;
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
    
//    public boolean increaseScope(int scope) {
//        /*Needs implementing, send system message to portal with number of steps
//        and this nodes name.*/
//    }
    
//    public boolean decreaseScope(int scope) {
//        /*Needs implementing, send system message to portal with number of steps
//        and this nodes name.*/
//    }
}
