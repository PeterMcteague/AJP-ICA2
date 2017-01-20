/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

/**SystemMessage - A kind of message which when recieved by a metaagent runs a 
 * system command used for maintenance of a middleware system.
 *
 * @author Joseph
 */
public class SystemMessage extends Message
{
    /*A data field which can be used in system messages for system messages to 
    run on.*/
    private final MetaAgent data; 
    
    /**SystemMessage(destination,sender,message,dataIn) - A constructor for a 
     * system message. 
     *
     * @param destination - The node for the message to be sent to.
     * @param sender - The sender of the message.
     * @param message - The message part of the message. Should be a command 
     * name i.e. suspend(),start(),stop(),addMonitor().
     * @param dataIn - For passing into methods, a metaAgent can be put into here.
     */
    public SystemMessage(String destination, String sender, String message, MetaAgent dataIn)
    {
        super(destination, sender, message);
        data = dataIn;
    }

    /**getData() - Allows for getting the data field of a message.
     * 
     * @return the data field of the message.
     */
    public MetaAgent getData()
    {
        return data;
    }
}
