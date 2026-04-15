
public class MachineOrders {

	private Doctor doctor;
	private Patient patient;
	private String type;

	public MachineOrders(Doctor doctor, Patient patient, String type) {
		this.doctor = doctor;
		this.patient = patient;
		this.type = type;
	}

	public Doctor getDoctor() {return doctor;}
	public Patient getPatient() {return patient;}
	public String getType() {return type;}

	
}
