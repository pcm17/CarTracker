import java.util.Arrays;
import java.util.Random;

public class Car {

    public Car() {

    }
    private String make, model, color;
    private double price, mileage;
    private char[] vin;

    public void make(String make) {
        this.make = make;
    }

    public String getMake() {
        return this.make;
    }

    public void model(String model) {
        this.model = model;
    }

    public String getModel() {
        return this.model;
    }

    public void color(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public void price(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void mileage(double mileage) {
        this.mileage = mileage;
    }

    public double getMileage() {
        return this.mileage;
    }

    public void newVin() {
        char[] alphabet = "0123456789abcdefghjklmnprstuvwxyz".toCharArray();
        char[] vin = new char[17];

        Random randNum = new Random();
        for (int i = 0; i < vin.length; i++) {
            vin[i] = alphabet[randNum.nextInt(vin.length)];
        }
        System.out.println("VIN = " + Arrays.toString(vin));

       this.vin = vin;

    }

    public char[] getVin() {
        return this.vin;
    }

}
