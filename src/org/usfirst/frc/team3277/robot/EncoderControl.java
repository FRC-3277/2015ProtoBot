/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Robot
 */
public class EncoderControl {
    public double speed;
    private double originalPosition;
    public double position;
    private CANJaguar jag; 
    
    public EncoderControl(CANJaguar jaguar)
    {
        Initialize(jaguar, 0);
    }
    
    public EncoderControl(CANJaguar jaguar, int startPosition)
    {
        Initialize(jaguar, startPosition);
    }
    
    private void Initialize(CANJaguar jaguar, int startPosition)
    {
        try {
            jag = jaguar;
            position = startPosition;            
            if (startPosition == 0)
            {
                originalPosition = jag.getPosition();
            }
            speed = jag.getSpeed();
        } catch (CANTimeoutException ex) {
            System.out.println("Error initializing Encoder Control. " + ex.getMessage());
        }
    }
    
    public void Update()
    {
        try {
            speed = jag.getSpeed();
            position = originalPosition - jag.getPosition();
        } catch (CANTimeoutException ex) {
            System.out.println("Error updating speed / position. " + ex.getMessage());
        }
    }
    
    public void Reset() throws CANTimeoutException
    {
        originalPosition = jag.getPosition();
    }
}
