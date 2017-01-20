/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

/**A type of message sent between useragents.
 *
 * @author Joseph
 */
public class UserMessage extends Message
{
    /**UserMessage(Destination,Sender,Message) - Create a usermessage
     * 
     * @param destination - The messages destination
     * @param sender - The sender of the message
     * @param message - The message text.
     */
    public UserMessage(String destination, String sender, String message)
    {
        super(destination, sender, message);
    }
    
}
