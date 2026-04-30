public class Pharmacist {
    private String name;
    private String staffID;

    public Pharmacist(String name, String staffID) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Pharmacist name cannot be empty.");
        }

        if (staffID == null || staffID.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff ID cannot be empty.");
        }

        this.name = name.trim();
        this.staffID = staffID.trim();
    }

    public String dispenseMedication(Patient p, String medicineName, int quantity) {
        Inventory inventory = new Inventory();

        if (p == null) {
            System.out.println("Invalid patient.");
            return null;
        }

        if (medicineName == null || medicineName.trim().isEmpty()) {
            System.out.println("Invalid medicine name.");
            return null;
        }

        if (quantity <= 0) {
            System.out.println("Invalid quantity entered.");
            return null;
        }

        medicineName = medicineName.trim();

        if (!inventory.checkAvailability(medicineName, quantity)) {
            System.out.println("Medicine unavailable or insufficient stock.");
            return null;
        }

        boolean stockUpdated = inventory.updateStock(medicineName, quantity);

        if (!stockUpdated) {
            System.out.println("Failed to update inventory.");
            return null;
        }

        PrescriptionRecord record = new PrescriptionRecord(medicineName, quantity);
        FileHandler fh = new FileHandler("pharmacyRecords.txt");
        fh.writeRecord(record.toFileString(p));

        System.out.println("Medication dispensed successfully!");
        System.out.println("Transaction ID: " + record.getTransactionID());
        return record.getTransactionID();
    }

    public String restockMedicine(String medicineName, int quantity) {
        Inventory inventory = new Inventory();

        if (medicineName == null || medicineName.trim().isEmpty()) {
            System.out.println("Invalid medicine name.");
            return null;
        }

        if (quantity <= 0) {
            System.out.println("Invalid quantity entered.");
            return null;
        }

        medicineName = medicineName.trim();

        boolean restocked = inventory.restockMedicine(medicineName, quantity);

        if (!restocked) {
            System.out.println("Failed to restock medicine.");
            return null;
        }

        RestockRecord record = new RestockRecord(medicineName, quantity);
        FileHandler fh = new FileHandler("restockRecords.txt");
        fh.writeRecord(record.toFileString());

        System.out.println("Medicine restocked successfully!");
        System.out.println("Restock Record ID: " + record.getRecordID());
        return record.getRecordID();
    }
    public boolean addNewMedicine(String medicineName, int quantity) {
        Inventory inventory = new Inventory();

        if (medicineName == null || medicineName.trim().isEmpty()) {
            System.out.println("Invalid medicine name.");
            return false;
        }

        if (quantity <= 0) {
            System.out.println("Invalid quantity entered.");
            return false;
        }

        boolean added = inventory.addNewMedicine(medicineName, quantity);

        if (!added) {
            System.out.println("Failed to add new medicine.");
            return false;
        }

        System.out.println("New medicine added successfully!");
        return true;
    }
}