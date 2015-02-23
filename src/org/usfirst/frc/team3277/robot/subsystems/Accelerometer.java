package org.usfirst.frc.team3277.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

/**
 *
 */
public class Accelerometer extends Subsystem
{
	// Subsystem devices
	BuiltInAccelerometer accelerometer = null;

	static private Logger lumberjack;

	// Variables
	private double xVal = 0;
	private double yVal = 0;
	private double zVal = 0;

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Instantiates a new accelerometer.
	 */
	public Accelerometer()
	{
		super();
		lumberjack = new Logger();

		// The BuiltInAccelerometer default Range value is k8G. The
		// accelerometer is k16G measurement capable, but not enabled in the
		// code.
		accelerometer = new BuiltInAccelerometer(Range.k2G);
	}

	public void getDataUpdate()
	{
		xVal = accelerometer.getX();
		yVal = accelerometer.getY();
		zVal = accelerometer.getZ();
	}

	public double getX()
	{
		return this.xVal;
	}

	public double getY()
	{
		return this.yVal;
	}

	public double getZ()
	{
		return this.zVal;
	}

	/**
	 * The log method puts information of interest from the Accelerometer
	 * subsystem to the SmartDashboard.
	 */
	public void dashLog()
	{
		getDataUpdate();
		lumberjack.dashLogNumber("Acc-X", this.getX());
		lumberjack.dashLogNumber("Acc-Y", this.getY());
		lumberjack.dashLogNumber("Acc-Z", this.getZ());
	}
}
