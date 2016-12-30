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
 * @author Peter
 */
public class NodeMonitor extends MetaAgent
{
    protected String name;
    
    public void NodeMonitor(String nameIn)
    {
        name = nameIn;
        agentThread = new Thread();
        agentThread.start();
    }
    
    @Override
    public void run() 
    {
        //While the thread hasn't been interrupted.
        while (!agentThread.isInterrupted())
        {
            //If the queue isn't empty, recieve the message
            if (!this.isEmpty())
            {
                recieveMessage();
            }
            //Otherwise we should sleep until there's something in the queue.
            else
            {
                //We synchronize this, to own the monitor (Makes wait possible).
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

    /*Probably doesn't need syncing.
    Different objects on their owns threads.*/
    @Override
    public synchronized boolean recieveMessage() 
    {
        //If the queue isn't empty.
        if(!this.isEmpty())
        {
            Message incomingMessage = this.poll();
            System.out.println(name + " recieved message: " + incomingMessage.toString());
        }
    }
    
}
