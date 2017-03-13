package polymorphism.pojo;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Car")
public final class Car extends Vehicle {
    private int cargoCapacityInLiter;

    public void setCargoCapacityInLiter(int cargoCapacityInLiter) {
        this.cargoCapacityInLiter = cargoCapacityInLiter;
    }

    public int getCargoCapacityInLiter() {
        return cargoCapacityInLiter;
    }

    @Override
    public String toString() {
        return "Car [cargoCapacityInLiter=" + cargoCapacityInLiter + ", toString()=" + super.toString() + "]";
    }
}
