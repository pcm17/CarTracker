
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The <tt>IndexMinPQ</tt> class represents an indexed priority queue of cars.
 * It supports  <em>insert</em>,  <em>delMin</em> and <em>delete</em>. In order to
 * let the client refer to cars on the priority queue, an integer between 0 and
 * maxN-1 is associated with each car;the client uses this integer to
 * specify which car to delete. It also supports methods for peeking at the
 * minimum car by price or by mileage, testing if the priority queue is empty, and iterating through
 * the cars.
 * <p>
 * This implementation uses a binary heap along with an array to associate cars
 * with integers in the given range. The <em>insert</em>, <em>delMin</em> and
 * <em>delete</em> operations take logarithmic time. The <em>is-empty</em>, <em>size</em>,
 * <em>min-index</em>, <em>min-key</em>, and <em>key-of</em>
 * operations take constant time. Construction takes time proportional to the
 * specified capacity.
 */
public class IndexMinPQ implements Iterable<Integer> {

    private int maxN;        // maximum number of elements on PQ
    private int N;           // number of elements on PQ
    private int[] pq;        // binary heap using 1-based indexing
    private int[] qp;        // inverse of pqPr - qpPr[pqPr[i]] = pqPr[qpPr[i]] = i
    private Car[] cars;      // carsByPrice[i] = priority of i
    private String priority;          // boolean to know what the priority is

    /**
     * Initializes an empty indexed priority queue with indices between
     * <tt>0</tt> and <tt>maxN - 1</tt>.
     *
     * @param maxN the cars on this priority queue are index from <tt>0</tt>
     * <tt>maxN - 1</tt>
     * @param priority
     * @throws IllegalArgumentException if <tt>maxN</tt> &lt; <tt>0</tt>
     */
    public IndexMinPQ(int maxN, String priority) {
        if (maxN < 0) {
            throw new IllegalArgumentException();
        }
        this.maxN = maxN;
        cars = new Car[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++) {
            qp[i] = -1;
        }
        if (!(priority.equalsIgnoreCase("price") || priority.equalsIgnoreCase("mileage"))) {
            StdOut.println("Invalid entry for priority.\nExiting Program.");
            System.exit(0);
        }
        this.priority = priority;
    }

    IndexMinPQ() {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return <tt>true</tt> if this priority queue is empty;
     * <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Is <tt>i</tt> an index on this priority queue?
     *
     * @param i an index
     * @return <tt>true</tt> if <tt>i</tt> is an index on this priority queue;
     * <tt>false</tt> otherwise
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt;
     * <tt>maxN</tt>
     */
    public boolean contains(int i) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }
        return qp[i] != -1;
    }

    /**
     * Returns the number of cars on this priority queue.
     *
     * @return the number of cars on this priority queue
     */
    public int size() {
        return N;
    }

    /**
     * Associates car with index <tt>i</tt>.
     *
     * @param i an index
     * @param car the car to associate with index <tt>i</tt>
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt;
     * <tt>maxN</tt>
     * @throws IllegalArgumentException if there already is an item associated
     * with index <tt>i</tt>
     */
    public void insert(int i, Car car) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }
        if (contains(i)) {
            throw new IllegalArgumentException("index is already in the priority queue");
        }
        N++;
        qp[i] = N;
        pq[N] = i;

        cars[i] = car;
        swim(N);
    }

    /**
     * Returns an index associated with a minimum car.
     *
     * @return an index associated with a minimum car
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int minIndex() {
        if (N == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return pq[1];
    }

    /**
     * Returns a minimum car.
     *
     * @return a minimum car
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Car minKey() {
        if (N == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return cars[pq[1]];
    }

    /**
     * Returns the car associated with index <tt>i</tt>.
     *
     * @param i the index of the car to return
     * @return the car associated with index <tt>i</tt>
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt;
     * <tt>maxN</tt>
     * @throws NoSuchElementException no car is associated with index <tt>i</tt>
     */
    public Car keyOf(int i) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        } else {
            return cars[i];
        }
    }

    /**
     * Remove the car associated with index <tt>i</tt>.
     *
     * @param i the index of the car to remove
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt;
     * <tt>maxN</tt>
     * @throws NoSuchElementException no car is associated with index <t>i</tt>
     */
    public void delete(int i) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("Index " + i + " is not in the priority queue");
        }
        int index = qp[i];
        exch(index, N--);
        swim(index);
        sink(index);
        cars[i] = null;
        qp[i] = -1;
    }

    /**
     * Removes a minimum car and returns its associated index.
     *
     * @return an index associated with a minimum car
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int delMin() {
        if (N == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        int min = pq[1];
        exch(1, N--);
        sink(1);
        assert min == pq[N + 1];
        qp[min] = -1;        // delete
        cars[min] = null;    // to help with garbage collection
        pq[N + 1] = -1;        // not needed
        return min;
    }

    /**
     * *************************************************************************
     * General helper functions.
     * *************************************************************************
     */
    private boolean greater(int i, int j) {
        if (priority.equalsIgnoreCase("price")) { // price
            return (cars[pq[i]]).getPrice() > (cars[pq[j]]).getPrice();
        } else { // mileage
            return (cars[pq[i]]).getMileage() > (cars[pq[j]]).getMileage();
        }
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    /**
     * *************************************************************************
     * Heap helper functions.
     * *************************************************************************
     */
    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    /**
     * *************************************************************************
     * Iterators.
     * *************************************************************************
     */
    /**
     * Returns an iterator that iterates over the car on the priority queue in
     * ascending order.
     *
     * @return an iterator that iterates over the keys in ascending order
     */
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {

        // create a new pq
        private IndexMinPQ copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no car move
        public HeapIterator() {
            if (priority.equalsIgnoreCase("price")) {
                copy = new IndexMinPQ(pq.length - 1, "price");
                for (int i = 1; i <= N; i++) {
                    copy.insert(pq[i], cars[pq[i]]);
                }
            } else {
                copy = new IndexMinPQ(pq.length - 1, "mileage");
                for (int i = 1; i <= N; i++) {
                    copy.insert(pq[i], cars[pq[i]]);
                }
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delMin();
        }
    }

}
