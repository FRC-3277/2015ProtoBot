package org.usfirst.frc.team3277.robot.subsystems;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Class for logging and directing where log information is output to.
 */
public class Logger
{
	private String stringMostRecentError = "";
	private boolean bStringErrorSet = false;
	
	public Logger()
	{

	}

	/**
	 * The log method puts "data"... information of interest from the subsystem
	 * to the SmartDashboard. Useful in dashLog() methods.
	 */
	public void dashLogData(String key, Sendable data)
	{
		SmartDashboard.putData(key, data);
	}

	/**
	 * The log method puts Numerical information of interest from the subsystem
	 * to the SmartDashboard. Useful in dashLog() methods.
	 */
	public void dashLogNumber(String key, double value)
	{
		SmartDashboard.putNumber(key, value);
	}

	/**
	 * The log method puts String information of interest from the subsystem to
	 * the SmartDashboard. Useful in dashLog() methods.
	 */
	public void dashLogString(String key, String message)
	{
		SmartDashboard.putString(key, message);
	}
	
	/*
	 * Log debug information to RioLog.
	 */
	public void dashLogDebug(String subsystem, String message)
	{
		System.out.println(subsystem + " Debug: " + message);
	}
	
	/**
	 * Log errors to the SmartDashboard. Only one error will be represented at a
	 * time which may mask multiple errors. Particularly useful in cases where
	 * logging in catch blocks.  Logic added to prevent copious amounts of repeating 
	 * errors for flood control.
	 */
	public void dashLogError(String subsystem, String message)
	{
		if (bStringErrorSet)
		{
			// Flood control!
			if (message != stringMostRecentError)
			{
				stringMostRecentError = message;
				SmartDashboard.putString("Error" + subsystem, message);
				System.out.println(subsystem + " Error: " + message);
			}
		}
		else
		{
			bStringErrorSet = true;
			stringMostRecentError = message;
			SmartDashboard.putString("Error" + subsystem, message);
			System.out.println(subsystem + " Error: " + message);
		}
	}
}
