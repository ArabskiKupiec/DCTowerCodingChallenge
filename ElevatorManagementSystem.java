/**
 * An elevator management system, it manages the elevators in a optimal way, so that many rides can by performed
 * subsequently. Once running, it is accepting incoming request for rides.
 *
 * @author Jakub Poprawski
 */


public interface ElevatorManagementSystem extends Runnable {

    /**
     * A request is passed to the system to provide an elevator which then conducts a ride on a specified route.
     *
     * @param from the floor from which the ride is ordered
     * @param to the floor to which the ride is ordered
     * @throws IllegalArgumentException is thrown if the ride parameters are invalid
     */
    void addRequest(int from, int to) throws IllegalArgumentException;
}
