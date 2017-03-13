package polymorphism.service;

import polymorphism.service.GsonVehicleService;
import polymorphism.service.VehicleService;

public class GsonVehicleServiceTest extends VehicleServiceTest {

    protected VehicleService createVehicleService() {
        return new GsonVehicleService();
    }

}
