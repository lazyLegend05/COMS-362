public class FireEmployeeCommand implements Command {
    private final HR hr;
    private final String staffID;
    private String capturedRecord;
    private boolean executed = false;

    public FireEmployeeCommand(HR hr, String staffID) {
        this.hr = hr;
        this.staffID = staffID;
    }

    @Override
    public void execute() {
        capturedRecord = new FileHandler("StaffRecords.txt").findRecord(staffID);
        executed = hr.fire(staffID);
    }

    @Override
    public void undo() {
        if (!executed || capturedRecord == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        new FileHandler("StaffRecords.txt").writeRecord(capturedRecord);
        hr.rehireFromRecord(capturedRecord);
        System.out.println("Fire undone. Employee " + staffID + " restored.");
    }
}
