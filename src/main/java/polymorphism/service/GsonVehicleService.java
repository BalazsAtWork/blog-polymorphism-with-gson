package polymorphism.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import polymorphism.pojo.Bicycle;
import polymorphism.pojo.Car;
import polymorphism.pojo.Plane;
import polymorphism.pojo.Vehicle;

public final class GsonVehicleService implements VehicleService {

    private final RuntimeTypeAdapterFactory<Vehicle> vehicleAdapter = RuntimeTypeAdapterFactory.of(Vehicle.class, "type")//
            .registerSubtype(Car.class, "Car")//
            .registerSubtype(Plane.class, "Plane")//
            .registerSubtype(Bicycle.class, "Bicycle");

    private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(vehicleAdapter).create();

    public String toJson(Vehicle vehicle) {
        return gson.toJson(vehicle, Vehicle.class);
    }

    public Vehicle fromJson(String json) {
        return gson.fromJson(json, Vehicle.class);
    }
}
