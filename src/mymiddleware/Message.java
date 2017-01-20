/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

/**Message - An abstract message class. Extend to create a message to send 
 * around the nodes.
 *
 * @author Joseph
 */
public abstract class Message
{
    /**The destination for the message.*/
    private final String destination; 
    /**The sender of the message.*/
    private final String sender; 
    /**The message text.*/
    private final String message; 

    /**Message(destination,sender,message) - For creating a message
     * 
     * @param destination - Sets destination attribute.
     * @param sender - Sets sender attribute.
     * @param message - Sets message attribute.
     */
    public Message(String destination, String sender, String message)
    {
        this.destination = destination;
        this.message = message;
        this.sender = sender;
    }

    /**toString() - Provides a string describing the message.
     * 
     * @return - The string describing the message.
     */
    @Override
    public String toString() 
    {
        return "Sender: " + this.sender + "\n"
               + "Message: " + this.message + "\n"
               + "Destination: " + this.destination; 
    }
    
    /**getDestination() - To get the destination attribute.
     * 
     * @return destination - returns the value of destination.
     */
    public String getDestination()
    {
        return destination;
    }
    
    /**getSender() - To get the sender attribute.
     * 
     * @return sender - returns the value of the sender attribute.
     */
    public String getSender()
    {
        return sender;
    }
    
    /**getMessage() - To get the message attribute.
     * 
     * @return message - returns the value of the message attribute.
     */
    public String getMessage()
    {
        return message;
    }
}


