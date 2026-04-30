public class RestockMedicineCommand implements Command {
    private final Pharmacist pharmacist;
    private final String medicineName;
    private final int quantity;
    private String recordID;

    public RestockMedicineCommand(Pharmacist pharmacist, String medicineName, int quantity) {
        this.pharmacist = pharmacist;
        this.medicineName = medicineName;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        recordID = pharmacist.restockMedicine(medicineName, quantity);
    }

    @Override
    public void undo() {
        if (recordID == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        new FileHandler("restockRecords.txt").removeRecord(recordID);
        new Inventory().updateStock(medicineName, quantity);
        System.out.println("Restock undone. Record " + recordID + " removed and inventory decremented.");
    }
}
