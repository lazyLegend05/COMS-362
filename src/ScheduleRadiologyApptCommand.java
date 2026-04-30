import java.time.LocalDateTime;

public class ScheduleRadiologyApptCommand implements Command {
    private final Radiology dept;
    private final Patient patient;
    private final MachineOrders orders;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private RadiologyAppt bookedAppt;

    public ScheduleRadiologyApptCommand(Radiology dept, Patient patient, MachineOrders orders,
                                        LocalDateTime start, LocalDateTime end) {
        this.dept = dept;
        this.patient = patient;
        this.orders = orders;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute() {
        boolean booked = dept.scheduleAppt(patient, orders, start, end);
        if (booked) {
            bookedAppt = dept.getAppointment(patient.getName());
        }
    }

    @Override
    public void undo() {
        if (bookedAppt == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        new FileHandler("radioImageAppointments.txt").removeRecord(patient.getName());
        bookedAppt.getMachine().releaseTime(start, end);
        System.out.println("Radiology appointment undone for patient: " + patient.getName());
    }
}
