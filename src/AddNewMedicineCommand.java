public class AddNewMedicineCommand implements Command {
    private final Pharmacist pharmacist;
    private final String medicineName;
    private final int quantity;
    private boolean executed = false;

    public AddNewMedicineCommand(Pharmacist pharmacist, String medicineName, int quantity) {
        this.pharmacist = pharmacist;
        this.medicineName = medicineName;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        executed = pharmacist.addNewMedicine(medicineName, quantity);
    }

    @Override
    public void undo() {
        if (!executed) {
            System.out.println("Nothing to undo.");
            return;
        }
        new FileHandler("src/inventory.txt").removeRecord(medicineName);
        System.out.println("Add medicine undone. " + medicineName + " removed from inventory.");
    }
}
