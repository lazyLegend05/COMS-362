
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Radiology {

	private ArrayList<Machine> machines = new ArrayList<Machine>();
	private ArrayList<Doctor> approvedDoctors = new ArrayList<Doctor>();
	private ArrayList<RadiologyAppt> bookedAppts = new ArrayList<RadiologyAppt>();

	public Radiology() {}

	public Radiology(ArrayList<Machine> machines, ArrayList<Doctor> doctors) {
		this.machines.addAll(machines);
		this.approvedDoctors.addAll(doctors);
	}


	public void addMachine(Machine machine) {
		machines.add(machine);
	}

	public void removeMachine(Machine target) {
		machines.remove(target);
	}

	public void addDoctor(Doctor doc) {
		approvedDoctors.add(doc);
	}

	public boolean checkOrders(Patient patient, MachineOrders orders) {
		return (patient.equals(orders.getPatient()) && approvedDoctors.contains(orders.getDoctor()));
	}

	public boolean scheduleAppt(Patient patient, MachineOrders orders, LocalDateTime start, LocalDateTime end) {

		if (!checkOrders(patient, orders)) {
			System.out.println("Sorry, those orders are not valid.");
			return false;
		}
		
		boolean booked = false;
		for (Machine machine: machines) {
			
			if (machine.getType().equals(orders.getType()) && !machine.isBooked(start, end)) {
				bookedAppts.add(new RadiologyAppt(orders, machine, start, end));
				machine.bookTime(start, end);
				booked = true;
				break;
			}
			
		}

		return booked;
			
	}

	
}
