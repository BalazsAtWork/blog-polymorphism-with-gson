package polymorphism.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import polymorphism.pojo.Bicycle;
import polymorphism.pojo.Car;
import polymorphism.pojo.Plane;
import polymorphism.pojo.Vehicle;

public abstract class VehicleServiceTest {

    private VehicleService cut = createVehicleService();

    @Test
    public void serializeAndDeserializeCar() throws IOException {
        Car car = new Car();
        car.setMaxSpeed(250);
        car.setCargoCapacityInLiter(400);

        String carJson = cut.toJson(car);
        Vehicle vehicle = cut.fromJson(carJson);

        assertThat(vehicle).isInstanceOf(Car.class);
        assertThat(vehicle).isEqualToComparingFieldByField(car);
    }

    @Test
    public void serializeAndDeserializePlane() throws IOException {
        Plane plane = new Plane();
        plane.setMaxSpeed(850);
        plane.setWingspanInMeter(50);

        String planeJson = cut.toJson(plane);
        Vehicle vehicle = cut.fromJson(planeJson);

        assertThat(vehicle).isInstanceOf(Plane.class);
        assertThat(vehicle).isEqualToComparingFieldByField(plane);
    }

    @Test
    public void serializeAndDeserializeBicycle() throws IOException {
        Bicycle bicycle = new Bicycle();
        bicycle.setMaxSpeed(36);
        bicycle.setFrameHeight(120);

        String bicycleJson = cut.toJson(bicycle);
        Vehicle vehicle = cut.fromJson(bicycleJson);

        assertThat(vehicle).isInstanceOf(Bicycle.class);
        assertThat(vehicle).isEqualToComparingFieldByField(bicycle);
    }

    protected abstract VehicleService createVehicleService();
}
