public class AssignBedOperation extends EmergencyOperation {

    private BedManager bedManager;
    private String selectedBedID;

    public AssignBedOperation() {
        super();
        this.bedManager = new BedManager();
    }

    @Override
    protected void processOperation(java.util.Scanner sc, String patientID) {
        // check if patient already has a bed
        String currentBed = bedManager.findPatientBed(patientID);
        if (currentBed != null) {
            System.out.println("Patient is currently occupying bed: " + currentBed);
            System.out.print("Do you want to switch beds? (yes/no): ");
            String switchInput = sc.nextLine().trim().toLowerCase();
            while (!switchInput.equals("yes") && !switchInput.equals("no")) {
                System.out.print("Please enter yes or no: ");
                switchInput = sc.nextLine().trim().toLowerCase();
            }
            if (!switchInput.equals("yes")) {
                System.out.println("Bed assignment unchanged.");
                selectedBedID = null;
                return;
            }
            bedManager.freeBed(patientID);
            System.out.println("Bed " + currentBed + " has been freed.");
        }

        java.util.List<Bed> availableBeds = bedManager.getAvailableBeds();
        if (availableBeds.isEmpty()) {
            System.out.println("No beds available. Placing patient on waiting list.");
            selectedBedID = null;
            return;
        }

        System.out.println("Available beds:");
        for (Bed b : availableBeds) {
            System.out.println("  Bed ID: " + b.getBedID()
                    + " | Ward: " + b.getWard());
        }

        System.out.print("Enter bed ID to assign: ");
        selectedBedID = sc.nextLine().trim().toUpperCase();

        boolean success = bedManager.assignBed(selectedBedID, patientID);
        if (!success) {
            System.out.println("Could not assign bed. Check bed ID.");
            selectedBedID = null;
        }
    }

    @Override
    protected void saveRecord(String patientID) {
        // bed status already saved inside BedManager.assignBed()
    }

    @Override
    protected void confirm() {
        if (selectedBedID != null) {
            System.out.println("Bed " + selectedBedID
                    + " successfully assigned to " + patient.getName());
        }
    }
}