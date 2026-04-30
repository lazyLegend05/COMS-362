public class RegisterEmergencyPatientCommand implements Command {
    private final Patient patient;
    private final EmergencyRecord record;
    private final FileHandler fileHandler;
    private boolean executed = false;

    public RegisterEmergencyPatientCommand(Patient patient, EmergencyRecord record, FileHandler fileHandler) {
        this.patient = patient;
        this.record = record;
        this.fileHandler = fileHandler;
    }

    @Override
    public void execute() {
        fileHandler.writeRecord(record.toFileString(patient));
        executed = true;
        System.out.println("Registration confirmed.");
        System.out.println("Generated Patient ID: " + record.getPatientID());
    }

    @Override
    public void undo() {
        if (!executed) {
            System.out.println("Nothing to undo.");
            return;
        }
        fileHandler.removeRecord(record.getPatientID());
        System.out.println("Emergency registration undone for patient: " + record.getPatientID());
    }
}
