package polymorphism.pojo;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Bicycle")
public final class Bicycle extends Vehicle {
    private int frameHeight;

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    @Override
    public String toString() {
        return "Bicycle [frameHeight=" + frameHeight + ", toString()=" + super.toString() + "]";
    }
}
