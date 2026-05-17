import javax.swing.SwingUtilities;

/**
 * APPLICATION ENTRY POINT: Main class that starts the parking lot management system
 * 
 * Execution Flow:
 * 1. Creates ParkingPlaza instance with name "Downtown Parking Plaza"
 * 2. Initializes 15 parking slots (9 compact, 4 large, 2 EV charging)
 * 3. Launches GUI in Event Dispatch Thread (Swing best practice)
 * 4. Displays initial system information to console
 * 
 * OOP Concepts Used: All 5 core concepts are demonstrated throughout the system
 */
public class ParkingLotMain {
    /**
     * Main method - Application entry point
     * Creates plaza, initializes slots, launches GUI and displays info
     * @param args - Command line arguments (not used)
     */
    public static void main(String[] args) {
        // 1. Create parking plaza instance with name
        ParkingPlaza plaza = new ParkingPlaza("Downtown Parking Plaza");
        
        // 2. Initialize all 15 parking slots
        plaza.initializeSlots();
        
        // 3. Launch GUI on Event Dispatch Thread (Swing thread-safe requirement)
        // Uses lambda expression to create GUI after thread setup
        SwingUtilities.invokeLater(() -> new ParkingGUI(plaza));
        
        // 4. Display system information to console
        System.out.println("\n===== PARKING LOT MANAGEMENT SYSTEM =====\n");
        demonstratePolymorphism(plaza);
    }
    
    /**
     * Displays parking system information: pricing and initial statistics
     * Demonstrates console output and data retrieval from plaza manager
     * @param plaza - ParkingPlaza instance with initialized slots
     */
    private static void demonstratePolymorphism(ParkingPlaza plaza) {
        System.out.println("PRICING STRUCTURE:");
        System.out.println("  Compact: $5/hour");
        System.out.println("  Large: $10/hour");
        System.out.println("  EV Charging: $8/hour\n");
        
        // Display initial slot statistics
        System.out.println("TOTAL SLOTS: " + plaza.getTotalSlots());
        System.out.println("AVAILABLE: " + plaza.getAvailableSlotCount());
        System.out.println("OCCUPIED: " + plaza.getOccupiedSlotCount());
        System.out.println("REVENUE: $" + String.format("%.2f", plaza.getTotalRevenue()));
        System.out.println("\nUse the GUI to park and exit vehicles.\n");
    }
}
