import java.util.Random;

public class Car {
    /**
     * ------- Global Declarations
     */
    private String make, model, color;
    private double price, mileage;
    private StringBuilder vin;
    /**
     * ------- Constructors
     */
    public Car() {

    }
    
    /**
     * ------- Getter/Setter Functions
     *  
     */
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
        String alphabet = "0123456789abcdefghjklmnprstuvwxyz";
        StringBuilder vin = new StringBuilder(17);

        Random randNum = new Random();
        for (int i = 0; i < vin.capacity(); i++) {
            vin.append(alphabet.charAt(randNum.nextInt(vin.capacity()))) ;
        }
       // System.out.println("\nVIN = " + Arrays.toString(vin));

       this.vin = vin;

    }

    public StringBuilder getVin() {
        return this.vin;
    }

}
