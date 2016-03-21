
public class CarSystem {

    /* -------- Global Declarations*/
    IndexMinPQ pqByPrice, pqByMileage;
    int N;

    /* -------- Constructors*/
    public CarSystem(int maxN) {
        // Create 2 new Priority Queues. One by price and one by mileage
        pqByPrice = new IndexMinPQ(maxN, "price");
        pqByMileage = new IndexMinPQ(maxN, "mileage");
        
    }
    /**
     * Returns true if this car system is empty.
     *
     * @return true if this car system is empty;
     * false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * -------- Car System Functions
     *
     */
    
    /**
     * @param i the index
     * @param car the car
     */
    public void insert(int i, Car car) {
        pqByPrice.insert(i, car);
        pqByMileage.insert(i, car);
        N++;
    }

    /**
     *
     * @return the PQ indexed by price
     */
    public IndexMinPQ pqByPrice() {
        return this.pqByPrice;
    }

    /**
     *
     * @return the PQ indexed by mileage
     */
    public IndexMinPQ pqByMileage() {
        return this.pqByMileage;
    }

}
