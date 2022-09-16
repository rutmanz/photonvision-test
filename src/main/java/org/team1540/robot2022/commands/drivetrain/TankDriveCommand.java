package org.team1540.robot2022.commands.drivetrain;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TankDriveCommand extends CommandBase {
    private final Drivetrain drivetrain;
    private final XboxController controller;

    public TankDriveCommand(Drivetrain drivetrain, XboxController controller) {
        this.drivetrain = drivetrain;
        this.controller = controller;
        addRequirements(drivetrain);
    }

    /**
     * Converts the input percent to a meter per second value
     *
     * @param inputPercent The percent input
     * @return The speed in meters per second
     */
    public double calculateMPS(double inputPercent) {
        double percent;

        if (inputPercent > 1)
            percent = 1;
        else if (inputPercent < -1)
            percent = -1;
        else
            percent = inputPercent;
        double maximumSpeed = SmartDashboard.getNumber("drivetrain/tankDrive/maxVelocity", 1);
        return percent * maximumSpeed;
    }


    /**
     * Applies the deadzone and converts inputs to meters per second
     * 
     * @param value The joystick input
     * 
     * @return The value after deadzone is checked and the units are converted
     */
    private double applyJoystickModifiers(double value) {
        double percentOutput = value - controller.getLeftTriggerAxis() + controller.getRightTriggerAxis();
        return calculateMPS(percentOutput);
    }


    public void execute() {
        double valueR = applyJoystickModifiers(controller.getLeftY());
        double valueL = applyJoystickModifiers(controller.getRightY());
        drivetrain.setPercent(valueL, valueR);
    }
}
