/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.DriverStationLCD;
/**
 *
 * @author Driver
 */
public final class DriverStationScreen {
    private DriverStationLCD lcd;
    public DriverStationScreen()
    {
        lcd = DriverStationLCD.getInstance();
    }

    public void printLine(int lineNum, String message)
    {
        printLine(lineNum, 1, message);        
    }
    public void printLine(int lineNum, int column, String message)
    {
        switch (lineNum)
        {
            case 1:
                lcd.println(DriverStationLCD.Line.kUser1, column, message); break;
            case 2:
                lcd.println(DriverStationLCD.Line.kUser2, column, message); break;
            case 3:
                lcd.println(DriverStationLCD.Line.kUser3, column, message); break;
            case 4:
                lcd.println(DriverStationLCD.Line.kUser4, column, message); break;
            case 5:
                lcd.println(DriverStationLCD.Line.kUser5, column, message); break;
            case 6:
                lcd.println(DriverStationLCD.Line.kUser6, column, message); break;
        }
        lcd.updateLCD();
    }
    
    public void clear()
    {
        for (int i = 0; i < 7; i++) {
            printLine(i, "                                         ");
        }
    }
}
