
public class Doctor extends Staff {

	public Doctor(String name, String staffID) {
		super(name, staffID, "doctor");
	}

	public MachineOrders makeMachineOrders(Patient patient, String type) {
		return new MachineOrders(this, patient, type);
	}
	
	
}
