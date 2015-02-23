package org.usfirst.frc.team3277.robot;

// TODO: Auto-generated Javadoc
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	/*
	 * Safety First!  If the robot goes rogue this is the time in seconds which the robot will stop moving.
	 */
	public final static int DRIVE_TRAIN_DEFAULT_DISABLE_TIMEOUT = 15;
	
	/*
	 * Operator input device port
	 */
	public final static int OPERATOR_INPUT_DEVICE_JOYSTICK = 0, OPERATOR_INPUT_DEVICE_CONTROLLER = 1;
	
	/*
	 * DriveTrain Motors - Keep the same for ProtoBot, Swerve, or Mecanum
	 * drive. Use the convention read like a book, front to back left to right
	 * to order. Skip #1 since it should be reserved to give to the RoboRio if
	 * need.
	 * 
	 * In a two motor bot such as the ProtoBot the FRONT motors are the ones to
	 * reference.
	 */
	public final static int FRONT_LEFT_DRIVE = 2, FRONT_RIGHT_DRIVE = 3,
					 REAR_LEFT_DRIVE = 4, REAR_RIGHT_DRIVE = 5;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

	/*
	 * Elevator setpoints broken down into 1 percent increments from zero to hundred.
	 */
	public final static int ELEVATOR_FLOOR = 0,
			ELEVATOR_MAX = 100;
	
	/*
	 * Elevator Motors
	 */
	public final static int LEFT_ELEVATOR = 6, RIGHT_ELEVATOR = 7;
	
	/*
	 * Elevator joystick buttons
	 */
	public final static int BUTTON_ELEVATOR_UP = 0,
					 BUTTON_ELEVATOR_DOWN = 1;
	
	/*
	 * Grabber motor
	 */
	public final static int GRABBER_MOTOR = 8;
	
	/*
	 * Grabber joystick buttons
	 */
	public final static int BUTTON_GRABBER_OPEN = 3;

	public final static int BUTTON_GRABBER_CLOSE = 4;
	
	/*
	 * Autonomous mode joystick button
	 */
	public final static int BUTTON_AUTONOMOUS_MODE_ENABLE = 5;
	
	/*************************** BEGIN SENSORS ***************************************/
	/*
	 * Gyro
	 */
	public final static int GYRO_ANALOG_CHANNEL = 0;
	// See Page 3 of 13 from gyro ADW22307 documentation for mV/degree/s
	public final static double GYRO_SENSITIVITY = 0.07;
	
	/*
	 * Usb Camera
	 */
	public final static String USB_CAMERA_INSTANCE = "cam1";
	public final static int USB_CAMERA_QUALITY = 50;
	
	/*
	 * LIDAR
	 */
	//public final static Port LIDAR_CHANNEL;
	
	/*************************** END SENSORS *****************************************/
}