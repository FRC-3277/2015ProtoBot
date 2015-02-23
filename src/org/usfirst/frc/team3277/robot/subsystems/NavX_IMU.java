package org.usfirst.frc.team3277.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

import NavX.kauailabs.nav6.frc.IMU;

/**
 *
 */
public class NavX_IMU extends Subsystem
{
	// Subsystem devices
	
	SerialPort usbSerialPort;
	IMU imu;
	
	static private Logger lumberjack;

	public void initDefaultCommand()
	{
		
	}
	
	public NavX_IMU()
	{
		lumberjack = new Logger();
		
		usbSerialPort = new SerialPort(57600,SerialPort.Port.kUSB);
		imu = new IMU(usbSerialPort);
		
        if (!imu.isCalibrating() ) {
            Timer.delay( 0.3 );
            imu.zeroYaw();
        }
	}
	
	public float getHeading()
	{
		
		boolean is_calibrating = imu.isCalibrating();
        if ( !is_calibrating ) {
        	return imu.getCompassHeading();
        }
		return 0;
	}
	
	/**
	 * The log method puts information of interest from the NavX_IMU subsystem to
	 * the SmartDashboard.
	 */
	public void dashLog()
	{
		lumberjack.dashLogNumber("NavXCompass", getHeading());
	}
}
