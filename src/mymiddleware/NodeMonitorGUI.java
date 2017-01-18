/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**A JFrame to show the nodemonitor to a user.
 *
 * @author 07mct
 */
public class NodeMonitorGUI extends JFrame{

    private final JTextArea output;
    private final JPanel panel;
    private final NodeMonitor monitor;
    
    /**NodeMonitorGUI - A constructor for a GUI for a nodemonitor
     * Creates new NodeMonitorGUI
     * @param nameIn - The name of the nodeMonitor.
     * @param monitorIn - The nodeMonitor to associate with this GUI.
     */
    public NodeMonitorGUI(String nameIn, NodeMonitor monitorIn) 
    {
        //Setting up the JFrame for the monitor
        monitor = monitorIn;
        output = new JTextArea(5, 20);
        JScrollPane scroll = new JScrollPane(output); 
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setEditable(false);  
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Output:"),BorderLayout.PAGE_START);
        panel.add(scroll,BorderLayout.CENTER);
        initialize(nameIn);
    }
    
    /**Initializes many JFrame properties.
    * These should be done separate from the constructor so they are put into
    * this class.
    */
    private void initialize(String nameIn)
    {
        this.setTitle("Nodemonitor: " + nameIn);
        Container frameContent = this.getContentPane();
        this.add(panel);
        this.pack();
        this.setSize(400,200);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                monitor.stop();
            }
        });
    }
    
    /**addToOutput - Adds a line to the output 
    * Simply appends a string to the JTextArea.
    *@param lineIn - The line to add to the output  
    */
    public void addToOutput(String lineIn)
    {
        output.append(lineIn);
    }
}