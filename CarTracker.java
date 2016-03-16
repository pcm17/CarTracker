import java.util.InputMismatchException;
import java.util.Scanner;

public class CarTracker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int carNum = 0, choice = 10;
        boolean valid;
        CarSystem cars = new CarSystem(5);
        
        do {
            do {
                StdOut.print("(1) Add a Car\t(5) Retrieve Lowest Mileage\n(2) Update Car\t(6) Retrieve Lowest Price by Make and Model\n(3) Remove Car\t(7) Retrieve Lowest Mileage by Make and Model\n(4) Retrieve Lowest Price");
                StdOut.print("\t(0) to Exit.\nChoice: ");
                try {
                    valid = true;
                    choice = sc.nextInt();
                } catch (InputMismatchException ime) {
                    StdOut.println("\nInvalid Input. ");
                    valid = false;
                    sc.next();
                }
                if (choice > 7 || choice < 0) {
                    valid = false;
                }
            } while (!valid);
            switch (choice) {
                case 0:
                    break;
                case 1: // Add a car
                    sc = new Scanner(System.in);
                    Car car = new Car();
                    System.out.print("Make: ");
                    car.make(sc.next());

                    System.out.print("Model: ");
                    car.model(car.getMake() + " model");
                    //car.model(sc.next());

                    System.out.print("\nColor: ");
                    car.color(car.getMake() + " color");
                    //car.color(sc.next());

                    System.out.print("\nPrice: ");
                    car.price(sc.nextDouble());

                    System.out.print("Mileage: ");
                    car.mileage(sc.nextDouble());

                    car.newVin();
                    System.out.println("VIN: " + car.getVin().toString());

                    cars.insert(carNum, car); // Add car to priority Queue by price
                    carNum++; // Increment carNum to reflect adding a car
                    break;
                case 2: // Update Car
                    Car carToUpdate = null;
                    StringBuilder userVin = new StringBuilder();
                    StdOut.print("Enter VIN: ");
                    sc = new Scanner(System.in);
                    userVin.append(sc.nextLine());
                    for (int k : cars.pqByPrice()) {
                        StringBuilder vin = cars.pqByPrice().keyOf(k).getVin();
                        if (vin.toString().equals(userVin.toString())) {
                            carToUpdate = cars.pqByPrice().keyOf(k);
                            StdOut.println("Updating the " + carToUpdate.getMake());
                            break;
                        }
                    }
                    if (carToUpdate == null) {
                        StdOut.println("Did not find car.");
                        break; // Loop back to start menu
                    }
                    int field = 0;
                    do {
                        valid = true;
                        StdOut.print("What field needs updated?\n(1) Make\t(4) Price\n(2) Model\t(5) Mileage\n(3) Color\t(6) VIN\nField: ");
                        try {
                            field = sc.nextInt();
                        } catch (InputMismatchException ime) {
                            StdOut.println("\nInvalid Input. ");
                            valid = false;
                            field = 0;
                            sc.next();
                        }
                        if (field > 6 || field < 0) {
                            valid = false;
                        }
                    } while (!valid);
                    switch (field) {
                        case 1: // make
                            StdOut.print("Enter new make: ");
                            carToUpdate.make(sc.next());
                            break;
                        case 2: // model
                            StdOut.print("Enter new model: ");
                            carToUpdate.model(sc.next());
                            break;
                        case 3: // color
                            StdOut.print("Enter new color: ");
                            carToUpdate.color(sc.next());
                            break;
                        case 4: // price
                            StdOut.print("Enter new price: ");
                            carToUpdate.price(sc.nextDouble());
                            break;
                        case 5: // mileage
                            StdOut.print("Enter new mileage: ");
                            carToUpdate.mileage(sc.nextDouble());
                            break;
                        case 6: // vin
                            StdOut.print("Generating new random VIN: ");
                            carToUpdate.newVin();
                            break;
                        default:
                            StdOut.println("Invalid Entry. Not making change.\nReturning to main menu.");
                    }
                    break;
                case 3: // remove
                    sc = new Scanner(System.in);
                    StdOut.print("Enter VIN: ");
                    StringBuilder remVin = new StringBuilder();
                    remVin.append(sc.nextLine());
                    for (int k : cars.pqByPrice()) {
                        StringBuilder vin = cars.pqByPrice().keyOf(k).getVin();
                        //StdOut.println("Checking VIN: " + Arrays.toString(vin));
                        if (vin.equals(remVin)) {
                            StdOut.println("Removing the " + cars.pqByPrice().keyOf(k).getMake());
                            cars.pqByPrice().delete(k);
                            break;
                        }
                    }
                    break;
                case 4: // Retrieve Lowest Price
                    StdOut.println("The " + cars.pqByPrice().minKey().getMake() + " has the lowest price equal to $" + cars.pqByPrice().minKey().getPrice() + "\n");
                    break;
                case 5: // Retrieve Lowest Mileage
                    StdOut.println("The " + cars.pqByMileage().minKey().getMake() + " has the lowest mileage equal to " + cars.pqByMileage().minKey().getMileage() + " miles\n");
                    break;
            }
            
             StdOut.println("Queue by price:");
             for (int i : cars.pqByPrice()) {
             StdOut.println(i);
             StdOut.print("Make = " + (cars.pqByPrice().keyOf(i)).getMake());
             StdOut.println("\t\tModel = " + (cars.pqByPrice().minKey()).getModel());
             StdOut.print("Color = " + (cars.pqByPrice().minKey()).getColor());
             StdOut.println("\t\tPrice = " + (cars.pqByPrice().keyOf(i)).getPrice());
             StdOut.print("Mileage = " + (cars.pqByPrice().keyOf(i)).getMileage());
             StdOut.println("\t\tVIN = " + (cars.pqByPrice().keyOf(i)).getVin().toString());

             }
             StdOut.println("\nQueue by mileage:");
             for (int j : cars.pqByMileage()) {
             StdOut.println(j);
             StdOut.print("Make = " + (cars.pqByMileage().keyOf(j)).getMake());
             StdOut.println("\t\tModel = " + (cars.pqByMileage().keyOf(j)).getModel());
             StdOut.print("Color = " + (cars.pqByMileage().keyOf(j)).getColor());
             StdOut.println("\t\tPrice = " + (cars.pqByMileage().keyOf(j)).getPrice());
             StdOut.print("Mileage = " + (cars.pqByMileage().keyOf(j)).getMileage());
             StdOut.println("\t\tVIN = " + (cars.pqByMileage().keyOf(j)).getVin().toString());
             }
             
        } while (choice != 0);

    }
}
