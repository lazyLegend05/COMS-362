public class AssignNurseCommand implements ICUCommand {
    private final ICUNurse nurse;
    private final String patientID;
    private final String nurseID;
    private final FileHandler patientFile;
    private final FileHandler nurseFile;
    private String previousNurseID;

    public AssignNurseCommand(ICUNurse nurse, String patientID, String nurseID,
                              FileHandler patientFile, FileHandler nurseFile) {
        this.nurse = nurse;
        this.patientID = patientID;
        this.nurseID = nurseID;
        this.patientFile = patientFile;
        this.nurseFile = nurseFile;
    }

    @Override
    public void execute() {
        String existing = patientFile.findRecord(patientID);
        if (existing != null) {
            String[] fields = existing.split(",", -1);
            previousNurseID = fields[fields.length - 1].trim();
        }
        nurse.assignNurseToPatient(patientID, nurseID, patientFile, nurseFile);
    }

    @Override
    public void undo() {
        if (previousNurseID == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        String existing = patientFile.findRecord(patientID);
        if (existing != null) {
            String[] fields = existing.split(",", -1);
            fields[fields.length - 1] = previousNurseID;
            patientFile.updateRecord(patientID, String.join(",", fields));
            System.out.println("Nurse assignment undone for patient: " + patientID);
        }
    }
}
