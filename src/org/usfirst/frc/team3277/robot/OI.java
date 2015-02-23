package org.usfirst.frc.team3277.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3277.robot.RobotMap;

// Make the commands available
import org.usfirst.frc.team3277.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * This is where UP+UP+DOWN+DOWN+LEFT+RIGHT+LEFT+RIGHT+B+A+SELECT+START means something!
 */
public class OI {
	// Mapping is sanity control!
	private RobotMap sanityMap;

	// Driver input(s)
	@SuppressWarnings("static-access")
	private Joystick joystick = new Joystick(
			sanityMap.OPERATOR_INPUT_DEVICE_JOYSTICK);
	@SuppressWarnings("static-access")
	private Joystick controller = new Joystick(
			sanityMap.OPERATOR_INPUT_DEVICE_CONTROLLER);

	public OI() {
		/*
		 * For every physical controller button create a virtual button as a
		 * failsafe in the event that the device fails and the robot cannot be
		 * interacted with.
		 */
		//SmartDashboard.putData("Elevator Floor", new SetElevatorSetpoint(sanityMap.ELEVATOR_FLOOR));
		//SmartDashboard.putData("Open Grabber", new OpenGrabber());
		//SmartDashboard.putData("Close Grabber", new CloseGrabber());
		//SmartDashboard.putdata("Elevator Up", new RaiseElevator());
		//SmartDashboard.putdata("Elevator Down", new LowerElevator());
		SmartDashboard.putData("Autonomous Mode", new Autonomous());

		// Button creation grouped by subsystem
		// Grabber buttons
		JoystickButton buttonGrabberOpen = new JoystickButton(joystick,
				sanityMap.BUTTON_GRABBER_OPEN);
		JoystickButton buttonGrabberClose = new JoystickButton(joystick,
				sanityMap.BUTTON_GRABBER_CLOSE);
		// Elevator buttons
		JoystickButton buttonElevatorUp = new JoystickButton(joystick,
				sanityMap.BUTTON_ELEVATOR_UP);
		JoystickButton buttonElevatorDown = new JoystickButton(joystick,
				sanityMap.BUTTON_ELEVATOR_DOWN);
		JoystickButton auto = new JoystickButton(joystick,
				sanityMap.BUTTON_AUTONOMOUS_MODE_ENABLE);

		// Connect the buttons to commands
		// Grabber listeners
		//buttonGrabberOpen.whenPressed(new OpenGrabber());
		//buttonGrabberClose.whenPressed(new CloseGrabber());
		// Elevator listeners
		// TODO: No idea how the elevator to button interaction will take place.
		//buttonElevatorUp.whenPressed(new RaiseElevator());
		//buttonElevatorDown.whenPressed(new LowerElevator());
		// Autonomous mode
		auto.whenPressed(new Autonomous());
	}

	public Joystick getController() {
		return controller;
	}

	public Joystick getJoystick() {
		return joystick;
	}
}