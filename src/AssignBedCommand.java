public class AssignBedCommand implements Command {
    private final BedManager bedManager;
    private final String bedID;
    private final String patientID;
    private boolean executed = false;

    public AssignBedCommand(BedManager bedManager, String bedID, String patientID) {
        this.bedManager = bedManager;
        this.bedID = bedID;
        this.patientID = patientID;
    }

    @Override
    public void execute() {
        executed = bedManager.assignBed(bedID, patientID);
        if (executed) {
            System.out.println("Bed " + bedID + " assigned to patient " + patientID + ".");
        } else {
            System.out.println("Could not assign bed. Please check the bed ID.");
        }
    }

    @Override
    public void undo() {
        if (!executed) {
            System.out.println("Nothing to undo.");
            return;
        }
        bedManager.releaseBed(bedID);
        System.out.println("Bed assignment undone. Bed " + bedID + " is now available.");
    }
}
