package com.chandra.nbody.model;

public class Body {

    private final double mass;

    private Vector3 position;

    private Vector3 velocity;

    public double getMass() {
        return mass;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public Body(double mass, Vector3 position, Vector3 velocity) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
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
}