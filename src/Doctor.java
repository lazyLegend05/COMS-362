
public class Doctor extends Staff {

	public Doctor(String name, String staffID) {
		super(name, staffID, "doctor");
	}

	public MachineOrders makeMachineOrders(Patient patient, String type) {
		// TODO: write to a file instead of returning anything
		return new MachineOrders(patient, this, type);
	}
	
}
