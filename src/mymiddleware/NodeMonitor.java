/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**NodeMonitor - A class for monitoring node actions.
 *
 * Prints messages sent and received on a portal when attached to the portal.
 * Would extend this in future to feature a GUI.
 * 
 * @author Peter
 */
public class NodeMonitor extends MetaAgent
{
    /**A pointer to the GUI for the nodeMonitor.*/
    private final NodeMonitorGUI gui;
    
    /**NodeMonitor(String) - Creates and names a NodeMonitor.
     * 
     * Remember to call .agentThread.start on the monitor after creation.
     * 
     * @param nameIn - The name to give to a NodeMonitor.
     */
    public NodeMonitor(String nameIn)
    {
        name = nameIn;
        gui = new NodeMonitorGUI(nameIn,this);
    }
    
    /**run() - The run method for the nodemonitor. See runnable.
     * @see runnable
     * 
     */
    @Override    
    public void run() 
    {
        System.out.println(name + " is running..");
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
                suspended = true;
                //We synchronize this, to own the monitor (Makes wait possible).
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
                        Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, "Node monitor" + name + " has stopped.", ex);
                    }
                }
            }
        }
    }

    /**Closes the GUI and stops the nodemonitor from running.
     * 
     * Make sure to dereference the nodemonitor for garbage disposal.
     * When using this have the method call NodeMonitor.close() followed by
     * nodeMonitorReference=null.
     */
    public void stop()
    {
        agentThread.interrupt();
        gui.dispose();
        System.out.println(name + " stopped");
    }
    
    /**recieveMessage() - gets the message at the front of the queue and adds it to the output on the GUI, with a timestamp.
     * 
     * Called by run().
     * 
     * @see System.currentTimeMillis
     * @return 
     */
    @Override
    public synchronized boolean recieveMessage() 
    {
        //If the queue isn't empty.
        if(!this.isEmpty())
        {
            System.out.println(name + " has recieved a message.");
            Calendar calendarInstance = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Message incomingMessage = (Message) this.poll();
            //Add message recieved with timestamp to the output on the GUI.
            gui.addToOutput(dateFormat.format(calendarInstance.getTime()) + ": " + incomingMessage.toString() + "\n");
            gui.addToOutput("");
            return true;
        }
        return false;
    }

    /**Starts the thread for the nodemonitor.*/
    @Override
    public void start () {
        System.out.println("Starting " + name);
        if (agentThread == null) {
            agentThread = new Thread (this);
            agentThread.start ();
        }
    }
   
    /**Suspends the thread for the nodemonitor.*/
    @Override
    public void suspend() {
      suspended = true;
    }
   
    /**Resumes the thread from a suspended state.*/
    @Override
    public synchronized void resume() {
        System.out.println(name + " has resumed.");
        suspended = false;
        notify();
        run();
   }
}
