package com.chandra.nbody.physics;

import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;

public class PhysicsEngine {

    private static final double G = 6.67430e-11;

    public Vector3 calculateForce(
            Body body1,
            Body body2
    ) {

        Vector3 direction =
                body2.getPosition()
                        .subtract(body1.getPosition());

        double distance =
                direction.magnitude();

        Vector3 unitDirection =
                direction.normalize();
        //need to take care of divide by zero error
        double forceMagnitude =
                G * body1.getMass() * body2.getMass()
                        / (distance * distance);

        return unitDirection.scale(forceMagnitude);
    }
    public Vector3 calculateAcceleration(
            Vector3 force,
            double mass
    ) {
        return force.scale(1.0 / mass);
    }
}