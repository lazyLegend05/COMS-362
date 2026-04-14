
public class LabStaff extends Staff{
	public LabStaff(String name, String staffID, String position) {
		super(name, staffID, position);
	}
	
	public void registerLabTest(Patient patient, String test, FileHandler fh) {
		String testID = "L" + System.currentTimeMillis(); 
		
		LaboratoryTests labTest = new LaboratoryTests(testID, patient, test);
		
		fh.writeRecord(labTest.toFileString());
		
		System.out.println("Lab test registered by " + getName() + "! Test ID: " + testID);
	}
		
}

	

