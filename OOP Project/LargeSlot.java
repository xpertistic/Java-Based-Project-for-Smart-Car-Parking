// INHERITANCE: Extends ParkingSlot abstract class
// POLYMORPHISM: Overrides abstract methods
public class LargeSlot extends ParkingSlot {
    private static final String SLOT_TYPE = "LARGE";
    
    public LargeSlot(String slotID) {
        super(slotID, 10.0);
    }
    
    @Override
    public double calculateTotalFee(int hours) {
        if (hours < 1 || hours > 24) throw new IllegalArgumentException("Hours must be between 1 and 24, received: " + hours);
        return hours * baseRate;
    }
    
    @Override
    public String getSlotType() {
        return SLOT_TYPE;
    }
}
