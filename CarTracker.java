import java.util.InputMismatchException;
import java.util.Scanner;

public class CarTracker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IndexMinPQ pq;
        int carNum = 0, choice = 10;
        String make, model;
        boolean valid;
        CarSystem cars = new CarSystem(50);

        do {
            do {
                StdOut.print("\n(1) Add a Car\t(5) Retrieve Lowest Mileage\n(2) Update Car\t(6) Retrieve Lowest Price by Make and Model\n(3) Remove Car\t(7) Retrieve Lowest Mileage by Make and Model\n(4) Retrieve Lowest Price");
                StdOut.print("\t(0) to Exit.\nChoice: ");
                try {
                    valid = true;
                    choice = sc.nextInt();
                } catch (InputMismatchException ime) {
                    StdOut.println("Invalid Input. ");
                    choice = 0;
                    valid = false;
                    sc.next();
                }
                if (choice > 7 || choice < 0) {
                    StdOut.println("Invalid Input. ");
                    valid = false;
                }
            } while (!valid);
            switch (choice) {
                case 0:
                    break;
                case 1: // Add a car
                    Car car = new Car();
                    boolean invalid;
                    do {
                        invalid = false;
                        try {
                            sc = new Scanner(System.in);
                            System.out.print("Make: ");
                            car.make(sc.next());

                            System.out.print("Model: ");
                            //car.model(car.getMake() + " model");
                            car.model(sc.next());

                            System.out.print("Color: ");
                            //car.color(car.getMake() + " color");
                            car.color(sc.next());

                            System.out.print("Price: ");
                            car.price(sc.nextDouble());

                            System.out.print("Mileage: ");
                            car.mileage(sc.nextDouble());
                        } catch (InputMismatchException ime) {
                            invalid = true;
                            StdOut.println("Invalid input. Try again from the beginning.");
                        }
                    } while (invalid);
                    car.newVin();
                    System.out.println("VIN: " + car.getVin().toString());

                    cars.insert(carNum, car); // Add car to priority Queue by price
                    carNum++; // Increment carNum to reflect adding a car
                    break;
                case 2: // Update Car
                    if (cars.isEmpty()) {
                        StdOut.println("No cars in the system.\nPlease add a car by entering 1 for choice ");
                    } else {
                        Car carToUpdate = null;
                        StringBuilder userVin = new StringBuilder();
                        StdOut.print("Enter VIN: ");
                        sc = new Scanner(System.in);
                        userVin.append(sc.nextLine());
                        for (int k : cars.pqByPrice()) {
                            StringBuilder vin = cars.pqByPrice().keyOf(k).getVin();
                            if (vin.toString().equals(userVin.toString())) {
                                carToUpdate = cars.pqByPrice().keyOf(k);
                                StdOut.println("Updating the " + carToUpdate.getColor() + " " + carToUpdate.getMake() + " " + carToUpdate.getModel());
                                break;
                            }
                        }
                        if (carToUpdate == null) {
                            StdOut.println("Did not find a car with VIN: " + userVin);
                            break; // Break the loop and go back to start menu
                        }
                        int field = 0;
                        do {
                            valid = true;
                            StdOut.print("\nWhat field would you like to update?\n(1) Make\t(4) Price\n(2) Model\t(5) Mileage\n(3) Color\t(6) VIN\nField: ");
                            try {
                                field = sc.nextInt();
                            } catch (InputMismatchException ime) {
                                StdOut.println("Invalid Input. ");
                                valid = false;
                                field = 0;
                                sc.next();
                            }
                            if ((field > 6 || field < 1) && valid) {
                                StdOut.println("Invalid Input. ");
                                valid = false;
                            }
                        } while (!valid);
                        do {
                            valid = true;
                            try {
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
                                        carToUpdate.newVin();
                                        StdOut.println("Generating new random VIN: " + carToUpdate.getVin());
                                        break;
                                    default:

                                }
                            } catch (InputMismatchException ime) {
                                StdOut.println("Invalid input. Try again.");
                                valid = false;
                                sc.next();
                            }
                        } while (!valid);
                    }
                    break;
                case 3: // remove
                    if (cars.isEmpty()) {
                        StdOut.println("No cars in the system.\nPlease add a car by entering 1 for choice ");
                    } else {
                        sc = new Scanner(System.in);
                        boolean found = false;
                        StdOut.print("Enter VIN: ");
                        StringBuilder remVin = new StringBuilder();
                        remVin.append(sc.nextLine());
                        for (int k : cars.pqByPrice()) {
                            StringBuilder vin = cars.pqByPrice().keyOf(k).getVin();
                            if (vin.equals(remVin)) {
                                StdOut.println("Removing the " + cars.pqByPrice().keyOf(k).getMake());
                                cars.pqByPrice().delete(k);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            StdOut.println("Did not find a car with VIN: " + remVin);
                        }
                    }
                    break;
                case 4: // Retrieve Lowest Price
                    if (cars.isEmpty()) {
                        StdOut.println("No cars in the system.\nPlease add a car by entering 1 for choice ");
                    } else {
                        StdOut.println("The " + cars.pqByPrice().minKey().getColor() + " " + cars.pqByPrice().minKey().getMake() + " " + cars.pqByPrice().minKey().getModel() + " has the lowest price equal to $" + cars.pqByPrice().minKey().getPrice() + "\n");

                    }
                    break;
                case 5: // Retrieve Lowest Mileage
                    if (cars.isEmpty()) {
                        StdOut.println("No cars in the system.\nPlease add a car by entering 1 for choice ");
                    } else {
                        StdOut.println("The " + cars.pqByMileage().minKey().getColor() + " " + cars.pqByMileage().minKey().getMake() + " " + cars.pqByMileage().minKey().getModel() + " has the lowest mileage equal to " + cars.pqByMileage().minKey().getMileage() + " miles\n");
                    }
                    break;
                case 6: // Retrieve Lowest Price by Make and Model
                    if (cars.isEmpty()) {
                        StdOut.println("No cars in the system.\nPlease add a car by entering 1 for choice ");
                    } else {
                        boolean foundPr = false;
                        sc = new Scanner(System.in);
                        StdOut.print("Enter Make: ");
                        make = sc.nextLine();
                        StdOut.print("Enter Model: ");
                        model = sc.nextLine();
                        pq = cars.pqByPrice();
                        for (int i : pq) {
                            if (pq.keyOf(i).getMake().equalsIgnoreCase(make) && pq.keyOf(i).getModel().equalsIgnoreCase(model)) {
                                StdOut.println("The lowest price " + pq.keyOf(i).getMake() + " " + pq.keyOf(i).getModel() + " is the " + pq.keyOf(i).getColor().toLowerCase() + " one that costs $" + pq.keyOf(i).getPrice());
                                foundPr = true;
                                break;
                            }
                        }
                        if (!foundPr) {
                            StdOut.println("There are no " + make + " " + model + "s in the system.");
                        }
                    }
                    break;
                case 7: // Retrieve Lowest Mileage by Make and Model

                    if (cars.isEmpty()) {
                        StdOut.println("No cars in the system.\nPlease add a car by entering 1 for choice ");
                    } else {
                        boolean foundMi = false;
                        sc = new Scanner(System.in);
                        StdOut.print("Enter Make: ");
                        make = sc.nextLine();
                        StdOut.print("Enter Model: ");
                        model = sc.nextLine();
                        pq = cars.pqByMileage();

                        for (int i : pq) {
                            if (pq.keyOf(i).getMake().equalsIgnoreCase(make) && pq.keyOf(i).getModel().equalsIgnoreCase(model)) {
                                StdOut.println("The lowest mileage " + pq.keyOf(i).getMake() + " " + pq.keyOf(i).getModel() + " is the " + pq.keyOf(i).getColor().toLowerCase() + " one, with " + pq.keyOf(i).getMileage() + " miles.");
                                foundMi = true;
                                break;
                            }
                        }
                        if (!foundMi) {
                            StdOut.println("There are no " + make + " " + model + "s in the system.");
                        }
                    }
                    break;
            }
        } while (choice != 0);
        /*
         StdOut.println("Queue by price:");
         for (int i : cars.pqByPrice()) {
         StdOut.print(i);
         StdOut.print(" Make = " + (cars.pqByPrice().keyOf(i)).getMake());
         StdOut.print("\tModel = " + (cars.pqByPrice().keyOf(i)).getModel());
         StdOut.print("\tColor = " + (cars.pqByPrice().keyOf(i)).getColor());
         StdOut.print("\tPrice = " + (cars.pqByPrice().keyOf(i)).getPrice());
         StdOut.print("\tMileage = " + (cars.pqByPrice().keyOf(i)).getMileage());
         StdOut.println("\tVIN = " + (cars.pqByPrice().keyOf(i)).getVin().toString());

         }
         StdOut.println("\nQueue by mileage:");
         for (int j : cars.pqByMileage()) {
         StdOut.print(j);
         StdOut.print(" Make = " + (cars.pqByMileage().keyOf(j)).getMake());
         StdOut.print("\tModel = " + (cars.pqByMileage().keyOf(j)).getModel());
         StdOut.print("\tColor = " + (cars.pqByMileage().keyOf(j)).getColor());
         StdOut.print("\tPrice = " + (cars.pqByMileage().keyOf(j)).getPrice());
         StdOut.print("\tMileage = " + (cars.pqByMileage().keyOf(j)).getMileage());
         StdOut.println("\tVIN = " + (cars.pqByMileage().keyOf(j)).getVin().toString());
         }
         */
    }
}
