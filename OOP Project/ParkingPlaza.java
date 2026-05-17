// ENCAPSULATION: Private fields with public methods for controlled access
public class ParkingPlaza {
    private ParkingSlot[] slots;
    private int slotCount;
    private double totalRevenue;
    private String name;
    
    public ParkingPlaza(String name) {
        this.name = name;
        this.slots = new ParkingSlot[15];
        this.slotCount = 0;
        this.totalRevenue = 0.0;
    }
    
    public void initializeSlots() {
        for (int i = 1; i <= 9; i++) slots[slotCount++] = new CompactSlot("C" + i);
        for (int i = 1; i <= 4; i++) slots[slotCount++] = new LargeSlot("L" + i);
        for (int i = 1; i <= 2; i++) slots[slotCount++] = new EVChargingSlot("EV" + i);
    }
    
    public ParkingSlot findAvailableSlot() {
        for (int i = 0; i < slotCount; i++) {
            if (!slots[i].isOccupied) return slots[i];
        }
        return null;
    }
    
    public ParkingSlot findAvailableSlot(String vehicleType) {
        if (vehicleType == null || vehicleType.isEmpty()) {
            throw new IllegalArgumentException("Vehicle type cannot be null or empty");
        }
        for (int i = 0; i < slotCount; i++) {
            if (!slots[i].isOccupied && slots[i].getSlotType().equalsIgnoreCase(vehicleType)) {
                return slots[i];
            }
        }
        return null;
    }
    
    public ParkingSlot findSlotByID(String slotID) {
        if (slotID == null || slotID.isEmpty()) {
            throw new IllegalArgumentException("Slot ID cannot be null or empty");
        }
        for (int i = 0; i < slotCount; i++) {
            if (slots[i].slotID.equalsIgnoreCase(slotID)) {
                return slots[i];
            }
        }
        return null;
    }
    
    public ParkingSlot parkVehicle(String vehicleType) throws PlazaFullException {
        try {
            if (vehicleType == null || vehicleType.isEmpty()) {
                throw new IllegalArgumentException("Vehicle type cannot be null or empty");
            }
            ParkingSlot slot = findAvailableSlot(vehicleType);
            if (slot == null && vehicleType.equalsIgnoreCase("GENERIC")) {
                slot = findAvailableSlot();
            }
            if (slot == null) {
                throw new PlazaFullException("No available slots for vehicle type: " + vehicleType);
            }
            slot.isOccupied = true;
            slot.vehicleType = vehicleType;
            return slot;
        } catch (IllegalArgumentException e) {
            throw new PlazaFullException("Invalid vehicle type provided", e);
        }
    }
    
    // POLYMORPHISM: calculateTotalFee() - behavior depends on slot type
    public double exitVehicle(String slotID, int hoursParked) {
        try {
            if (slotID == null || slotID.isEmpty()) {
                throw new IllegalArgumentException("Slot ID cannot be null or empty");
            }
            if (hoursParked < 1 || hoursParked > 24) {
                throw new IllegalArgumentException("Hours parked must be between 1 and 24, received: " + hoursParked);
            }
            ParkingSlot slot = findSlotByID(slotID);
            if (slot == null) {
                throw new IllegalArgumentException("Slot not found: " + slotID);
            }
            if (!slot.isOccupied) {
                throw new IllegalArgumentException("Slot is already empty: " + slotID);
            }
            double fee = slot.calculateTotalFee(hoursParked);
            slot.isOccupied = false;
            slot.vehicleType = "";
            totalRevenue += fee;
            return fee;
        } catch (IllegalArgumentException e) {
            System.err.println("Error processing exit: " + e.getMessage());
            return 0.0;
        }
    }
    
    public int getAvailableSlotCount() {
        int count = 0;
        for (int i = 0; i < slotCount; i++) {
            if (!slots[i].isOccupied) count++;
        }
        return count;
    }
    
    public int getOccupiedSlotCount() {
        return slotCount - getAvailableSlotCount();
    }
    
    public int getTotalSlots() {
        return slotCount;
    }
    
    public double getTotalRevenue() {
        return totalRevenue;
    }
    
    public ParkingSlot[] getAllSlots() {
        ParkingSlot[] result = new ParkingSlot[slotCount];
        for (int i = 0; i < slotCount; i++) result[i] = slots[i];
        return result;
    }
}
