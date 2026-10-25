package com.chandra.nbody.app;

import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;
import com.chandra.nbody.physics.PhysicsEngine;

public class SimulationRunner {
    public static void main(String[] args) {
        Body earth =
                new Body(
                        5.972e24,
                        new Vector3(0,0,0),
                        new Vector3(0,0,0)
                );

        Body moon =
                new Body(
                        7.348e22,
                        new Vector3(384400000,0,0),
                        new Vector3(0,1022,0)
                );
        PhysicsEngine physicsEngine =
                new PhysicsEngine();

        Vector3 force =
                physicsEngine.calculateForce(
                        earth,
                        moon
                );
        System.out.println(force);

        Vector3 acceleration =
                physicsEngine.calculateAcceleration(
                        force,
                        earth.getMass()
                );

        System.out.println(acceleration);

    }
}
