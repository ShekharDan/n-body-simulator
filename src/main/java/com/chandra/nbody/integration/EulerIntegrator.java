package com.chandra.nbody.integration;

import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;
import com.chandra.nbody.physics.PhysicsEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EulerIntegrator
        implements Integrator {

    @Override
    public void integrate(
            List<Body> bodies,
            PhysicsEngine physicsEngine,
            double dt
    ) {

        // Phase 1:
        // Calculate all accelerations using the OLD state

        Map<Body, Vector3> accelerations =
                new HashMap<>();

        for (Body body : bodies) {

            Vector3 netForce =
                    new Vector3(
                            0,
                            0,
                            0
                    );

            for (Body otherBody : bodies) {

                if (body == otherBody) {
                    continue;
                }

                Vector3 force =
                        physicsEngine.calculateForce(
                                body,
                                otherBody
                        );

                netForce =
                        netForce.add(
                                force
                        );
            }

            Vector3 acceleration =
                    physicsEngine.calculateAcceleration(
                            netForce,
                            body.getMass()
                    );

            accelerations.put(
                    body,
                    acceleration
            );
        }

        // Phase 2:
        // Update everybody together

        for (Body body : bodies) {

            Vector3 acceleration =
                    accelerations.get(
                            body
                    );

            Vector3 newVelocity =
                    body.getVelocity()
                            .add(
                                    acceleration.scale(dt)
                            );

            Vector3 newPosition =
                    body.getPosition()
                            .add(
                                    newVelocity.scale(dt)
                            );

            body.updateVelocity(
                    newVelocity
            );

            body.updatePosition(
                    newPosition
            );
        }
    }
}