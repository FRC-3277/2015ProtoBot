package org.usfirst.frc.team3277.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;






// Each subsystem available to the robot must be included here.
import org.usfirst.frc.team3277.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3277.robot.subsystems.Logger;
//import org.usfirst.frc.team3277.robot.subsystems.Elevator;
//import org.usfirst.frc.team3277.robot.subsystems.Grabber;
//import org.usfirst.frc.team3277.robot.subsystems.UsbCamera;
import org.usfirst.frc.team3277.robot.subsystems.NavX_IMU;

// Commands to include
//import org.usfirst.frc.team3277.robot.commands.Autonomous;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
//	Command autonomousCommand;
	
	public static Logger lumberjack;
	
	// Declare the subsystems
	public static DriveTrain drivetrain;
//	public static Elevator elevator;
//	public static Grabber grabber;
//	public static UsbCamera usbCamera;
	public static NavX_IMU imu;
	public static OI operatorInterface;
	
	/*
	 * Avoiding calls to subsystems that could not initialize.
	 */
	boolean bDrivetrain = false, bElevator = false, bGrabber = false, bUsbCamera = false, bLidarSensor = false,
			bAccelerometer = false, bCompassSensor = false, bOperatorInterface = false, bImu = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		lumberjack = new Logger(); // Make first priority to enable ability to capture logging.
		
		// Initialize the subsystems
		operatorInterface = new OI();
		drivetrain = new DriveTrain();
//		elevator = new Elevator();
//		grabber = new Grabber();
//		usbCamera = new UsbCamera();
		try
		{
			imu = new NavX_IMU();
			bImu = true;
		} catch (Exception e)
		{
			lumberjack.dashLogError("Robot", "Fatal Error NavX: " + e.getMessage());
		}

		// instantiate the command used for the autonomous period
//		autonomousCommand = new Autonomous();

		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(drivetrain);
		try
		{
			if (bImu)
			{
				SmartDashboard.putData(imu);
			}
		}
		catch (Exception e)
		{
			lumberjack.dashLogError("RobotNavX", "NavX SmartDashboard Error: " + e.getMessage());
		}
//		SmartDashboard.putData(elevator);
//		SmartDashboard.putData(grabber);
//		SmartDashboard.putData(usbCamera);
	}

	/**
	 * This function is called when the autonomous period starts.
	 */
	public void autonomousInit() {
		// Launches the Autonomous command within the autonomous periodic
		// Scheduler
//		autonomousCommand.start();
	}

	/**
	 * This function is repeatedly called periodically, 20 ms or less, during autonomous mode.
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		this.dashLog();
	}

	/**
	 * This function is called when the teleop period starts.
	 */
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
//		autonomousCommand.cancel();
	}

	/**
	 * This function is repeatedly called periodically, 20 ms or less during operator control.
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		this.dashLog();
	}

	/**
	 * This function is repeatedly called periodically, 20 ms or less during test mode
	 */
	public void testPeriodic() 
	{
		// Not sure what this does.
		LiveWindow.run();
		this.dashLog();
	}
	
	/**
	 * The log method puts information of interest of the subsystems to the SmartDashboard.
	 */
    private void dashLog() {
//        elevator.dashLog();
        drivetrain.dashLog();
        
        try
		{
			if (bImu)
			{
				imu.dashLog();
			}
		} catch (Exception e)
		{
			lumberjack.dashLogError("RobotNavX", e.getMessage());
		}
    }
}