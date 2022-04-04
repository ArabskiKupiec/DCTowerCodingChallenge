
import java.lang.*;
public class DCElevatorManagementSystem implements ElevatorManagementSystem {

    final private int UP = 1;
    final private int DOWN = 2;
    final private int STILL = 0;
    final private int TOO_BIG_VALUE = 1000;
    private DCElevator[] elevators;

    public DCElevatorManagementSystem()
    {
        elevators = new DCElevator[7];
        for (int i = 0; i < 7; i++) {
            elevators[i] = new DCElevator();
        }
    }

    public synchronized void addRequest(int from, int to) throws IllegalArgumentException
    {
        DCElevator chosen;
        if(from > 54 || from < 0 || to == from || to > 55 || to < 0)
        {
            throw new IllegalArgumentException("invalid ride parameters provided, please try again with proper ones");
        }
        chosen = findASuitableElevator(from, to);
        chosen.orderARideTo(from, to);
    }

    /**
     * Fetches the elevator whose position and movement direction make it a suitable candidate for the provided ride-job
     *  (kinda greedy approach - the lift that can pick the ride-job fastest always gets it - what may be suboptimal in the
     *  longer run, when all the elevators are operating in the same time.
     *
     * @param from the station where the ride is supposed to start
     * @param to the station where the ride is supposed to end
     * @return elevator that was found suitable for a given ride-job
     **/
    private synchronized DCElevator findASuitableElevator(int from, int to)
    {
        int minimalDistance = TOO_BIG_VALUE;
        int currentDistance = TOO_BIG_VALUE;
        int rideDirection;
        int chosenElevatorNumber = 0;

        if(from < to) // is the ride up or down?
        {
            rideDirection = UP;
        }
        else {
                rideDirection = DOWN;
        }

        // find the most suitable elevator for the ride-job
        for (int i = 0; i < 7; i++) {
            currentDistance = elevators[i].countDistance(from, rideDirection);

            //If it's smaller, swap the old minimal distance value with the new one, and change the chosen elevator number entry
            if(currentDistance < minimalDistance)
            {
                minimalDistance = currentDistance;
                chosenElevatorNumber = i;
            }
        }
        return elevators[chosenElevatorNumber];
    }

    // the elevators management system is running
    @Override
    public synchronized void run() {
        for (int i = 0; i < 7; i++) {
            elevators[i].start();
        }
    }
}
