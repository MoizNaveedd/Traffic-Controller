
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    // Enum for traffic lights
    public enum TrafficLight {
        RED, YELLOW, GREEN
    }

    // Enum for directions
    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    // Constants for timer range
    public static final int MIN_GREEN_TIMER = 1;
    public static final int MAX_GREEN_TIMER = 30;

    // Map to associate each direction with traffic light status and timer
    private static final Direction[] ALL_DIRECTIONS = Direction.values();
    private static final Map<Direction, TrafficLightInfo> directionTrafficMap = new HashMap<>();

    // TrafficLightInfo class to store light status and timer
    public static class TrafficLightInfo {
        private TrafficLight light;
        private int timer;

        public TrafficLightInfo(TrafficLight light, int timer) {
            this.light = light;
            this.timer = timer;
        }

        public TrafficLight getLight() {
            return light;
        }

        public int getTimer() {
            return timer;
        }

        public void setLight(TrafficLight light) {
            this.light = light;
        }

        public void setTimer(int timer) {
            this.timer = timer;
        }
    }

    // Function to print the state of the system
    private static void printSystemState(String outputMessage) {
        System.out.println(outputMessage);
        for (Direction direction : Direction.values()) {
            TrafficLightInfo info = directionTrafficMap.getOrDefault(direction,
                    new TrafficLightInfo(TrafficLight.RED, 0));
            System.out.printf("%-6s %-10s %-5d%n", direction, info.getLight(), info.getTimer());
        }
    }


    public static void decrementTimers() {
        for (Direction direction : ALL_DIRECTIONS) {
            TrafficLightInfo info = directionTrafficMap.getOrDefault(direction,
                    new TrafficLightInfo(TrafficLight.RED, 0));
            int currentTimer = info.getTimer();
    
            if (info.getLight() == TrafficLight.RED && currentTimer == 0) {
                info.setLight(TrafficLight.YELLOW);
                info.setTimer(1);
            } else if (info.getLight() == TrafficLight.YELLOW && currentTimer == 0) {
                info.setLight(TrafficLight.GREEN);
                info.setTimer(27);
            } 
             else if (info.getLight() == TrafficLight.GREEN && currentTimer == 0) {
                info.setLight(TrafficLight.RED);
                info.setTimer(99);
            } else if (info.getLight() == TrafficLight.RED && currentTimer == 0) {
                info.setLight(TrafficLight.GREEN);
                info.setTimer(30);
            } else if (currentTimer > 0) {
                info.setTimer(currentTimer - 1);
            }
    
            directionTrafficMap.put(direction, info);
        }
        printSystemState("The updated State of the system is following");
    }
    
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
            printSystemState("The Initial State of the system is following");

            System.out.println("\nEnter new information:");

            Direction inputDirection;
            int inputTimer;

            // Loop until valid direction is entered
            while (true) {
                System.out.print("Enter direction (NORTH, EAST, SOUTH, WEST) to Set it to Green: ");
                String directionInput = scanner.nextLine().toUpperCase();
                try {
                    inputDirection = Direction.valueOf(directionInput);
                    break; // Exit the loop if the direction is valid
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid direction. Please enter a valid direction.");
                }  }
            // Loop until valid timer is entered
            while (true) {
                System.out.print("Enter timer value: ");
                try {
                    inputTimer = Integer.parseInt(scanner.nextLine());
                    if (inputTimer >= MIN_GREEN_TIMER && inputTimer <= MAX_GREEN_TIMER) {
                        break; // Exit the loop if the timer is valid
                    } else {
                        System.out.println(
                                "Timer value should be between " + MIN_GREEN_TIMER + " and " + MAX_GREEN_TIMER);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid timer format. Please enter a valid integer."); } }
            // Clear the map before updating it
            directionTrafficMap.clear();

            // Update the traffic light information based on user input
            TrafficLightInfo updatedInfo = new TrafficLightInfo(TrafficLight.GREEN, inputTimer);
            directionTrafficMap.put(inputDirection, updatedInfo);

            // Adjust timers for other directions
            int timerValue = inputTimer + 3;
            for (Direction direction : ALL_DIRECTIONS) {
                if (!direction.equals(inputDirection)) {
                    TrafficLightInfo info = directionTrafficMap.getOrDefault(direction,
                            new TrafficLightInfo(TrafficLight.RED, 0));
                    info.setLight(TrafficLight.RED); // Set the light to RED for other directions
                    info.setTimer(info.getTimer() + timerValue); // Increment the timer by the input timer value
                    directionTrafficMap.put(direction, info);
                    timerValue += 33; } }
            // Call the print function to display the updated state of the system
            printSystemState("The updated State of the system is following");
            // After displaying the updated state of the system
            // decrementTimers();
                      ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            // Schedule the decrementTimers task to run repeatedly with a delay of 1 second
            scheduler.scheduleAtFixedRate(App::decrementTimers, 0, 1, TimeUnit.SECONDS);

            // Close the scheduler when the program exits
            Runtime.getRuntime().addShutdownHook(new Thread(scheduler::shutdown));

            // Ask the user if they want to set another direction

        scanner.close();}} // Close the Scanner object when done 
