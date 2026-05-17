# OOP Concepts - Detailed Breakdown & Code Examples

## 1. INHERITANCE ✓

### Definition
Creating a class that inherits properties and methods from another class.

### Implementation in Our Project

**Base Class:**
```java
public abstract class ParkingSlot {
    protected String slotID;
    protected boolean isOccupied;
    protected double baseRate;
    // ... abstract methods
}
```

**Derived Classes:**
```java
public class CompactSlot extends ParkingSlot {
    public CompactSlot(String slotID) {
        super(slotID, 5.0); // Calls parent constructor
    }
}

public class LargeSlot extends ParkingSlot {
    public LargeSlot(String slotID) {
        super(slotID, 8.0);
    }
}

public class EVChargingSlot extends ParkingSlot {
    public EVChargingSlot(String slotID) {
        super(slotID, 7.0);
    }
}
```

**Key Points:**
- `extends` keyword establishes inheritance
- Subclasses inherit all protected/public members
- Subclasses can call parent methods via `super`
- Constructors use `super()` to initialize parent state

---

## 2. ABSTRACTION ✓

### Definition
Hiding implementation details and showing only essential features.

### Implementation in Our Project

**Abstract Base Class:**
```java
public abstract class ParkingSlot {
    // Concrete method - implementation provided
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
    
    // Abstract method - no implementation, must be overridden
    public abstract double calculateTotalFee(int hours);
    
    public abstract String getSlotType();
}
```

**Forcing Implementation:**
```java
// This MUST be implemented by subclass or it won't compile
public class CompactSlot extends ParkingSlot {
    @Override
    public double calculateTotalFee(int hours) {
        int chargeableHours = Math.max(hours, 2);
        return chargeableHours * baseRate;
    }
    
    @Override
    public String getSlotType() {
        return "COMPACT";
    }
}
```

**Benefits:**
- Cannot instantiate abstract class: `new ParkingSlot()` → ERROR
- Forces all subclasses to implement required methods
- Defines contract that all slots must follow
- Hides implementation complexity from user

---

## 3. POLYMORPHISM ✓

### Definition
Objects of different types responding to the same message (method call) in their own way.

### Implementation in Our Project

**Polymorphic Array:**
```java
ParkingSlot[] slots = new ParkingSlot[3];
slots[0] = new CompactSlot("C1");
slots[1] = new LargeSlot("L1");
slots[2] = new EVChargingSlot("EV1");
// All stored as ParkingSlot type, but maintain their own type
```

**Polymorphic Method Invocation:**
```java
// Key insight: Same method call, different behavior!
for (int i = 0; i < slots.length; i++) {
    ParkingSlot slot = slots[i];
    // At runtime, Java determines which calculateTotalFee() to call
    double fee = slot.calculateTotalFee(3); // ← Polymorphic call
}

// Output:
// CompactSlot.calculateTotalFee(3) → 15.0  (3 * 5.0)
// LargeSlot.calculateTotalFee(3)  → 33.0  ((3 * 8.0) + (3 * 3.0))
// EVChargingSlot.calculateTotalFee(3) → 26.0  ((3 * 7.0) + 5.0)
```

**How Java Determines Which Method to Call (Dynamic Dispatch):**
```
1. Compiler checks: Is calculateTotalFee valid for ParkingSlot? YES
2. Runtime checks: What's the ACTUAL type of this object?
3. Runtime calls: The method of the ACTUAL type
```

**Example in ParkingPlaza:**
```java
public double exitVehicle(String slotID, int hoursParked) {
    ParkingSlot slot = findSlotByID(slotID);
    
    // Polymorphic call - the actual subclass's method is called
    double fee = slot.calculateTotalFee(hoursParked);
    // No if-else needed! No casting needed! Just polymorphism!
    
    return fee;
}
```

---

## 4. ENCAPSULATION ✓

### Definition
Bundling data (attributes) and methods together, hiding internal details.

### Implementation in Our Project

**Private/Protected Attributes:**
```java
public abstract class ParkingSlot {
    protected String slotID;           // Only accessible by subclasses
    protected boolean isOccupied;
    protected double baseRate;
    // NOT public - controlled access
}
```

**Getter/Setter Methods:**
```java
public boolean isOccupied() {
    return isOccupied;
}

public void setOccupied(boolean occupied) {
    isOccupied = occupied;
    // Could add validation here
}

public String getSlotID() {
    return slotID;
}
```

**Benefits:**
- Protects data integrity
- Can add validation in setters
- Can change internal implementation without affecting external code

---

## 5. POLYMORPHISM & OVERRIDING ✓

### Definition
Subclass provides specific implementation of a method declared in parent class.

### Implementation

**Method Overriding:**
```java
// Parent class declares abstract method
public abstract class ParkingSlot {
    public abstract double calculateTotalFee(int hours);
}

// Each subclass overrides with its own logic
public class CompactSlot extends ParkingSlot {
    @Override  // Annotation (optional but recommended)
    public double calculateTotalFee(int hours) {
        int chargeableHours = Math.max(hours, 2);
        return chargeableHours * baseRate;
    }
}

public class LargeSlot extends ParkingSlot {
    @Override
    public double calculateTotalFee(int hours) {
        return (hours * baseRate) + (hours * oversizeCharge);
    }
}

public class EVChargingSlot extends ParkingSlot {
    @Override
    public double calculateTotalFee(int hours) {
        return (hours * baseRate) + chargingFee;
    }
}
```

**Call Site (No Knowledge of Subclass Types):**
```java
public double exitVehicle(String slotID, int hoursParked) {
    ParkingSlot slot = findSlotByID(slotID);
    
    // Works for ANY subclass without modification!
    double fee = slot.calculateTotalFee(hoursParked);
    
    return fee;
}
```

---

## 6. FUNCTION OVERLOADING ✓

### Definition
Multiple methods with same name but different parameters.

### Implementation in Our Project

**In ParkingPlaza class:**

```java
// Overload 1: No parameters
public ParkingSlot findAvailableSlot() {
    for (ParkingSlot slot : slots) {
        if (!slot.isOccupied()) {
            return slot;
        }
    }
    return null;
}

// Overload 2: String parameter (vehicle type)
public ParkingSlot findAvailableSlot(String vehicleType) {
    for (ParkingSlot slot : slots) {
        if (!slot.isOccupied() && slot.getSlotType().equalsIgnoreCase(vehicleType)) {
            return slot;
        }
    }
    return null;
}

// Overload 3: Different purpose (slot ID lookup)
public ParkingSlot findSlotByID(String slotID) {
    for (ParkingSlot slot : slots) {
        if (slot.getSlotID().equalsIgnoreCase(slotID)) {
            return slot;
        }
    }
    return null;
}
```

**Usage:**
```java
plaza.findAvailableSlot();           // Calls overload 1
plaza.findAvailableSlot("COMPACT");  // Calls overload 2
plaza.findSlotByID("C1");            // Calls overload 3
```

**How Java Determines Which to Call:**
```
Java matches based on:
1. Method name (must be identical)
2. Number of parameters
3. Parameter types
4. Parameter order

Called "Method Resolution" at compile time.
```

---

## 7. EXCEPTION HANDLING ✓

### Custom Exception

```java
public class PlazaFullException extends Exception {
    public PlazaFullException() {
        super("Parking Plaza is full! No available slots.");
    }
    
    public PlazaFullException(String message) {
        super(message);
    }
}
```

### Throwing Custom Exception

```java
public ParkingSlot parkVehicle(String vehicleType) throws PlazaFullException {
    ParkingSlot slot = findAvailableSlot(vehicleType);
    
    if (slot == null) {
        throw new PlazaFullException(
            "No available slots for vehicle type: " + vehicleType
        );
    }
    
    slot.setOccupied(true);
    return slot;
}
```

### Handling Exception

```java
try {
    ParkingSlot slot = plaza.parkVehicle("COMPACT");
} catch (PlazaFullException e) {
    JOptionPane.showMessageDialog(null, e.getMessage());
}
```

### Input Validation with Exceptions

```java
public double exitVehicle(String slotID, int hoursParked) {
    try {
        if (slotID == null || slotID.isEmpty()) {
            throw new IllegalArgumentException("Slot ID cannot be empty");
        }
        
        if (hoursParked <= 0) {
            throw new IllegalArgumentException("Hours must be greater than 0");
        }
        
        ParkingSlot slot = findSlotByID(slotID);
        if (slot == null) {
            throw new IllegalArgumentException("Slot not found: " + slotID);
        }
        
        // Process transaction
        double fee = slot.calculateTotalFee(hoursParked);
        slot.setOccupied(false);
        totalRevenue += fee;
        
        return fee;
        
    } catch (IllegalArgumentException e) {
        System.err.println("Error: " + e.getMessage());
        return 0.0;
    }
}
```

---

## 8. GUI COMPONENTS ✓

### Swing Components Used

```java
// Main frame
JFrame extends JFrame

// Panels
JPanel slotsPanel = new JPanel(new GridLayout(6, 3));

// Buttons (color-coded) - stored in array
JButton[] slotButtons = new JButton[15]; // Fixed array for 15 slots
JButton slotButton = new JButton();
slotButton.setBackground(AVAILABLE_COLOR); // Green
slotButton.setBackground(OCCUPIED_COLOR);  // Red

// Dialogs
JDialog parkingDialog = new JDialog(this, "Park Vehicle", true);
JDialog exitDialog = new JDialog(this, "Exit Vehicle", true);

// Input components
JComboBox<String> vehicleCombo = new JComboBox<>(types);
JSpinner hoursSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 24, 1));

// Labels for statistics
JLabel totalSlotsLabel = new JLabel("Total Slots: " + total);
JLabel revenueLabel = new JLabel("Revenue: $" + revenue);
```

### Event Handling

```java
slotButton.addActionListener(e -> {
    handleSlotClick(slot);
});

confirmButton.addActionListener(e -> {
    slot.setOccupied(true);
    refreshDisplay();
    dialog.dispose();
});
```

### Live Updates

```java
private void refreshDisplay() {
    // Update all button colors
    ParkingSlot[] allSlots = plaza.getAllSlots();
    for (int i = 0; i < allSlots.length && i < slotButtons.length; i++) {
        ParkingSlot slot = allSlots[i];
        if (slot != null && slotButtons[i] != null) {
            updateSlotButton(slotButtons[i], slot);
        }
    }
    
    // Update statistics
    updateStatistics();
    
    // Repaint
    slotsPanel.repaint();
}
```

---

## Summary: How All Concepts Work Together

```
┌─────────────────────────────────────────────────────────────┐
│                      Inheritance Hierarchy                    │
├─────────────────────────────────────────────────────────────┤
│
│  ParkingSlot (Abstract)
│      ↑
│      ├─ CompactSlot
│      ├─ LargeSlot
│      └─ EVChargingSlot
│
└─────────────────────────────────────────────────────────────┘

Polymorphic Array:
ParkingSlot[] contains all types

Overriding:
Each subclass overrides calculateTotalFee()

Overloading:
findAvailableSlot() with different parameters

Exception Handling:
PlazaFullException for error cases
Try-catch for robustness

GUI:
Interactive interface showing all this in action
```

---

## Final Code Example: Everything Together

```java
// 1. Create polymorphic array
ParkingSlot[] slots = new ParkingSlot[3];
slots[0] = new CompactSlot("C1");
slots[1] = new LargeSlot("L1");
slots[2] = new EVChargingSlot("EV1");

// 2. Use overloaded method
ParkingSlot slot = plaza.findAvailableSlot("COMPACT"); // Overload 2

// 3. Handle potential exceptions
try {
    if (slot == null) {
        throw new PlazaFullException("No compact slots available");
    }
    
    // 4. Polymorphic method call
    double fee = slot.calculateTotalFee(2);
    
    // 5. Encapsulation - use public field or setter
    slot.isOccupied = true;
    
    // 6. GUI updates
    refreshDisplay();
    
} catch (PlazaFullException e) {
    // 7. Error handling
    JOptionPane.showMessageDialog(null, e.getMessage());
}
```

This single code snippet demonstrates:
✓ Inheritance (slots are ParkingSlot types)
✓ Abstraction (using abstract methods)
✓ Polymorphism (calculateTotalFee called polymorphically)
✓ Overloading (findAvailableSlot with parameter)
✓ Encapsulation (controlled access to slot state)
✓ Exception Handling (try-catch with custom exception)
✓ Arrays (Fixed-size array collections)
✓ GUI (refreshDisplay updates UI)

