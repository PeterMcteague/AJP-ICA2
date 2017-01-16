/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymiddleware;

/**
 *
 * @author Joseph
 */
public class Message
{
    String destination,sender,message;

    public Message(String destination, String sender, String message)
    {
        this.destination = destination;
        this.message = message;
        this.sender = sender;
    }

    @Override
    public String toString() 
    {
        return "Sender: " + this.sender + "\n"
               + "Message: " + this.message + "\n"
               + "Destination: " + this.destination; 
    }
}


