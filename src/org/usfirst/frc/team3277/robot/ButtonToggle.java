/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Robot
 */
public class ButtonToggle
{
    public boolean toggle;
    private final int TOGGLE_DELAY = 1000;
    private double toggleDelay;
    
    public ButtonToggle(boolean onOff)
    {
        toggle = onOff;
        toggleDelay = System.currentTimeMillis();
    }

    public void SetToggle(boolean value)
    {
        if (value = true)
        {
            toggle = true;
            toggleDelay = System.currentTimeMillis();  
        }
        
        if (System.currentTimeMillis() -  toggleDelay > TOGGLE_DELAY)
        {
            toggle = value;
            toggleDelay = System.currentTimeMillis();                    
        }
    }
    
    public void Toggle()
    {
        if (System.currentTimeMillis() -  toggleDelay > TOGGLE_DELAY)
        {
            toggle = !toggle;
            toggleDelay = System.currentTimeMillis();                    
        }
    }
};