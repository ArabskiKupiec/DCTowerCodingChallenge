import java.util.PriorityQueue;

//a running elevator, can be either on the move up, down or stay, having a queue of floors to stop at to its disposal
public class DCElevator extends Thread implements Elevator {

    final private int UP = 1;
    final private int DOWN = 2;
    final private int STILL = 0;
    final private int EMPTY_VALUE = -1000;
    private int currentFloor = 0;
    private int finalGoal = EMPTY_VALUE;
    // 0 = not moving, 1 = up, 2 = down;
    private int direction = 0;
    private int currentGoal = 0;
    private int fromToReach = EMPTY_VALUE;

    // if the elevator is going up all the entries in the priority queue are positive, if it goes down negative, thus
    // making the elevator always pick the consecutive floor to stop at.
    private PriorityQueue<Integer> floorsToVisit;

    public DCElevator()
    {
        floorsToVisit = new PriorityQueue<Integer>();
    }

    // The ride stations are added to the queue or, if the elevator is starting to move in the moment, the start station
    // is treated differently, as it always has to be reached first, no matter, from which direction for the algorithm to function
    public void orderARideTo(int from, int to)
    {
        if(finalGoal == EMPTY_VALUE) // the elevator doesn't have a set goal
        {
            finalGoal = to; // so we set it
            if(fromToReach == EMPTY_VALUE && direction == STILL)
            {
                fromToReach = from; // and the "from" start destination that must always be reached first
            }
            else
            {
                floorsToVisit.add(from);
            }

            if(to > fromToReach) // setting the direction of the ride AFTER reaching the first selected station ("from")
            {
                direction = UP;
                floorsToVisit.add(to);
            }
            else
            {
                direction = DOWN;
                floorsToVisit.add(to * -1);
            }
        }
        else //the elevator still has a goal to reach - its on the run
        {
            if(direction == UP) // if it's going up
            {
                if(finalGoal < to) {
                    finalGoal = to;
                }
                floorsToVisit.add(to);
            }
            else
                if(direction == DOWN) // if it's going down we have to additionally multiply the floor number by -1
                {
                    if(finalGoal > to) {
                        finalGoal = to;
                    }
                    floorsToVisit.add(to * -1);
                }
        }

    }


    public int countDistance(int from, int rideDirection)
    {
        // CASE 0: elevator in a stillstand
        if(direction == STILL)
        {
            return Math.abs(from - currentFloor);
        }
        // CASE 1: elevator moving in a opposite direction than the one of the ride to be ordered, i.e. eventually going to:
        // a) move away from the "from" next ride start station in the direction opposite to the one of the ride, or
        // b) already moving away (in such a case its ride direction doesn't matter)
        if ((direction == UP && (from < currentFloor || (rideDirection == DOWN && from < finalGoal))) ||
                (direction == DOWN && (from > currentFloor || (rideDirection == UP && from > finalGoal))))
        {
            return Math.abs(from - currentFloor) + 2 * Math.abs(finalGoal - currentFloor);
        }
        //CASE 2: elevator already having the same ride direction as the one of the potentially ordered ride,
        // i.e. eventually going to move (or already moving) towards the "from" ride start station
        // (opposite of 1 if no stillstand occurs)
        else
        {
            return Math.abs(from - currentFloor);
        }
    }

    private void print()
    {
        System.out.println("Elevator No." + this.hashCode() + " currently at floor " + this.currentFloor + " moving towards " +
                this.currentGoal + " further to " + this.finalGoal); //+ " in the direction No." + direction + "\n");
    }

    //the elevator is running, constantly (every X ms) checking whether there aren't any movements to do
    @Override
    public void run() {
        while (true) {
            try {
                if (!floorsToVisit.isEmpty()) { // if there are any floors to be visited in the future
                    if(fromToReach != EMPTY_VALUE) // if the elevator still has to reach the first selected "from" start floor
                    {
                        currentGoal = fromToReach;
                        if(fromToReach > currentFloor) // if it's riding up
                            while (currentFloor != fromToReach) {
                                currentFloor++;
                                print();
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }
                        if(fromToReach < currentFloor) // if it's riding down
                            while (currentFloor != fromToReach){
                                currentFloor--;
                                print();
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }
                        fromToReach = EMPTY_VALUE; // the floor where the ride starts has been reached
                    }
                    if (direction == UP) // ride up
                    {
                        while (currentFloor != finalGoal) {
                            if(currentFloor == currentGoal && !floorsToVisit.isEmpty()) // update the currentGoal if it has been reached already
                            {
                                currentGoal = floorsToVisit.poll();
                            }
                            currentFloor++;
                            print();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                    } else { // ride down
                        while (currentFloor != finalGoal) {
                            if(currentFloor == currentGoal && !floorsToVisit.isEmpty()) // update the currentGoal if it has been reached already
                            {
                                currentGoal = floorsToVisit.poll() * -1;
                            }
                            currentFloor--;
                            print();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                    }
                }
                direction = 0; // the ride has ended, since the final destination has been reached
                Thread.sleep(50);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
