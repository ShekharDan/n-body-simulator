package com.chandra.nbody.integration;

import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;

public class EulerIntegrator
        implements Integrator {

    @Override
    public void integrate(
            Body body,
            Vector3 acceleration,
            double dt
    ) {

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

        body.updateVelocity(newVelocity);
        body.updatePosition(newPosition);
    }
}