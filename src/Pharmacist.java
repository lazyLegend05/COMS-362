public class Pharmacist {
    private String name;
    private String staffID;

    public Pharmacist(String name, String staffID) {
        this.name = name;
        this.staffID = staffID;
    }

    public void dispenseMedication(Patient p, String medicineName, int quantity) {
        Inventory inventory = new Inventory();

        if (p == null) {
            System.out.println("Invalid patient.");
            return;
        }

        if (medicineName == null || medicineName.trim().isEmpty()) {
            System.out.println("Invalid medicine name.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Invalid quantity entered.");
            return;
        }

        if (!inventory.checkAvailability(medicineName, quantity)) {
            System.out.println("Medicine unavailable or insufficient stock.");
            return;
        }

        boolean stockUpdated = inventory.updateStock(medicineName, quantity);
        if (!stockUpdated) {
            System.out.println("Failed to update inventory.");
            return;
        }

        PrescriptionRecord record = new PrescriptionRecord(medicineName, quantity);
        FileHandler fh = new FileHandler("pharmacyRecords.txt");
        fh.writeRecord(record.toFileString(p));

        System.out.println("Medication dispensed successfully!");
        System.out.println("Transaction ID: " + record.getTransactionID());
    }

    public void restockMedicine(String medicineName, int quantity) {
        Inventory inventory = new Inventory();

        if (medicineName == null || medicineName.trim().isEmpty()) {
            System.out.println("Invalid medicine name.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Invalid quantity entered.");
            return;
        }

        boolean restocked = inventory.restockMedicine(medicineName, quantity);
        if (!restocked) {
            System.out.println("Failed to restock medicine.");
            return;
        }

        RestockRecord record = new RestockRecord(medicineName, quantity);
        FileHandler fh = new FileHandler("restockRecords.txt");
        fh.writeRecord(record.toFileString());

        System.out.println("Medicine restocked successfully!");
        System.out.println("Restock Record ID: " + record.getRecordID());
    }
}