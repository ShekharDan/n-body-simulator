package com.chandra.nbody.app;

import com.chandra.nbody.integration.EulerIntegrator;
import com.chandra.nbody.integration.Integrator;
import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;
import com.chandra.nbody.physics.PhysicsEngine;
import com.chandra.nbody.simulation.Simulation;

import java.util.List;

public class SimulationRunner {
    public static void main(String[] args) {
        Body earth =
                new Body(
                        5.972e24,
                        new Vector3(0, 0, 0),
                        new Vector3(0, 0, 0)
                );

        Body moon =
                new Body(
                        7.348e22,
                        new Vector3(384400000, 0, 0),
                        new Vector3(0, 1022, 0)
                );

        PhysicsEngine physicsEngine =
                new PhysicsEngine();

        Integrator integrator =
                new EulerIntegrator();

        Simulation simulation =
                new Simulation(
                        List.of(earth, moon),
                        physicsEngine,
                        integrator
                );
        for (int i = 0; i < 1000; i++) {

            simulation.step(1);

            if (i % 100 == 0) {
                System.out.println(
                        "Step " + i
                );

                System.out.println(
                        "Earth: " + earth.getPosition()
                );

                System.out.println(
                        "Moon : " + moon.getPosition()
                );

                System.out.println();
            }
        }

    }
}
