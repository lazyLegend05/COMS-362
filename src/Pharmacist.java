public class Pharmacist {
    private String name;
    private String staffID;

    public Pharmacist(String name, String staffID) {
        this.name = name;
        this.staffID = staffID;
    }

    public void dispenseMedication(Patient p, String medicineName, int quantity) {
        Inventory inventory = new Inventory();

        if (quantity <= 0) {
            System.out.println("Invalid quantity entered.");
            return;
        }

        if (!inventory.checkAvailability(medicineName, quantity)) {
            System.out.println("Medicine unavailable or insufficient stock.");
            return;
        }

        PrescriptionRecord record = new PrescriptionRecord(medicineName, quantity);
        FileHandler fh = new FileHandler("pharmacyRecords.txt");
        fh.writeRecord(record.toFileString(p));
        inventory.updateStock(medicineName, quantity);

        System.out.println("Medication dispensed successfully!");
        System.out.println("Transaction ID: " + record.getTransactionID());
    }
}
