package org.team1540.robot2022;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import org.photonvision.common.hardware.VisionLEDMode;
import org.team1540.robot2022.commands.drivetrain.Drivetrain;
import org.team1540.robot2022.commands.drivetrain.PointToTarget;
import org.team1540.robot2022.commands.drivetrain.TankDriveCommand;
import org.team1540.robot2022.utils.ChickenPhotonCamera;

public class RobotContainer {
    // Hardware

    public final ChickenPhotonCamera limelight = new ChickenPhotonCamera("limelight");

    public final Drivetrain drivetrain = new Drivetrain(NeutralMode.Brake);
    // Controllers
    public final XboxController driverController = new XboxController(0);

    public RobotContainer() {
        initSmartDashboard();
        configureButtonBindings();
        limelight.setLED(VisionLEDMode.kOff);
        drivetrain.setDefaultCommand(new TankDriveCommand(drivetrain, driverController));
    }

    private void configureButtonBindings() {
        new Trigger(driverController::getLeftBumper).whileActiveOnce(
                new PointToTarget(drivetrain, limelight, (int) SmartDashboard.getNumber("pointToTarget/targetID", 1))
        );
    }

    private void initSmartDashboard() {
        SmartDashboard.setDefaultNumber("pointToTarget/kP", 0.006);
        SmartDashboard.setDefaultNumber("pointToTarget/kI", 0);
        SmartDashboard.setDefaultNumber("pointToTarget/kD", 0.015);
        SmartDashboard.setDefaultNumber("pointToTarget/targetID", 1);
    }

}
