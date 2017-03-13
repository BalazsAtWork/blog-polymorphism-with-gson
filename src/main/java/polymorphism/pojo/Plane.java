package polymorphism.pojo;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Plane")
public final class Plane extends Vehicle {
    private int wingspanInMeter;

    public void setWingspanInMeter(int wingspanInMeter) {
        this.wingspanInMeter = wingspanInMeter;
    }

    public int getWingspanInMeter() {
        return wingspanInMeter;
    }

    @Override
    public String toString() {
        return "Plane [wingspanInMeter=" + wingspanInMeter + ", toString()=" + super.toString() + "]";
    }
}
