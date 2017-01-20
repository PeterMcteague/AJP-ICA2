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
    private final NodeMonitorGUI gui;
    
    /**NodeMonitor(String) - Creates and names a NodeMonitor.
     * 
     * Remember to call .agentThread.start on the monitor after creation.
     * 
     * @param nameIn - The name to give to a NodeMonitor.
     */
    public NodeMonitor(String nameIn)
    {
        setName(nameIn);
        gui = new NodeMonitorGUI(nameIn,this);
        this.start();
    }
    
    /**Closes the GUI and stops the nodemonitor from running.
     * 
     * Make sure to dereference the nodemonitor for garbage disposal.
     * When using this have the method call NodeMonitor.close() followed by
     * nodeMonitorReference=null.
     */
    public void stop()
    {
        interruptThread();
        gui.dispose();
        System.out.println(getName() + " stopped");
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
            System.out.println(getName() + " has recieved a message.");
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
   
    /*Can't use the one from metaagent because running this causes it to never 
    give another object a chance to run.*/
    @Override
    public synchronized void resume() {
        System.out.println(getName() + " has resumed.");
        setSuspended(false);
        notify();
   }
}
