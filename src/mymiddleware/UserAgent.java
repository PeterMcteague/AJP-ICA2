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
    
    public void sendMessage() {
        Portal.add(Message);
        }

    @Override
    public boolean recieveMessage() {
        if(!this.isEmpty())
        {
            Message incomingMessage = this.poll();
            System.out.println(name + " recieved message: " + incomingMessage.toString());
        }
    }
    
    public void userAgent(String input) {
        name = input;
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
    
    public boolean increaseScope(int scope) {
        if (scope==1)
        {
            Portal.Register(UserAgent);
        }
        if (scope==2)
        {
            Portal.Register(UserAgent);
        }
    }
    
    public boolean decreaseScope(int scope) {
        if (scope==1)
        {
            Portal.Unregister(UserAgent);
        }
        if (scope==2)
        {
            Portal.Unregister(UserAgent);
        }
    }
}
