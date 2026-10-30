package com.chandra.nbody.physics;

import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;

import java.util.List;

public class PhysicsEngine {

    private static final double G = 6.67430e-11;

    public Vector3 calculateForce(
            Body body1,
            Body body2
    ) {

        Vector3 direction =
                body2.getPosition()
                        .subtract(
                                body1.getPosition()
                        );

        double distance =
                direction.magnitude();

        Vector3 unitDirection =
                direction.normalize();

        double forceMagnitude =
                G *
                        body1.getMass() *
                        body2.getMass()
                        /
                        (distance * distance);

        return unitDirection.scale(
                forceMagnitude
        );
    }

    public Vector3 calculateAcceleration(
            Vector3 force,
            double mass
    ) {

        return force.scale(
                1.0 / mass
        );
    }

    public double calculateKineticEnergy(
            Body body
    ) {

        double speed =
                body.getVelocity()
                        .magnitude();

        return 0.5 *
                body.getMass() *
                speed *
                speed;
    }

    public double calculatePotentialEnergy(
            Body body1,
            Body body2
    ) {

        double distance =
                body2.getPosition()
                        .subtract(
                                body1.getPosition()
                        )
                        .magnitude();

        return -(
                G *
                        body1.getMass() *
                        body2.getMass()
                        /
                        distance
        );
    }

    public double calculateTotalKineticEnergy(
            List<Body> bodies
    ) {

        double total = 0;

        for (Body body : bodies) {
            total += calculateKineticEnergy(body);
        }

        return total;
    }

    public double calculateTotalPotentialEnergy(
            List<Body> bodies
    ) {

        double total = 0;

        for (int i = 0; i < bodies.size(); i++) {

            for (int j = i + 1; j < bodies.size(); j++) {

                total += calculatePotentialEnergy(
                        bodies.get(i),
                        bodies.get(j)
                );
            }
        }

        return total;
    }

    public double calculateTotalEnergy(
            List<Body> bodies
    ) {

        return calculateTotalKineticEnergy(bodies)
                + calculateTotalPotentialEnergy(bodies);
    }
}