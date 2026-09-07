
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

## 🖼️GUI Images

this image features an interactive 15-slot grid—categorized into Compact, Large, and EV spaces—that uses color-coded status indicators to manage vehicle parking and exits. A real-time sidebar dynamically tracks occupancy metrics and total accumulated revenue as vehicles are parked or retrieved.
<img width="1032" height="655" alt="Screenshot 2026-09-07 215528" src="https://github.com/user-attachments/assets/dda97b77-50ba-4efb-bc03-00117dbadf67" />

The modal dialog Park Vehicle in Slot C1 appears after a user clicks on an available parking slot button. It displays the slot details (Slot ID: C1, Slot Type: COMPACT, Base Rate: $5.00/hour) and provides input controls for the user to select the vehicle type and set the parking duration between 1 and 24 hours.
<img width="1033" height="652" alt="Screenshot 2026-09-07 215700" src="https://github.com/user-attachments/assets/298e8b12-3666-4b5b-bfd4-9d855dae9c2f" />

An Invalid Input error popup appears when the user enters 0 hours into the parking duration field. The system blocks the invalid entry with the message "Error: Value not within min/max range", ensuring only valid parking durations between 1 and 24 hours are accepted.
<img width="1031" height="656" alt="Screenshot 2026-09-07 215733" src="https://github.com/user-attachments/assets/303d0f7d-42c8-47dc-88bc-54922175ab59" />

The main dashboard now shows three slots ((C1)COMPACT, (C8)COMPACT, and (EV1)EV) marked as red, indicating they are currently occupied. The Live Statistics sidebar automatically updates to reflect 3 occupied slots and 12 available slots.
<img width="1036" height="650" alt="Screenshot 2026-09-07 215807" src="https://github.com/user-attachments/assets/63b4a766-552c-49ae-8bd8-1501dea9cfdc" />

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the issues page or submit a pull request.
Thank You 😊
