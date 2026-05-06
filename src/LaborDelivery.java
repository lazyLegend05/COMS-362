import java.time.LocalDate;
import java.util.Scanner;

public class LaborDelivery {
	
	private String hospitalName;
	private String city;
	private String state;
	private String country = "United States of America";
	
	public LaborDelivery(String hospitalName, String city, String state) {
		this.hospitalName = hospitalName;
		this.city = city;
		this.state = state;
	}

	public void createBirthCertificate(Scanner scnr, FileHandler fh) {
		
		BCBuilder builder = new BCBuilder();
		
		builder.addChildInformation(scnr);
		builder.addPlaceTime(hospitalName, city + " " + state + " " + country, LocalDate.now());
		builder.addParentInformation(scnr);
		
		String[] data = builder.returnData();
		
		fh.writeRecord("First Name, " + data[0], true);
		fh.writeRecord("Middle Name, " + data[1], true);
		fh.writeRecord("Last Name, " + data[2], true);
		fh.writeRecord("Sex, " + data[3], true);
		fh.writeRecord("Date, " + data[4], true);
		fh.writeRecord("Hospital, " + data[5], true);
		fh.writeRecord("City, " + data[6], true);
		fh.writeRecord("Mother's Name, " + data[7], true);
		fh.writeRecord("Father's Name, " + data[8], true);
		fh.writeRecord("Registrar, Not yet certified", true);
		
	}
	
	public void certifyBirthCertificate(FileHandler fh, LocalDate certDate) {
		
		fh.updateRecord("Registrar", "Registrar, Certified by the " + city + " Registrar's Office of " + state + " on " + certDate.toString());
		
	}
	
}
