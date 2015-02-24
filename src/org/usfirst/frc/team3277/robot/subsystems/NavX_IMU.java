package org.usfirst.frc.team3277.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

import NavX.kauailabs.nav6.frc.IMU;
import NavX.kauailabs.navx_mxp.AHRS;

/**
 *
 */
public class NavX_IMU extends Subsystem
{
	// Subsystem devices
	
	SerialPort usbSerialPort;
	IMU imu;
	float mostRecentKnownHeading = 0;
	boolean subsystemReady = false;
	
	static private Logger lumberjack;

	public void initDefaultCommand()
	{
		
	}
	
	public NavX_IMU()
	{
		lumberjack = new Logger();
		
		usbSerialPort = new SerialPort(57600,SerialPort.Port.kUSB);
		// Using this method fails to provide heading values as expected
		//imu = new IMU(usbSerialPort);
		
		imu = new AHRS(usbSerialPort);
		
        while (imu.isCalibrating() ) {
            Timer.delay( 0.1 );
        }
        Timer.delay( 0.3 );
        imu.zeroYaw();
	}
	
	public float getHeading()
	{
        if ( !imu.isCalibrating() ) {
        	mostRecentKnownHeading = imu.getCompassHeading();
        	return mostRecentKnownHeading;
        }
		return mostRecentKnownHeading;
	}
	
	/**
	 * The log method puts information of interest from the NavX_IMU subsystem to
	 * the SmartDashboard.
	 */
	public void dashLog()
	{
		float logHeading = getHeading();
		lumberjack.dashLogNumber("NavXCompass", logHeading);
	}
}
