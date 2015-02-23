package org.usfirst.frc.team3277.robot.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.can.CANNotInitializedException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Technically this is NOT a subsystem.
 */
public class Jaguar extends Subsystem
{
	// Subsystem devices
	// N/A This is the component level

	static private Logger lumberjack;

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public Jaguar()
	{
		lumberjack = new Logger();
	}

	public static CANJaguar initJag(int motor)
	{
		return initJag(motor, CANJaguar.NeutralMode.Brake);
	}

	public static CANJaguar initJag(int motor, CANJaguar.NeutralMode neutralMode)
	{
		int maxRetries = 0;
		CANJaguar jag = null;

		// Attempt to initialize up to 10 times
		for (maxRetries = 0; jag == null && maxRetries < 10; maxRetries++)
		{
			try
			{
				jag = new CANJaguar(motor);
			} catch (CANNotInitializedException e)
			{
				SmartDashboard.putString("Error", "CANJaguar(" + motor + ") \"" + "\" error " + e.getMessage());
				System.out.println("CANJaguar(" + motor + ") \"" + "\" error " + e.getMessage());
				Timer.delay(.1);
			}
		}

		if (jag == null)
		{
			lumberjack.dashLogError("Jaguar", "CANJaguar(" + motor + ") \"" + "\" did not initialize, cannot drive");
		} else
		{
			try
			{
				// Set up the encoders
				// jag.configEncoderCodesPerRev(codesPerRev);
				// jag.setSpeedReference(SpeedReference.kEncoder);
				// jag.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
				jag.configNeutralMode(neutralMode);
				System.out.println("CANJaguar(" + motor + ") \"" + "\" is set up correctly");
			} catch (CANNotInitializedException ex)
			{
				lumberjack.dashLogError("Jaguar",
						"CANJaguar(" + motor + ") \"" + "\" error configuring encoder: " + ex.getMessage());
			}
		}
		return jag;
	}

	/**
	 * The log method puts information of interest from the Jaguar subsystem to
	 * the SmartDashboard.
	 */
	public void dashLog()
	{
		// SmartDashboard.putData("Key", value);
	}
}
