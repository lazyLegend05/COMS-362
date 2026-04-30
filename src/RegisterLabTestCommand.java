public class RegisterLabTestCommand implements Command {
    private final LabStaff labStaff;
    private final Patient patient;
    private final String test;
    private final FileHandler fileHandler;
    private String testID;

    public RegisterLabTestCommand(LabStaff labStaff, Patient patient, String test, FileHandler fileHandler) {
        this.labStaff = labStaff;
        this.patient = patient;
        this.test = test;
        this.fileHandler = fileHandler;
    }

    @Override
    public void execute() {
        testID = labStaff.registerLabTest(patient, test, fileHandler);
    }

    @Override
    public void undo() {
        if (testID == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        fileHandler.removeRecord(testID);
        System.out.println("Lab test registration undone. Test ID " + testID + " removed.");
    }
}
