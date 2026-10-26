package com.chandra.nbody.integration;

import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;

public interface Integrator {

    void integrate(
            Body body,
            Vector3 acceleration,
            double dt
    );
}