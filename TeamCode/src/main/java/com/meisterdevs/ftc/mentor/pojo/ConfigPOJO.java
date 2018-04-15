package com.meisterdevs.ftc.mentor.pojo;

/**
 * Created by burgera on 1/1/18.
 */

public class ConfigPOJO {

    String name;
    String frontLeftMotorName;
    String frontRightMotorName;
    String backLeftMotorName;
    String backRightMotorName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrontLeftMotorName() {
        return frontLeftMotorName;
    }

    public void setFrontLeftMotorName(String frontLeftMotorName) {
        this.frontLeftMotorName = frontLeftMotorName;
    }

    public String getFrontRightMotorName() {
        return frontRightMotorName;
    }

    public void setFrontRightMotorName(String frontRightMotorName) {
        this.frontRightMotorName = frontRightMotorName;
    }

    public String getBackLeftMotorName() {
        return backLeftMotorName;
    }

    public void setBackLeftMotorName(String backLeftMotorName) {
        this.backLeftMotorName = backLeftMotorName;
    }

    public String getBackRightMotorName() {
        return backRightMotorName;
    }

    public void setBackRightMotorName(String backRightMotorName) {
        this.backRightMotorName = backRightMotorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigPOJO that = (ConfigPOJO) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (frontLeftMotorName != null ? !frontLeftMotorName.equals(that.frontLeftMotorName) : that.frontLeftMotorName != null)
            return false;
        if (frontRightMotorName != null ? !frontRightMotorName.equals(that.frontRightMotorName) : that.frontRightMotorName != null)
            return false;
        if (backLeftMotorName != null ? !backLeftMotorName.equals(that.backLeftMotorName) : that.backLeftMotorName != null)
            return false;
        return backRightMotorName != null ? backRightMotorName.equals(that.backRightMotorName) : that.backRightMotorName == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (frontLeftMotorName != null ? frontLeftMotorName.hashCode() : 0);
        result = 31 * result + (frontRightMotorName != null ? frontRightMotorName.hashCode() : 0);
        result = 31 * result + (backLeftMotorName != null ? backLeftMotorName.hashCode() : 0);
        result = 31 * result + (backRightMotorName != null ? backRightMotorName.hashCode() : 0);
        return result;
    }
}
