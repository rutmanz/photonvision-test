package org.team1540.robot2022.commands.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.team1540.robot2022.utils.ChickenPhotonCamera;

import java.util.Optional;

public class PointToTarget extends CommandBase {
    // Overridden later
    PIDController turnController = new PIDController(0, 0, 0);
    private final Drivetrain drivetrain;
    private final ChickenPhotonCamera limelight;
    private final int targetID;

    // A little testing says kP=0.7 and kD=0.4 are fairly strong.


    public PointToTarget(Drivetrain drivetrain, ChickenPhotonCamera limelight, int targetID) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;
        this.targetID = targetID;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        System.out.println("PTT Initialized");
    }

    private double getYawToTarget() {
        Optional<PhotonTrackedTarget> target = limelight.getTarget(targetID);
        return target.map(PhotonTrackedTarget::getYaw).orElse(0.0);
    }


    public void execute() {
        double p = SmartDashboard.getNumber("pointToTarget/kP", 0.006);
        double i = SmartDashboard.getNumber("pointToTarget/kI", 0);
        double d = SmartDashboard.getNumber("pointToTarget/kD", 0.015);
        turnController.setPID(p, i, d);
        double rotation = turnController.calculate(getYawToTarget(), 0)/2;
        drivetrain.setPercent(-rotation, rotation);
    }

    @Override
    public void end(boolean isInterrupted) {
        drivetrain.stopMotors();
    }
}
