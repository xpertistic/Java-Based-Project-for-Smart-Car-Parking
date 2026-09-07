
# 🚗 Smart Parking Lot Management System

A desktop application built in Java Swing that demonstrates core Object-Oriented Programming (OOP) concepts while providing an interactive parking space management system.

---

## 📌 Features

- **Interactive GUI Dashboard:** Real-time visual monitoring of 15 parking slots with color-coded status indicators (Green for Available, Red for Occupied).
- **Multiple Vehicle & Slot Types:** Specialized slots for `COMPACT`, `LARGE`, and `EV` (Electric Vehicle) parking.
- **Dynamic Fee Calculation:** Automated pricing based on slot type and duration (1 to 24 hours).
- **Slot Validation:** Prevents improper vehicle-to-slot type assignments.
- **Live Business Analytics:** Real-time metrics for total slots, occupied count, available count, and accumulated revenue.
- **Robust Input Validation & Custom Exceptions:** Prevents invalid operational entries and handles edge cases gracefully.

---

## 🏗️ Core OOP Concepts Applied

| Concept | Implementation Details |
| :--- | :--- |
| **Abstraction** | `ParkingSlot` is an `abstract` base class defining essential methods (`calculateTotalFee`, `getSlotType`) without enforcing a specific implementation. |
| **Inheritance** | `CompactSlot`, `LargeSlot`, and `EVChargingSlot` extend `ParkingSlot`, inheriting base fields while specifying rates and behaviors. |
| **Polymorphism** | Dynamic method dispatch enables fee calculation (`slot.calculateTotalFee(hours)`) to automatically adjust depending on the actual instance type. |
| **Encapsulation** | `ParkingPlaza` restricts direct state modification, granting access through getter methods and controlled state changes. |
| **Method Overloading** | Multiple overloaded signatures for `findAvailableSlot()` offer search functionality by general availability or explicit vehicle type. |
| **Exception Handling** | Custom checked exception `PlazaFullException` handles capacity issues alongside `try-catch` blocks for robust execution. |

---

## 📁 Repository Structure

---

## 💳 Pricing Structure

| Slot Type | Rate | Description |
| :--- | :--- | :--- |
| **Compact (`C1`–`C9`)** | `$5.00 / hr` | Designed for standard compact cars |
| **EV Charging (`EV1`–`EV2`)** | `$8.00 / hr` | Outfitted with electric vehicle charging capabilities |
| **Large (`L1`–`L4`)** | `$10.00 / hr` | Designed for oversized vehicles / SUVs |

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK):** Version 8 or higher installed on your system.

### Compilation & Execution

1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/your-username/parking-lot-management.git](https://github.com/your-username/parking-lot-management.git)
   cd parking-lot-management

```

2. **Compile the Java Files:**
```bash
javac *.java

```


3. **Run the Application:**
```bash
java ParkingLotMain

```



---

## 🖥️ How to Use the GUI

1. **Park a Vehicle:**
* Click on any **Green (Available)** slot button.
* Select the **Vehicle Type** matching the slot requirement.
* Enter the desired **Hours to Park** (1–24 hours).
* Click **Park Vehicle**. The slot status turns **Red (Occupied)**.


2. **Exit & Process Payment:**
* Click on any **Red (Occupied)** slot button.
* Review the calculated breakdown and total fee due.
* Click **Exit & Pay**. The slot resets to **Green (Available)**, and total revenue updates.



---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the issues page or submit a pull request.
Thank You 😊
