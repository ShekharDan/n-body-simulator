package com.chandra.nbody.simulation;

import com.chandra.nbody.integration.Integrator;
import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;
import com.chandra.nbody.physics.PhysicsEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {

    private final List<Body> bodies;
    private final PhysicsEngine physicsEngine;
    private final Integrator integrator;

    public Simulation(
            List<Body> bodies,
            PhysicsEngine physicsEngine,
            Integrator integrator
    ) {
        this.bodies = bodies;
        this.physicsEngine = physicsEngine;
        this.integrator = integrator;
    }

    public void step(double dt) {

        // Phase 1: Calculate accelerations
        Map<Body, Vector3> accelerations =
                new HashMap<>();

        for (Body body : bodies) {

            Vector3 netForce =
                    new Vector3(0, 0, 0);

            for (Body otherBody : bodies) {

                if (body == otherBody) {
                    continue;
                }

                Vector3 force =
                        physicsEngine.calculateForce(
                                body,
                                otherBody
                        );

                netForce = netForce.add(force);
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

        // Phase 2: Integrate everyone together
        for (Body body : bodies) {
            integrator.integrate(
                    body,
                    accelerations.get(body),
                    dt
            );
        }

    }
}