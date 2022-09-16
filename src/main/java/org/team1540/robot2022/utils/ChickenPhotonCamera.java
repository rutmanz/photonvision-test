package org.team1540.robot2022.utils;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.Optional;

public class ChickenPhotonCamera extends PhotonCamera {

    public ChickenPhotonCamera(String cameraName) {
        super(cameraName);
    }

    public Optional<PhotonTrackedTarget> getTarget(int id) {
        for (PhotonTrackedTarget target : this.getLatestResult().getTargets()) {
            if (target.getFiducialId() == id) {
                return Optional.of(target);
            }
        }
        return Optional.empty();
    }
}
