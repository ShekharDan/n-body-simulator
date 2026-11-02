package com.chandra.nbody.model;

public class Body {

    private final String name;
    private final double mass;

    private Vector3 position;
    private Vector3 velocity;

    public Body(
            String name,
            double mass,
            Vector3 position,
            Vector3 velocity
    ) {
        this.name = name;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void updateVelocity(
            Vector3 velocity
    ) {
        this.velocity = velocity;
    }

    public void updatePosition(
            Vector3 position
    ) {
        this.position = position;
    }

    public Body copy() {

        return new Body(
                name,
                mass,
                new Vector3(
                        position.getX(),
                        position.getY(),
                        position.getZ()
                ),
                new Vector3(
                        velocity.getX(),
                        velocity.getY(),
                        velocity.getZ()
                )
        );
    }
}