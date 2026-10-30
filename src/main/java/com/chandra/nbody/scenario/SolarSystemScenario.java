package com.chandra.nbody.scenario;

import com.chandra.nbody.model.Body;
import com.chandra.nbody.model.Vector3;

import java.util.List;

public class SolarSystemScenario {

    private SolarSystemScenario() {
    }

    public static List<Body> createBodies() {

        Body sun =
                new Body(
                        "Sun",
                        1.989e30,
                        new Vector3(0, 0, 0),
                        new Vector3(0, 0, 0)
                );

        Body earth =
                new Body(
                        "Earth",
                        5.972e24,
                        new Vector3(
                                149_600_000_000.0,
                                0,
                                0
                        ),
                        new Vector3(
                                0,
                                29_780,
                                0
                        )
                );

        Body moon =
                new Body(
                        "Moon",
                        7.348e22,
                        new Vector3(
                                149_600_000_000.0 + 384_400_000.0,
                                0,
                                0
                        ),
                        new Vector3(
                                0,
                                29_780 + 1022,
                                0
                        )
                );

        return List.of(
                sun,
                earth,
                moon
        );
    }
}