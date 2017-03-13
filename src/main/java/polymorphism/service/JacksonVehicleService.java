package polymorphism.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import polymorphism.pojo.Vehicle;

public final class JacksonVehicleService implements VehicleService {

    public String toJson(Vehicle vehicle) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(vehicle);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during serialization.", e);
        }
    }

    public Vehicle fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Vehicle.class);
        } catch (IOException e) {
            throw new RuntimeException("Error during deserialization.", e);
        }
    }
}
