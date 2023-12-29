package Model;

public class Car {
    private int id;
    private int arrivalTime;

    public Car(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
