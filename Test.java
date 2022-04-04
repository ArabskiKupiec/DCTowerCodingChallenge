public class Test {
    public static void main(String[] args) {
        DCElevatorManagementSystem dcElevatorManagementSystem = new DCElevatorManagementSystem();
        dcElevatorManagementSystem.run();

        //TESTS:
        dcElevatorManagementSystem.addRequest(1, 20);
        try
        {
            Thread.sleep(500);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        try
        {
         dcElevatorManagementSystem.addRequest(-1, 20);
        } catch (IllegalArgumentException e)
        {
            System.out.println("Exception catched: " + e);
        }
        dcElevatorManagementSystem.addRequest(15, 5);
        dcElevatorManagementSystem.addRequest(7, 41);
        dcElevatorManagementSystem.addRequest(35, 45);

        try
        {
            dcElevatorManagementSystem.addRequest(34, 56);
        } catch (IllegalArgumentException e)
        {
            System.out.println("Exception catched: " + e);
        }

        try
        {
            Thread.sleep(300);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        dcElevatorManagementSystem.addRequest(6, 1);
        dcElevatorManagementSystem.addRequest(10, 15);


    }

}
