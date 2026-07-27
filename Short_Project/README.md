# Short Java Hotel Management System

A short, complete Java-based Hotel Management System with GUI interface (Swing / Applet compliant), HTML/CSS web presentation portal, and MongoDB / In-Memory storage.

## Files Included

1. **`HotelManagement.java`**: The main self-contained Java GUI application.
2. **`hotel.html`**: Combined HTML & CSS web portal for project presentation & applet embedding.

---

## Features

- **🏨 Room Booking**: Enter guest details, select room type (Standard, Deluxe, Executive Suite), set duration, and calculate prices.
- **🔑 Check-In & Check-Out Billing**: Search bookings by ID, change status, and generate detailed GST tax invoices.
- **📊 Interactive JTable**: View all live booking records.
- **🌐 HTML Portal Exporter**: Generates and launches `hotel.html` directly from the Java application.
- **💾 MongoDB / In-Memory Mode**: Works natively without external databases using in-memory collection, while automatically connecting to local MongoDB (`mongodb://localhost:27017`) if available.

---

## How to Run

### 1. Compile the Java Application
```cmd
javac HotelManagement.java
```

### 2. Launch Desktop GUI Application
```cmd
java HotelManagement
```

### 3. Open Web Presentation Portal
Double-click `hotel.html` or open it in any web browser.
