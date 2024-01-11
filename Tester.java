public class Tester {
    public static void main(String[] args) {
        testSingleDirection(); // Test setting a single direction to GREEN
        testInvalidDirection(); // Test entering an invalid direction
        testInvalidTimer(); // Test entering an invalid timer value
        testMultipleDirections(); // Test setting multiple directions
    }
    private static void testSingleDirection() {
        System.out.println("\nTest 1: Setting a single direction to GREEN");
        App.main(new String[]{"NORTH", "5"});
    }
    private static void testInvalidDirection() {
        System.out.println("\nTest 2: Entering an invalid direction");
        App.main(new String[]{"INVALID_DIRECTION", "10"});
    }
    private static void testInvalidTimer() {
        System.out.println("\nTest 3: Entering an invalid timer value");
        App.main(new String[]{"SOUTH", "40"});
    }
    private static void testMultipleDirections() {
        System.out.println("\nTest 4: Setting multiple directions");
        App.main(new String[]{"EAST", "15", "yes", "WEST", "7", "no"});
    }
}
