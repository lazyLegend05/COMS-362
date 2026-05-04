public class DischargePatientOperation extends EmergencyOperation {

    private BedManager bedManager;
    private DischargeRecord dischargeRecord;
    private String doctorName = "";
    private String prescription = "";
    private boolean confirmed = false;

    public DischargePatientOperation() {
        super();
        this.bedManager = new BedManager();
    }

    @Override
    protected void processOperation(java.util.Scanner sc, String patientID) {
        // validate doctor
        EmergencyDoctor doctor = null;
        while (doctor == null) {
            System.out.print("\nEnter attending doctor's name: ");
            doctorName = sc.nextLine().trim();
            System.out.print("Enter doctor ID: ");
            String doctorID = sc.nextLine().trim();
            doctor = EmergencyDoctor.validate(doctorID, doctorName);
            if (doctor == null) {
                System.out.println("Doctor not found. Please check name and ID.");
            }
        }

        // doctor inspects
        boolean cleared = doctor.inspectPatient(patient, sc);
        if (!cleared) {
            System.out.println("Discharge cancelled. Patient requires further treatment.");
            return;
        }

        // doctor prescribes
        prescription = doctor.prescribeMedicine(sc);

        // confirm discharge
        System.out.print("\nConfirm discharge? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        while (!confirm.equals("yes") && !confirm.equals("no")) {
            System.out.print("Please enter yes or no: ");
            confirm = sc.nextLine().trim().toLowerCase();
        }

        if (!confirm.equals("yes")) {
            System.out.println("Discharge cancelled.");
            return;
        }

        confirmed = true;
        dischargeRecord = new DischargeRecord(patientID, doctorName, prescription);

        // free the bed
        boolean bedFreed = bedManager.freeBed(patientID);
        if (bedFreed) {
            System.out.println("Bed successfully freed.");
        } else {
            System.out.println("No bed was assigned to this patient.");
        }
    }

    @Override
    protected void saveRecord(String patientID) {
        if (!confirmed) return;
        FileHandler dischargeFile = new FileHandler("discharge_records.txt");
        dischargeFile.writeRecord(dischargeRecord.toFileString(patient));
    }

    @Override
    protected void confirm() {
        if (confirmed) {
            System.out.println("\nPatient " + patient.getName()
                    + " discharged successfully!");
            System.out.println("Discharge ID: " + dischargeRecord.getDischargeID());
            System.out.println("Discharged by Dr. " + doctorName);
            System.out.println("Prescription: " + prescription);
        }
    }
}