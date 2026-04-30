public class HireEmployeeCommand implements Command {
    private final HR hr;
    private final String name;
    private final String position;
    private String staffID;

    public HireEmployeeCommand(HR hr, String name, String position) {
        this.hr = hr;
        this.name = name;
        this.position = position;
    }

    @Override
    public void execute() {
        staffID = hr.hire(name, position);
    }

    @Override
    public void undo() {
        if (staffID == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        hr.removeEmployee(staffID);
        hr.removeFromFile(staffID);
        System.out.println("Hire undone. " + name + " (ID: " + staffID + ") removed.");
    }
}
