import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class HR {
	
	private String name;
	
	private String staffID;
	
	private ArrayList<Staff> employees = new ArrayList<>();
	
	/**
	 * Creates the HR object
	 * 
	 * @param name
	 * @param staffID
	 */
	public HR(String name, String staffID) {
		this.name = name;
		this.staffID = staffID;
	}
	
	/**
	 * Hires a new employee by entering information and saving it to a .txt file
	 */
	public void hire() {
		Scanner input = new Scanner(System.in);
		
		String employeeName;
		
		// Gets an employees name
		while(true) {
			System.out.println("Enter employee name: ");
			employeeName = input.nextLine();
			
			// Checks to make sure that HR has entered the employees name
			if (employeeName.trim().isEmpty()) {
				System.out.println("Invalid name.");
			} else {
				break;
			}
		}
		
		String position;
		
		// Gets an employees position
		while(true) {
			System.out.print("Enter position: ");
			position = input.nextLine();
			
			// Checks to make sure that HR has entered the employees position
			if(position.trim().isEmpty()) {
				System.out.println("Invalid position");
			} else {
				break;
			}
		} 
		
		// Generates the new hires unique staffID
		String newHireID = "S" + System.currentTimeMillis();
		
		// Create staff object
		Staff newStaff = new Staff(employeeName, newHireID, position);
		
		employees.add(newStaff);
		
		// Save to a .txt file
		FileHandler fileHandler = new FileHandler("StaffRecords.txt");
		fileHandler.writeRecord(newStaff.toFileString());
		
		System.out.println(employeeName + " was hired successfully!");
		System.out.println("Staff ID: " + newHireID);
	}
	
	/**
	 * Fires an employee using their staff ID and removes them from the .txt file
	 */
	public void fire() {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter Staff ID to fire: ");
		String staffToFire = input.nextLine();
		
		boolean found = false;
		
		// Searches through the employee list
		for(int i = 0; i < employees.size(); i++) {
			if(employees.get(i).getStaffID().equals(staffToFire)) {
				System.out.println(employees.get(i).getName() + " was fired.");
				
				// Removes the employee from the list
				employees.remove(i);
				
				// Removes the employee from the .txt file
				removeFromFile(staffToFire);
				found = true;
				break;
			}
		}
		
		if(!found) {
			System.out.println("Employee not found");
		}
		
	}
	
	/**
	 * Removes the employee from the .txt file and overwrites the old file with the new updated one
	 * @param staffID
	 */
	public void removeFromFile(String staffID) {
		try {
			File staffRecord = new File("StaffRecords.txt");
			
			// List of staff
			ArrayList<String> updateStaffRecord = new ArrayList<>();
			
			Scanner fileReader = new Scanner(staffRecord);
			
			// Reads each line from the file
			while(fileReader.hasNextLine()) {
				String currentStaffFromFile = fileReader.nextLine();
				
				// Keeps only the ones that don't match the ID we are looking to fire
				if(!currentStaffFromFile.startsWith(staffID + ",")) {
					updateStaffRecord.add(currentStaffFromFile);
				}
			}
			
			fileReader.close();
			
			// Overwrite file with new data
			FileWriter fileWriterToOverWriteFile = new FileWriter(staffRecord, false);
			
			for(String staff : updateStaffRecord) {
				fileWriterToOverWriteFile.write(staff);
				fileWriterToOverWriteFile.write(System.lineSeparator());
			}
			
			fileWriterToOverWriteFile.close();
			
		} catch (IOException e) {
			System.out.println("Error updating the file.");
		}
	}
}
