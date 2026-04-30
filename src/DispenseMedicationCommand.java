public class DispenseMedicationCommand implements Command {
    private final Pharmacist pharmacist;
    private final Patient patient;
    private final String medicineName;
    private final int quantity;
    private String transactionID;

    public DispenseMedicationCommand(Pharmacist pharmacist, Patient patient, String medicineName, int quantity) {
        this.pharmacist = pharmacist;
        this.patient = patient;
        this.medicineName = medicineName;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        transactionID = pharmacist.dispenseMedication(patient, medicineName, quantity);
    }

    @Override
    public void undo() {
        if (transactionID == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        new FileHandler("pharmacyRecords.txt").removeRecord(transactionID);
        new Inventory().restockMedicine(medicineName, quantity);
        System.out.println("Dispense undone. Transaction " + transactionID + " removed and inventory restored.");
    }
}
