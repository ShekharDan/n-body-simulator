package com.chandra.nbody.simulation;

import com.chandra.nbody.integration.Integrator;
import com.chandra.nbody.model.Body;
import com.chandra.nbody.physics.PhysicsEngine;

import java.util.List;

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
        integrator.integrate(
                bodies,
                physicsEngine,
                dt
        );
    }
}