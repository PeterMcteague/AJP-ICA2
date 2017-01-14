/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JSplitPane.LEFT;

/**NodeMonitor - A class for monitoring node actions.
 *
 * Prints messages sent and received on a portal when attached to the portal.
 * Would extend this in future to feature a GUI.
 * 
 * @author Peter
 */
public class NodeMonitor extends MetaAgent
{
    protected String name;
    private JFrame frame;
    private JTextArea output;
    private JPanel panel;
    
    /**NodeMonitor(String) - Creates and names a NodeMonitor.
     * 
     * Remember to call .agentThread.start on the monitor after creation.
     * 
     * @param nameIn - The name to give to a NodeMonitor.
     */
    public NodeMonitor(String nameIn)
    {
        name = nameIn;
        agentThread = new Thread();
        //Setting up the JFrame for the monitor
        output = new JTextArea(5, 20);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setEditable(false);
        frame = new JFrame();
        frame.setTitle("Nodemonitor: " + name);
        Container frameContent = frame.getContentPane();
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);
        panel.add(new JLabel("Output:"),BorderLayout.PAGE_START);
        panel.add(output,BorderLayout.CENTER);
        frame.pack();
        frame.setSize(400,200);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    /**run() - The run method for the nodemonitor. See runnable.
     * @see runnable
     * 
     */
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

    /**recieveMessage() - gets the message at the front of the queue and prints it.
     * 
     * Called by run().
     * 
     * @return 
     */
    @Override
    public synchronized boolean recieveMessage() 
    {
        //If the queue isn't empty.
        if(!this.isEmpty())
        {
            Message incomingMessage = this.poll();
            output.append(incomingMessage.toString() + "\n");
            return true;
        }
        return false;
    }
    
}
