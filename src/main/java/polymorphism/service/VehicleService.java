package polymorphism.service;

import polymorphism.pojo.Vehicle;

public interface VehicleService {

    public String toJson(Vehicle vehicle);

    public Vehicle fromJson(String json);
}
