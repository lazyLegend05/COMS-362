public class ExecuteRadiologyApptCommand implements Command {
    private final Radiology dept;
    private final RadiologyAppt appt;
    private final String result;
    private String capturedOrdersRecord;
    private String capturedApptRecord;
    private boolean executed = false;

    public ExecuteRadiologyApptCommand(Radiology dept, RadiologyAppt appt, String result) {
        this.dept = dept;
        this.appt = appt;
        this.result = result;
    }

    @Override
    public void execute() {
        String patientName = appt.getPatient().getName();
        capturedOrdersRecord = new FileHandler("machineOrders.txt").findRecord(patientName);
        capturedApptRecord = new FileHandler("radioImageAppointments.txt").findRecord(patientName);
        dept.executeAppt(appt, result);
        executed = true;
    }

    @Override
    public void undo() {
        if (!executed) {
            System.out.println("Nothing to undo.");
            return;
        }
        String patientName = appt.getPatient().getName();
        new FileHandler("radioImageRecords.txt").removeRecordByPrefix(patientName + "| ");
        if (capturedOrdersRecord != null) {
            new FileHandler("machineOrders.txt").writeRecord(capturedOrdersRecord);
        }
        if (capturedApptRecord != null) {
            new FileHandler("radioImageAppointments.txt").writeRecord(capturedApptRecord);
        }
        System.out.println("Radiology appointment execution undone for patient: " + patientName);
    }
}
