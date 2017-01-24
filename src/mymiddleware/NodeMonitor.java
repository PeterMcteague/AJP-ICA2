/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**NodeMonitor - A class for monitoring node actions.
 *
 * Prints messages sent and received on a portal when attached to the portal.
 * Would extend this in future to feature a GUI.
 * 
 * @author Peter
 */
public class NodeMonitor extends MetaAgent
{
    /**A GUI to display information from the nodemonitor on.*/
    private final NodeMonitorGUI gui;
    
    /**NodeMonitor(String) - Creates and names a NodeMonitor.
     * @param nameIn - The name to give to a NodeMonitor.
     */
    public NodeMonitor(String nameIn)
    {
        setName(nameIn);
        gui = new NodeMonitorGUI(nameIn,this);
        this.start();
    }
    
    /**Closes the GUI and stops the nodemonitor from running.
     * Please dereference the nodemonitor from the agent after this.
     */
    public void stopGUI()
    {
        gui.dispose();
        super.stop();
    }
    
    /**recieveMessage() - gets the message at the front of the queue and adds it to the output on the GUI, with a timestamp.
     * 
     * Called by run().
     * 
     * @see System.currentTimeMillis
     * @return 
     */
    @Override
    public boolean recieveMessage() 
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
            gui.addToOutput("\n");
            return true;
        }
        return false;
    }
    
    /**A method that gives the agent something to do whilst its running.
     * This should involve handling the message queue (Linked blocking queue).
     */
    @Override
    public synchronized void run() 
    {
        System.out.println(getName() + " is running..");
        System.out.println("");
        //While the thread hasn't been interrupted.
        while (getThread()!=null && !getThread().isInterrupted())
        {
            //Wait until there is something
            if (!this.isEmpty())
            {
                recieveMessage();
            }
            else
            {
                stop();
            }
        }
    }
}
