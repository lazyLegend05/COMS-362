public abstract class EmergencyOperation {

    protected FileHandler fileHandler;
    protected Patient patient;

    public EmergencyOperation() {
        this.fileHandler = new FileHandler("emergency_records.txt");
    }

    public final void execute(java.util.Scanner sc) {
        String patientID = getPatientID(sc);
        patient = findPatient(patientID);

        if (!validatePatient(patient)) {
            System.out.println("Patient not found. Operation cancelled.");
            return;
        }

        displayPatient(patient);
        processOperation(sc, patientID);
        saveRecord(patientID);
        confirm();
    }

    protected String getPatientID(java.util.Scanner sc) {
        System.out.print("Enter patient ID: ");
        return sc.nextLine().trim();
    }

    protected Patient findPatient(String patientID) {
        return fileHandler.findPatient(patientID);
    }

    protected boolean validatePatient(Patient patient) {
        return patient != null;
    }

    protected void displayPatient(Patient patient) {
        System.out.println("Patient found: " + patient.getName()
                + ", Age: " + patient.getAge());
    }

    protected abstract void processOperation(java.util.Scanner sc, String patientID);
    protected abstract void saveRecord(String patientID);

    protected void confirm() {
        System.out.println("Operation completed successfully.");
    }
}