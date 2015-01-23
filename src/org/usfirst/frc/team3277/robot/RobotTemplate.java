/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CANJaguar.SpeedReference;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class RobotTemplate extends IterativeRobot {

    /* ----- Robot Drive ----- **/
    private final int FRONT_LEFT_JAG_CHANNEL = 1;
    private final int FRONT_RIGHT_JAG_CHANNEL = 2;
    private final int REAR_LEFT_JAG_CHANNEL = 3;
    private final int REAR_RIGHT_JAG_CHANNEL = 4;
    private CANJaguar frontLeft;
    private CANJaguar frontRight;
    private CANJaguar rearLeft;
    private CANJaguar rearRight;
    /* ----- Shooter --------- **/
    private final int FRONT_SHOOTER_JAG_CHANNEL = 5;
    private final int REAR_SHOOTER_JAG_CHANNEL = 6;
    private final int SHOOTER_FEEDER_CHANNEL = 2;
    private final int LOADER_FEEDER_CHANNEL = 1;
    private CANJaguar frontShooter;
    private CANJaguar rearShooter;
    // Toggle shooter wheel spin on/off
    ButtonToggle shooterButton;
    final int SHOOT_DURATION = 350;
    final int LOAD_DURATION = 1075;
    final int POST_LOAD_DELAY = 750;
    private boolean Shooting = false;
    private final int SHOOTER_LEFT_LIMIT_CHANNEL = 1;
    private final int SHOOTER_RIGHT_LIMIT_CHANNEL = 2;
    private boolean ShooterIsMoving = false;
    private double ShooterTime;
    
    DigitalInput ShooterLeftLimit;
    boolean leftLimitSwitch;
    DigitalInput ShooterRightLimit;
    boolean rightLimitSwitch;
    
    TimeDelaySwitch LoadDelay;
    private double PostLoadDelay;
    private boolean PostLoadDelaying;
    private Relay shooterFeeder;
    private Relay loaderFeeder;
    private Joystick joystickOne;
    private Joystick joystickTwo;
    private double driveSensitivity = 1;
    private int codesPerRev = 250;
    // The "" at the beginning is to offset the array so the motors match up with 1,2,3, etc.
    private String[] motorNames = {"", "FrontLeft", "FrontRight", "RearLeft", "RearRight", "FrontShooter", "RearShooter", "Winch"};
    private RobotDrive robotDrive;
    /* ----- Drive Wheels Encoder Information ----- **/
//    private EncoderControl frontLeftEncoder;
//    private EncoderControl rearLeftEncoder;
//    private EncoderControl frontRightEncoder;
//    private EncoderControl rearRightEncoder;
    
    /* ----- Lift Mechanism ------ */
    private final int LIFT_WINCH_JAG_CHANNEL = 7;
    private CANJaguar LiftWinch;

    /* ----- Camera ----- **/
//    private AxisCamera camera;
    
    /* ----- Autonomous ----- **/    
    private final int AUTONOMOUS_DRIVE_FORWARD = 3950;
    private final int AUTONOMOUS_DRIVE_ANGLE = 5050;
    private final int AUTONOMOUS_DRIVE_FORWARD_AT_ANGLE = 5300;
    private final int SHOOTER_WHEEL_SPEED_UP = 6000;
    private int autonomousState = 0;
    private double autonomousTimer;
    
    /* ----- Misc ----- **/
    private DriverStationScreen dss;

    private CANJaguar initJag(int motor) {
        return initJag(motor, CANJaguar.NeutralMode.kBrake);
    }

    private CANJaguar initJag(int motor, CANJaguar.NeutralMode neutralMode) {
        int maxRetries = 0;
        CANJaguar jag = null;
        // Attempt to initialize up to 10 times
        for (maxRetries = 0; jag == null && maxRetries < 10; maxRetries++) {
            try {
                jag = new CANJaguar(motor);
            } catch (Exception e) {
                System.out.println("CANJaguar(" + motor + ") \"" + motorNames[motor] + "\" error " + e.getMessage());
                Timer.delay(.1);
            }
        }
        if (jag == null) {
            System.out.println("CANJaguar(" + motor + ") \"" + motorNames[motor] + "\" did not initialize, cannot drive");
        } else {
            try {
                // Set up the encoders
//                jag.configEncoderCodesPerRev(codesPerRev);
//                jag.setSpeedReference(SpeedReference.kEncoder);
//                jag.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
                jag.configNeutralMode(neutralMode);
                System.out.println("CANJaguar(" + motor + ") \"" + motorNames[motor] + "\" is set up correctly");
            } catch (CANTimeoutException ex) {
                System.out.println("CANJaguar(" + motor + ") \"" + motorNames[motor] + "\" error configuring encoder: " + ex.getMessage());
            }
        }
        return jag;
    }

    private void updateEncoderData() {
        Watchdog.getInstance().feed();
//        frontLeftEncoder.Update();
//        rearLeftEncoder.Update();
//        frontRightEncoder.Update();
//        rearRightEncoder.Update();
        Watchdog.getInstance().feed();
    }

    private double getThrottle(Joystick joystick) {
        if (joystick == null) {
            return 0;
        }

        return Math.abs(joystick.getThrottle() - 1.0) / 2.0;
    }

    public void robotInit() {
        Watchdog.getInstance().setExpiration(0.4);        

        // Joysticks
        joystickOne = new Joystick(1);
        joystickTwo = new Joystick(2);
        try {
            // Drive motors
            frontLeft = initJag(FRONT_LEFT_JAG_CHANNEL);
            frontRight = initJag(FRONT_RIGHT_JAG_CHANNEL);
            rearLeft = initJag(REAR_LEFT_JAG_CHANNEL);
            rearRight = initJag(REAR_RIGHT_JAG_CHANNEL);
        }
        catch (Exception ex)
        {
            System.out.println("Error initializing jaguars: " + ex.getMessage());
        }
//        frontLeftEncoder = new EncoderControl(frontLeft, 0);
//        frontRightEncoder = new EncoderControl(frontRight, 0);
//        rearLeftEncoder = new EncoderControl(rearLeft, 0);
//        rearRightEncoder = new EncoderControl(rearRight, 0);

        robotDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
        robotDrive.setSafetyEnabled(false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

        // Shooter
        frontShooter = initJag(FRONT_SHOOTER_JAG_CHANNEL);
        rearShooter = initJag(REAR_SHOOTER_JAG_CHANNEL);
        shooterButton = new ButtonToggle(false);

        ShooterLeftLimit = new DigitalInput(SHOOTER_LEFT_LIMIT_CHANNEL);
        ShooterRightLimit = new DigitalInput(SHOOTER_RIGHT_LIMIT_CHANNEL);
        leftLimitSwitch = false;
        rightLimitSwitch =  false;

        LoadDelay = new TimeDelaySwitch(LOAD_DURATION);

        shooterFeeder = new Relay(SHOOTER_FEEDER_CHANNEL);
        loaderFeeder = new Relay(LOADER_FEEDER_CHANNEL);
        LiftWinch = initJag(LIFT_WINCH_JAG_CHANNEL);
        
//        camera = AxisCamera.getInstance("10.32.77.11");
//        camera.writeResolution(AxisCamera.ResolutionT.k640x480);
//        camera.writeCompression(0);
//        camera.writeBrightness(5);

        dss = new DriverStationScreen();

        flushJoystickInput(joystickOne);
        flushJoystickInput(joystickTwo);
    }

    public void flushJoystickInput(Joystick joy) {
        boolean buttonFlush = false;
        while (!buttonFlush) {
            buttonFlush = true;
            for (int buttonIdx = 1; buttonIdx <= 12; buttonIdx++) {
                if (joy.getRawButton(buttonIdx)) {
                    buttonFlush = false;
                }
            }
        }
    }

    public void SensorUpdate() {
        //updateEncoderData();
        Watchdog.getInstance().feed();
    }

    public void autonomousInit() {
        try {
            frontLeft.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            frontRight.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            rearLeft.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            rearRight.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            autonomousTimer = System.currentTimeMillis();
            autonomousState = 0;
            Shooting = false;
        } catch (CANTimeoutException ex) {
            System.out.println("Error in the autonomous init: " + ex.getMessage());
        }
    }

    private void AutonomousCenterStart()
    {
        try {
            Watchdog.getInstance().feed();
            
            rearShooter.setX(0.42);
            frontShooter.setX(0.46);
            
            if (System.currentTimeMillis() - autonomousTimer > SHOOTER_WHEEL_SPEED_UP)
            {
                if (!Shooting) {
                    ShooterTime = System.currentTimeMillis();
                    ShooterIsMoving = true;
                    Shooting = true;
                    leftLimitSwitch = false;
                    rightLimitSwitch = false;
                    PostLoadDelaying = false;
                }
                
                Shoot();
            }
            
            Watchdog.getInstance().feed();
        } catch (CANTimeoutException ex) {
            System.out.println("Error in the autonomous periodic: " + ex.getMessage());
        }
    }
    
    private void AutonomousSideStart(boolean rightSide)
    {
        try {
            rearShooter.setX(0.42);
            frontShooter.setX(0.46);
            double at = System.currentTimeMillis() - autonomousTimer;
            if (autonomousState == 0) {
                dss.printLine(1, "DRIVING " + at + " ");
                robotDrive.mecanumDrive_Cartesian(0, -.325, 0, 0);
                if (at > AUTONOMOUS_DRIVE_FORWARD)
                {
                   autonomousState++;
                   robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
                }
            }
            if (autonomousState == 1)
            {               
                dss.printLine(1, "ROTATING " + at + " ");
                if (rightSide)
                    robotDrive.mecanumDrive_Cartesian(0, 0, -.25, 0);
                else                    
                    robotDrive.mecanumDrive_Cartesian(0, 0, .25, 0);
                if (at > AUTONOMOUS_DRIVE_ANGLE)
                {
                    autonomousState++;
                    robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
                }
            }
            if (autonomousState == 2)
            {
                if (at > SHOOTER_WHEEL_SPEED_UP)
                {
                    autonomousState++;
                }
            }
//            if (autonomousState == 3)
//            {
//                robotDrive.mecanumDrive_Cartesian(0, -.25, 0, 0);
//                if (at > AUTONOMOUS_DRIVE_FORWARD_AT_ANGLE)
//                {
//                    autonomousState++;
//                    robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
//                }
//            }
            if (autonomousState == 3)
            {
                AutonomousCenterStart();
            }
            
        } catch (CANTimeoutException ex) {
            System.out.println("Error in the autonomous periodic: " + ex.getMessage());
        }
    }
    
    public void autonomousPeriodic() {        
        // LEFT SIDE START
        //AutonomousSideStart(false);
        
        
        // CENTER START
        //AutonomousCenterStart();
        
        
        // RIGHT SIDE START
        AutonomousSideStart(true);
    }

    public void teleopInit() {
        try {

            frontLeft.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            frontRight.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            rearLeft.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            rearRight.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            Shooting = false;
//            frontLeftEncoder.Reset();
//            frontRightEncoder.Reset();
//            rearLeftEncoder.Reset();
//            rearRightEncoder.Reset();

            dss.clear();

        } catch (CANTimeoutException ex) {
            System.out.println("Error in the teleop init: " + ex.getMessage());
        }
    }
    
    private void DriveControl() {
        Watchdog.getInstance().feed();
        CheckLimits();
        // Drive sensitivity so that we can throttle the drive speed down for more fine control.
        if (joystickOne.getRawButton(2)) {
            driveSensitivity = .3;
        } else {
            driveSensitivity = 1;
        }
        // Set up the driving mechanism... the driving is set from the X, Y, and twist of the joystick.        
        robotDrive.mecanumDrive_Cartesian(driveSensitivity * joystickOne.getX(), driveSensitivity * joystickOne.getY(), driveSensitivity * joystickOne.getTwist(), 0);
        Watchdog.getInstance().feed();
        CheckLimits();
    }

    private void ShootControl() {
        CheckLimits();
        // Control the shooter feeder -- the paddle that moves the frisbee into the shooter wheels.
        if (joystickOne.getTrigger()) {
            if (!Shooting) {
                ShooterIsMoving = true;
                Shooting = true;
                leftLimitSwitch = false;
                rightLimitSwitch = false;
                PostLoadDelaying = false;
                ShooterTime = System.currentTimeMillis();
            }
        }        
        dss.printLine(3, "Shooting: " + Shooting + "  ");        
        Shoot();
        CheckLimits();
    }
    
    private void CheckLimits()
    {
        Watchdog.getInstance().feed();
        if (Shooting)
        {
            dss.printLine(5, "LLS:" + !ShooterLeftLimit.get() + "|RLS:" + !ShooterRightLimit.get() + "   ");
            if (!ShooterLeftLimit.get() && (System.currentTimeMillis() - ShooterTime) > SHOOT_DURATION)
            {                
                shooterFeeder.set(Relay.Value.kOff);
                leftLimitSwitch = true;
            }
        }
        Watchdog.getInstance().feed();
    }
    
    public void Shoot() {
        CheckLimits();
        // If we're not shooting, make sure the shooter and the loader are off        
        
        if (!Shooting) {
            CheckLimits();
            shooterFeeder.set(Relay.Value.kOff);
            loaderFeeder.set(Relay.Value.kOff);
            // Don't do anything else
            return;
        }
        
        // if the shooter paddle is moving...
        if (ShooterIsMoving) {
            CheckLimits();           
            shooterFeeder.set(Relay.Value.kForward);
            // if the left limit switch is hit, stop the shooter paddle and kick
            // on the loader
            if (leftLimitSwitch) {
                ShooterIsMoving = false;
            }
            if (!ShooterIsMoving)
            {
                LoadDelay.Set();
            }
        } // if the shooter paddle isn't moving
        else {
            Watchdog.getInstance().feed();
            // Stop moving the shooter paddle
            shooterFeeder.set(Relay.Value.kOff);
            // If the load delay is on, then move the loader motor            
            dss.printLine(4, "LOADER: " + LoadDelay.Check());
            if (LoadDelay.Check()) {
                loaderFeeder.set(Relay.Value.kForward);
            } // Turn off the loader engine.
            else {
                loaderFeeder.set(Relay.Value.kOff);
                if (!PostLoadDelaying)
                {
                   PostLoadDelaying = true;                    
                   PostLoadDelay = System.currentTimeMillis();
                }
                else
                {
                    if (System.currentTimeMillis() - PostLoadDelay > POST_LOAD_DELAY)
                    {
                        Shooting = false;
                    }
                }                                
            }
        }
        Watchdog.getInstance().feed();
    }
    
    private void ShooterWheelControl() 
    {
        CheckLimits();
        // Check the shooter toggle button.
        if (joystickOne.getRawButton(3)) {
            shooterButton.Toggle();
        }
        // If the toggle is on, spin up the shooter 
        // setX will give the jaguar the specified voltage.
        if (shooterButton.toggle) {
            try {
                CheckLimits();
                double rearShooterSpeed = 0.42 * (1 + getThrottle(joystickOne) * .1);
                double frontShooterSpeed = 0.46 * (1 + getThrottle(joystickOne) * .1);
                rearShooter.setX(rearShooterSpeed);
                frontShooter.setX(frontShooterSpeed);
                dss.printLine(1, "RSWS: " + rearShooter.getX());
                dss.printLine(2, "FSWS: " + frontShooter.getX());
            } catch (CANTimeoutException ex) {
                System.out.println("Error setting shooter speeds: " + ex.getMessage());
            }
        } // Otherwise, turn it off.
        else {
            try {
                CheckLimits();
                rearShooter.setX(0);
                frontShooter.setX(0);
            } catch (CANTimeoutException ex) {
                System.out.println("Error setting shooter speeds: " + ex.getMessage());
            }
        }
        Watchdog.getInstance().feed();
    }
    
    private void WinchControl()
    {
        CheckLimits();
        // Winch control
        if (joystickTwo.getRawButton(2))
        {
            try {
                LiftWinch.setX(.75);
            } catch (CANTimeoutException ex) {
                System.out.println("Error setting lift winch speed: " + ex.getMessage());
            }
        }        
        else if (joystickTwo.getRawButton(4))
        {
            try
            {
                LiftWinch.setX(-.75);
            }
            catch (CANTimeoutException ex) {
                System.out.println("Error setting lift winch speed: " + ex.getMessage());
            }
        }
        else
        {
            try
            {
                LiftWinch.setX(0);
            }
            catch (CANTimeoutException ex) {
                System.out.println("Error setting lift winch speed: " + ex.getMessage());
            }
        }
        Watchdog.getInstance().feed();
    }

    public void teleopPeriodic() {
        CheckLimits();
        DriveControl();
        ShooterWheelControl();        
        WinchControl();               
        ShootControl();
        SensorUpdate();
        CheckLimits();
    }
}