// ABSTRACTION: Abstract base class - subclasses must implement abstract methods
public abstract class ParkingSlot {
    public String slotID;
    public boolean isOccupied;
    public double baseRate;
    public String vehicleType = "";
    public int hoursParked = 0;
    
    public ParkingSlot(String slotID, double baseRate) {
        this.slotID = slotID;
        this.baseRate = baseRate;
    }
    
    // POLYMORPHISM: Subclasses override these methods
    public abstract double calculateTotalFee(int hours);
    public abstract String getSlotType();
}
