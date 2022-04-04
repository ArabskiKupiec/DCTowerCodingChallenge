import java.util.List;

/**
 * A running elevator, can be either on the move up, down or staying in place, having a queue of floors to stop
 * at to its disposal, is able to conduct a ride and to perform an analysis how long is it going to take it to get ready
 * for a given ride.
 *
 * @author Jakub Poprawski
 */

public interface Elevator extends Runnable {

    /**
     * Orders this elevator to conduct a ride from a given floor to a provided destination floor.
     *
     * The ride stations are added to the queue.
     * If the elevator is starting to move in the moment, the start station is treated differently, as it always has to
     * be reached first, no matter from which direction for the algorithm to function.
     *
     * @param from the floor from which the ride is ordered
     * @param to the floor to which the ride is ordered
     *
     */
    void orderARideTo(int from, int to);

    /**
     * Performs an analysis how long is it going to take this elevator to get ready for a ride from a provided floor in the given direction.
     *
     * @param from the start floor of the considered new ride to reach
     * @param rideDirection the direction of the considered new ordered ride
     * @return the time that it will take this elevator to reach the start station in order to start a ride in a given direction
     *         or, distance in the floors that the elevator has to move through before reaching the from station).
     */
    int countDistance(int from, int rideDirection);

    /**
     * Runs our runnable elevator in a thread
     */
    void start();
}
