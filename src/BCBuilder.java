
import java.time.LocalDate;
import java.util.Scanner;

public class BCBuilder {

	private String[] data;
	
	public BCBuilder() {
		String[] constructor = {"no first name", "", "no last name", "no assigned sex", 	// 0-3 child information
								"no date", "no hospital", "no city", 						// 4-6 time + place
								"no mother name", null};									// 7-8 parent names
		data = constructor;
	}
	
	public void addChildInformation(Scanner scnr) {
		
		boolean correct = false;
		String first, middle, last, sex;
		
		do {
			
			System.out.println("Enter the name of the child born.");
			String ans;
			String[] fields = new String[0];
			while (fields.length < 2) {
				System.out.println("Please use the format \"LAST FIRST MIDDLE1 MIDDLE2 etc.\" Middle name(s) are optional.");
				ans = scnr.nextLine();
				fields = ans.split(" ", -1);
			}
			
			last = fields[0];
			first = fields[1];
			
			
			if (fields.length > 2) {
				
				middle = fields[2];
				for (int i = 3; i < fields.length; i++) {
					middle += fields[i];
				}
				
			} else {
				middle = null;
			}
			
			System.out.println("Enter the sex of the child born (f, m, i)");
			ans = scnr.nextLine().trim().toLowerCase();
			while (!(ans.equals("m") || ans.equals("f") || ans.equals("i"))) {
				System.out.println("Incorrect format. Use \"f\", \"m\", or \"i\"");
				ans = scnr.nextLine();
			}
			
			switch(ans) {
				case "f":
					sex = "Female";
					break;
				case "m":
					sex = "Male";
					break;
				default:
					sex = "Intersex";
					break;
			}
			
			System.out.println("Name: " + first + " " + middle + " " + last);
			System.out.println("Sex: " + sex);
			System.out.println("Is this information correct? (y/n)");
			ans = scnr.nextLine().trim().toLowerCase();
	        while (!(ans.equals("y") || ans.equals("n"))) {
	            System.out.print("Please enter y or n: ");
	            ans = scnr.nextLine().trim().toLowerCase();
	        }
	        
	        if (ans.equals("y")) {
	        	correct = true;
	        }
			
		} while(!correct);
			
			
		data[0] = first;
		data[1] = middle;
		data[2] = last;
		data[3] = sex;
		
		System.out.println("Child information registered successfully");
		
	}
	
	public void addPlaceTime(String hospitalName, String cityFull, LocalDate date) {
		
		data[4] = date.toString();
		data[5] = hospitalName;
		data[6] = cityFull;
		
		System.out.println("Place and time informations registered successfully");
		
	}
	
	public void addParentInformation(Scanner scnr) {
		
		boolean correct = false;
		String motherName, fatherName;
		
		do {
			
			System.out.println("Please enter the mother's name.");
			String ans = null;
			String[] fields = new String[0];
			while (fields.length != 2) {
				System.out.println("Please use the format \"FIRST LAST\"");
				ans = scnr.nextLine();
				fields = ans.split(" ", -1);
			}
			
			motherName = ans;
			
			System.out.println("Please enter the father's name.");
			ans = null;
			fields = new String[0];
			while (fields.length != 2) {
				System.out.println("Please use the format \"FIRST LAST\", or enter nothing if father is unknown");
				ans = scnr.nextLine();
				if (ans.isEmpty()) {
					ans = "Unknown";
					break;
				}
				fields = ans.split(" ", -1);
			}
			
			fatherName = ans;
			
			System.out.println("Mother: " + motherName);
			System.out.println("Father: " + fatherName);
			System.out.println("Is this information correct? (y/n)");
			ans = scnr.nextLine().trim().toLowerCase();
	        while (!(ans.equals("y") || ans.equals("n"))) {
	            System.out.print("Please enter y or n: ");
	            ans = scnr.nextLine().trim().toLowerCase();
	        }
	        
	        if (ans.equals("y")) {
	        	correct = true;
	        }
        
		} while (!correct);
		
		data[7] = motherName;
		data[8] = fatherName;
		
		System.out.println("Parent data registered successfully");
		
	}
	
	public String[] returnData() {return data;}
	
}
