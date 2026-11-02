package com.chandra.nbody.integration;

import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;
import com.chandra.nbody.physics.PhysicsEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RK4Integrator
        implements Integrator {

    @Override
    public void integrate(
            List<Body> bodies,
            PhysicsEngine physicsEngine,
            double dt
    ) {

        // RK4 implementation coming soon
        Map<Body, Vector3> k1Position =
                new HashMap<>();

        Map<Body, Vector3> k1Velocity =
                new HashMap<>();

        for (Body body : bodies) {

            Vector3 acceleration =
                    calculateAcceleration(
                            body,
                            bodies,
                            physicsEngine
                    );

            k1Position.put(
                    body,
                    body.getVelocity()
            );

            k1Velocity.put(
                    body,
                    acceleration
            );
        }
        List<Body> halfStepBodies =
                new ArrayList<>();

        for (Body body : bodies) {

            Body copy =
                    body.copy();

            copy.updatePosition(
                    body.getPosition()
                            .add(
                                    k1Position.get(body)
                                            .scale(dt / 2.0)
                            )
            );

            copy.updateVelocity(
                    body.getVelocity()
                            .add(
                                    k1Velocity.get(body)
                                            .scale(dt / 2.0)
                            )
            );

            halfStepBodies.add(copy);
        }
        Map<Body, Vector3> k2Position =
                new HashMap<>();

        Map<Body, Vector3> k2Velocity =
                new HashMap<>();
        for (Body originalBody : bodies) {

            Body halfStepBody =
                    findBodyByName(
                            halfStepBodies,
                            originalBody.getName()
                    );

            Vector3 acceleration =
                    calculateAcceleration(
                            halfStepBody,
                            halfStepBodies,
                            physicsEngine
                    );

            k2Position.put(
                    originalBody,
                    halfStepBody.getVelocity()
            );

            k2Velocity.put(
                    originalBody,
                    acceleration
            );
        }
        List<Body> secondHalfStepBodies =
                new ArrayList<>();

        for (Body body : bodies) {

            Body copy =
                    body.copy();

            copy.updatePosition(
                    body.getPosition()
                            .add(
                                    k2Position.get(body)
                                            .scale(dt / 2.0)
                            )
            );

            copy.updateVelocity(
                    body.getVelocity()
                            .add(
                                    k2Velocity.get(body)
                                            .scale(dt / 2.0)
                            )
            );

            secondHalfStepBodies.add(copy);
        }
        Map<Body, Vector3> k3Position =
                new HashMap<>();

        Map<Body, Vector3> k3Velocity =
                new HashMap<>();
        for (Body originalBody : bodies) {

            Body halfStepBody =
                    findBodyByName(
                            secondHalfStepBodies,
                            originalBody.getName()
                    );

            Vector3 acceleration =
                    calculateAcceleration(
                            halfStepBody,
                            secondHalfStepBodies,
                            physicsEngine
                    );

            k3Position.put(
                    originalBody,
                    halfStepBody.getVelocity()
            );

            k3Velocity.put(
                    originalBody,
                    acceleration
            );
        }
        List<Body> endStepBodies =
                new ArrayList<>();

        for (Body body : bodies) {

            Body copy =
                    body.copy();

            copy.updatePosition(
                    body.getPosition()
                            .add(
                                    k3Position.get(body)
                                            .scale(dt)
                            )
            );

            copy.updateVelocity(
                    body.getVelocity()
                            .add(
                                    k3Velocity.get(body)
                                            .scale(dt)
                            )
            );

            endStepBodies.add(copy);
        }
        Map<Body, Vector3> k4Position =
                new HashMap<>();

        Map<Body, Vector3> k4Velocity =
                new HashMap<>();
        for (Body originalBody : bodies) {

            Body endStepBody =
                    findBodyByName(
                            endStepBodies,
                            originalBody.getName()
                    );

            Vector3 acceleration =
                    calculateAcceleration(
                            endStepBody,
                            endStepBodies,
                            physicsEngine
                    );

            k4Position.put(
                    originalBody,
                    endStepBody.getVelocity()
            );

            k4Velocity.put(
                    originalBody,
                    acceleration
            );
        }
        for (Body body : bodies) {

            Vector3 positionIncrement =
                    k1Position.get(body)
                            .add(
                                    k2Position.get(body)
                                            .scale(2)
                            )
                            .add(
                                    k3Position.get(body)
                                            .scale(2)
                            )
                            .add(
                                    k4Position.get(body)
                            )
                            .scale(dt / 6.0);

            Vector3 velocityIncrement =
                    k1Velocity.get(body)
                            .add(
                                    k2Velocity.get(body)
                                            .scale(2)
                            )
                            .add(
                                    k3Velocity.get(body)
                                            .scale(2)
                            )
                            .add(
                                    k4Velocity.get(body)
                            )
                            .scale(dt / 6.0);

            body.updatePosition(
                    body.getPosition()
                            .add(positionIncrement)
            );

            body.updateVelocity(
                    body.getVelocity()
                            .add(velocityIncrement)
            );
        }

    }

    private Vector3 calculateAcceleration(
            Body body,
            List<Body> bodies,
            PhysicsEngine physicsEngine
    ) {

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

            netForce =
                    netForce.add(
                            physicsEngine.calculateForce(
                                    body,
                                    otherBody
                            )
                    );
        }

        return physicsEngine.calculateAcceleration(
                netForce,
                body.getMass()
        );
    }

    private Body findBodyByName(
            List<Body> bodies,
            String name
    ) {

        for (Body body : bodies) {

            if (body.getName().equals(name)) {
                return body;
            }
        }

        throw new IllegalArgumentException(
                "Body not found: " + name
        );
    }
}