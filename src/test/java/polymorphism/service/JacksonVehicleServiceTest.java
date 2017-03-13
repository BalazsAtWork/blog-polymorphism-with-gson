package polymorphism.service;

import polymorphism.service.JacksonVehicleService;
import polymorphism.service.VehicleService;

public class JacksonVehicleServiceTest extends VehicleServiceTest {

    protected VehicleService createVehicleService() {
        return new JacksonVehicleService();
    }

}
