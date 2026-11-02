package com.chandra.nbody.integration;

import com.chandra.nbody.model.Body;
import com.chandra.nbody.physics.PhysicsEngine;

import java.util.List;

public interface Integrator {

    void integrate(
            List<Body> bodies,
            PhysicsEngine physicsEngine,
            double dt
    );
}