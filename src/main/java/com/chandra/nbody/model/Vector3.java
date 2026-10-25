package com.chandra.nbody.model;

public class Vector3 {

    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(
                this.x + other.x,
                this.y + other.y,
                this.z + other.z
        );
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(
                this.x - other.x,
                this.y - other.y,
                this.z - other.z
        );
    }

    public Vector3 scale(double factor) {
        return new Vector3(
                this.x * factor,
                this.y * factor,
                this.z * factor
        );
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public double magnitude() {
        return Math.sqrt(
                x * x +
                        y * y +
                        z * z
        );
    }

    public Vector3 normalize() {
        double magnitude = magnitude();

        return new Vector3(
                x / magnitude,
                y / magnitude,
                z / magnitude
        );
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}