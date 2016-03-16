public class CarSystem {

    IndexMinPQ pqByPrice, pqByMileage;

    public CarSystem(int maxN) {
        pqByPrice = new IndexMinPQ(maxN, "price");
        pqByMileage = new IndexMinPQ(maxN, "mileage");
    }

    public void insert(int i, Car car) {
        pqByPrice.insert(i, car);
        pqByMileage.insert(i, car);
    }

    public IndexMinPQ pqByPrice() {
        return this.pqByPrice;
    }

    public IndexMinPQ pqByMileage() {
        return this.pqByMileage;
    }

}
