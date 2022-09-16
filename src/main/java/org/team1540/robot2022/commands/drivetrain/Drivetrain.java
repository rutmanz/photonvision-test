package org.team1540.robot2022.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team1540.robot2022.Constants;

public class Drivetrain extends SubsystemBase {
    public final TalonFX driveLFront = new TalonFX(Constants.Motors.LEFT_FRONT);
    public final TalonFX driveLRear = new TalonFX(Constants.Motors.LEFT_REAR);
    public final TalonFX driveRFront = new TalonFX(Constants.Motors.RIGHT_FRONT);
    public final TalonFX driveRRear = new TalonFX(Constants.Motors.RIGHT_REAR);

    private final TalonFX[] driveMotors = {driveLFront, driveLRear, driveRFront, driveRRear};


    public Drivetrain(NeutralMode brakeType) {
        driveLRear.follow(driveLFront);
        driveRRear.follow(driveRFront);
        setNeutralMode(brakeType);
    }


    public void resetEncoders() {
        for (TalonFX motor : driveMotors) {
            motor.setSelectedSensorPosition(0);
        }
    }

    /**
     * Sets the percent output for the drivetrain motors
     *
     * @param leftPercent  the percent to run the left side at
     * @param rightPercent the percent to run the right side at
     */
    public void setPercent(double leftPercent, double rightPercent) {
        driveLFront.set(ControlMode.PercentOutput, leftPercent);
        driveRFront.set(ControlMode.PercentOutput, rightPercent);
    }

    /**
     * Stops both sides of the drivetrain
     */
    public void stopMotors() {
        this.setPercent(0, 0);
    }


    /**
     * Sets the NeutralMode for the drivetrain (either coast or brake)
     *
     * @param mode The mode to set the wheels to
     */
    public void setNeutralMode(NeutralMode mode) {
        for (TalonFX motor : driveMotors) {
            motor.setNeutralMode(mode);
        }
    }

}
