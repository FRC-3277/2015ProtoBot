/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Driver
 */
public class TimeDelaySwitch {
    public double StartTime;
    public double Duration;
    private boolean Triggered;
    
    public TimeDelaySwitch(double duration)
    {
        this.Duration = duration;
        Triggered = false;
    } 
    
    // Set the trigger.  If it is already set, don't do anything.
    public boolean Set()
    {                
        if (!Triggered)
        {
            Triggered = true;
            StartTime = System.currentTimeMillis();
            return true;
        }
        
        return false;
    }
    
    // Check the state of the trigger.  If the trigger was set and the timer has
    // met or surpassed the duration, reset the trigger.
    public boolean Check()
    {
        if (Triggered && System.currentTimeMillis() - StartTime > Duration)
        {
            Triggered = false;
        }
        
        return Triggered;
    }
}
