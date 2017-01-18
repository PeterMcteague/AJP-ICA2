/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

/**A superclass representing messages to be sent between agents.
 *
 * @author Joseph
 */
public class Message
{
    protected String destination;
    protected String sender;
    protected String message;

    /**A constructor for the message.
     * 
     * @param destination - The name for the destination of the message.
     * @param sender - The name of agent sending the message.
     * @param message  - The message itself.
     */
    public Message(String destination, String sender, String message)
    {
        this.destination = destination;
        this.message = message;
        this.sender = sender;
    }

    /**toString() - Sets the string returned when printing the message.
     * Currently used to show messages on the nodemonitor.
     * @return - The message for printing.
     */
    @Override
    public String toString() 
    {
        return "Sender: " + this.sender + "\n"
               + "Message: " + this.message + "\n"
               + "Destination: " + this.destination; 
    }
}


