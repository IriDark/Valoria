package com.idark.darkrpg.math;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class Vector3 {

    public static final Vector3 ZERO = new Vector3(0,0,0);
    public static final Vector3 DEFAULT_SCALE = new Vector3(1,1,1);

    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(double x, double y, double z) {
        this.x = (float)x;
        this.y = (float)y;
        this.z = (float)z;
    }

    public Vector3 clamp(Vector3 min, Vector3 max) {
        return new Vector3(MathUtils.clamp(x,min.x,max.x),MathUtils.clamp(y,min.y,max.y),MathUtils.clamp(z,min.z,max.z));
    }

    public Vector3 interpolate(Vector3 vector, float t){
        return new Vector3(MathUtils.interpolate(x,vector.getX(),t),MathUtils.interpolate(y,vector.getY(),t),MathUtils.interpolate(z,vector.getZ(),t));
    }

    public static Vector3 from(float x, float y, float z){
        return new Vector3(x,y,z);
    }

    public static Vector3 from(BlockPos pos){
        return new Vector3(pos.getX(),pos.getY(),pos.getZ());
    }

    public static Vector3 from(Vector3d vector) {
        return Vector3.from((float)vector.x,(float)vector.y,(float)vector.z);
    }

    public static Vector3 from(Vector3f vector) {
        return Vector3.from(vector.x(),vector.y(),vector.z());
    }

    public static Vector3 rad(float x, float y, float z){
        return new Vector3(MathUtils.rad(x),MathUtils.rad(y),MathUtils.rad(z));
    }

    public Vector3 add(Vector3 vector){
        return new Vector3(x+vector.getX(),y+vector.getY(),z+vector.getZ());
    }

    public Vector3 rotateYaw(float yaw){
        float angle = (float) Math.toRadians(yaw);
        double rotatedX = (getX()*Math.cos(angle)-getZ()*Math.sin(angle));
        double rotatedZ = (getZ()*Math.cos(angle)+getX()*Math.sin(angle));
        return new Vector3(rotatedX,getY(),rotatedZ);
    }

    public Vector3 add(Vector3 vector,float delta){
        Vector3 newVector = new Vector3(x+vector.getX(),y+vector.getY(),z+vector.getZ());
        return interpolate(newVector,delta);
    }

    public float magnitude(){
        return (float) Math.sqrt(x*x+y*y+z*z);
    }

    public float lengthSquared(){
        return x*x+y*y+z*z;
    }

    public Vector3 normalize(){
        float magnitude = magnitude();
        if (magnitude > 0) {
            return div(magnitude);
        }else{
            return new Vector3(x,y,z);
        }
    }

    public Vector3 direction(Vector3 vector){
        return vector.sub(this).normalize();
    }

    public Vector3 directionWithoutNormalize(Vector3 vector){
        return vector.sub(this);
    }

    public Vector3 rotation(){
        Vector3 dir = normalize();
        return new Vector3((float)Math.cos(dir.x),(float)Math.sin(dir.y),(float)Math.sin(dir.z));
    }

    public Vector3 rotationDegrees(){
        Vector3 dir = normalize();
        return new Vector3((float)Math.toDegrees(Math.cos(dir.x)),(float)Math.toDegrees(Math.sin(dir.y)),(float)Math.toDegrees(Math.sin(dir.z)));
    }

    public Vector2 yawPitch(){
        Vector3 dir = normalize();
        return new Vector2((float)Math.atan2(dir.x,dir.z),(float)Math.asin(dir.y));
    }

    public static Vector3 yawPitchToDirection(float yaw,float pitch) {
        double pitchRadians = Math.toRadians(pitch);
        double yawRadians = Math.toRadians(yaw);
        double sinPitch = Math.sin(pitchRadians);
        double cosPitch = Math.cos(pitchRadians);
        double sinYaw = Math.sin(yawRadians);
        double cosYaw = Math.cos(yawRadians);
        return new Vector3(-cosPitch * sinYaw, sinPitch, -cosPitch * cosYaw);
    }


    public Vector2 yawPitchDegrees(){
        Vector3 dir = normalize();
        return new Vector2((float)Math.toDegrees(Math.atan2(dir.x,dir.z)),(float)Math.toDegrees(Math.asin(dir.y)));
    }

    public float distanceTo(Vector3 vector){
        return MathHelper.sqrt(squareDistanceTo(vector));
    }

    public float squareDistanceTo(Vector3 vector){
        float resX = (this.x-vector.x)*(this.x-vector.x);
        float resY = (this.y-vector.y)*(this.y-vector.y);
        float resZ = (this.z-vector.z)*(this.z-vector.z);
        return resX+resY+resZ;
    }

    public float distanceXTo(Vector3 vector){
        float res = (this.x-vector.x)*(this.x-vector.x);
        return MathHelper.sqrt(res);
    }

    public float distanceYTo(Vector3 vector){
        float res = (this.y-vector.y)*(this.y-vector.y);
        return MathHelper.sqrt(res);
    }

    public float distanceZTo(Vector3 vector){
        float res = (this.z-vector.z)*(this.z-vector.z);
        return MathHelper.sqrt(res);
    }


    public float distanceXToSquared(Vector3 vector){
        float res = (this.x-vector.x)*(this.x-vector.x);
        return res;
    }

    public float distanceYToSquared(Vector3 vector){
        float res = (this.y-vector.y)*(this.y-vector.y);
        return res;
    }

    public float distanceZToSquared(Vector3 vector){
        float res = (this.z-vector.z)*(this.z-vector.z);
        return res;
    }

    public Vector3 sub(Vector3 vector){
        return new Vector3(x-vector.getX(),y-vector.getY(),z-vector.getZ());
    }

    public Vector3 mul(Vector3 vector){
        return new Vector3(x*vector.getX(),y*vector.getY(),z*vector.getZ());
    }

    public Vector3 div(Vector3 vector){
        return new Vector3(x/vector.getX(),y/vector.getY(),z/vector.getZ());
    }

    public Vector3 add(float x1,float y1,float z1){
        return new Vector3(x+x1,y+y1,z+z1);
    }

    public Vector3 sub(float x1,float y1,float z1){
        return new Vector3(x/x1,y/y1,z/z1);
    }

    public Vector3 mul(float x1,float y1,float z1){
        return new Vector3(x*x1,y*y1,z*z1);
    }

    public Vector3 div(float x1,float y1,float z1){
        return new Vector3(x/x1,y/y1,z/z1);
    }

    public Vector3 add(float t){
        return new Vector3(x+t,y+t,z+t);
    }

    public Vector3 sub(float t){
        return new Vector3(x-t,y-t,z-t);
    }

    public Vector3 mul(float t){
        return new Vector3(x*t,y*t,z*t);
    }

    public Vector3 div(float t){
        return new Vector3(x/t,y/t,z/t);
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }


    public void setZ(float z) {
        this.z = z;
    }

    public Vector3d toVector3d(){
        return new Vector3d(x,y,z);
    }

    public Vector3f toVector3f(){
        return new Vector3f(x,y,z);
    }

    @Override
    public String toString() {
        return "[x="+x+";y="+y+";z="+z+"]";
    }
}