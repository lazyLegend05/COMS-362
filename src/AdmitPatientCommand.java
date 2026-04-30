public class AdmitPatientCommand implements Command {
    private final ICUNurse nurse;
    private final Patient patient;
    private final ICURecord record;
    private final FileHandler fileHandler;
    private boolean executed = false;

    public AdmitPatientCommand(ICUNurse nurse, Patient patient, ICURecord record, FileHandler fileHandler) {
        this.nurse = nurse;
        this.patient = patient;
        this.record = record;
        this.fileHandler = fileHandler;
    }

    @Override
    public void execute() {
        nurse.admitPatient(patient, record, fileHandler);
        executed = true;
    }

    @Override
    public void undo() {
        if (executed) {
            fileHandler.removeRecord(record.getPatientID());
            System.out.println("Admission undone for patient: " + record.getPatientID());
        }
    }
}
