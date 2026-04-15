
import java.util.Scanner;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Hospital Management System ===");
        System.out.println("1. Pharmacy Department");
        System.out.println("2. Emergency Care Department");
        System.out.println("3. Laboratory Department");
        System.out.println("4. Radiology Department");
        System.out.print("Choose department: ");
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1:
                runPharmacy(sc);
                break;
            case 2:
                runEmergency(sc);
                break;
            case 3:
                runLab(sc);
                break;
            case 4:
                runRadiology(sc);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        sc.close();
    }

    public static void runPharmacy(Scanner sc) {
        System.out.println("\n=== Pharmacy Department ===");

        System.out.print("Enter patient name: ");
        String patientName = sc.nextLine();

        System.out.print("Enter medicine name: ");
        String medicineName = sc.nextLine();

        System.out.print("Enter quantity prescribed: ");
        int quantity = Integer.parseInt(sc.nextLine());

        Patient patient = new Patient(patientName);
        Pharmacist pharmacist = new Pharmacist("Aadi", "P001");

        pharmacist.dispenseMedication(patient, medicineName, quantity);
    }

    public static void runEmergency(Scanner sc) {
        System.out.println("\n=== Emergency Care Department ===");

        FileHandler fileHandler = new FileHandler("emergency_records.txt");

        System.out.print("Is the patient unconscious? (yes/no): ");
        String unconsciousInput = sc.nextLine().trim().toLowerCase();

        String infoSource;
        if (unconsciousInput.equals("yes")) {
            infoSource = "Companion";
            System.out.println("Companion will provide patient information.");
        } else {
            infoSource = "Patient";
        }

        System.out.print("Enter patient name: ");
        String name = sc.nextLine();

        int age;
        while (true) {
            System.out.print("Enter patient age: ");
            try {
                age = Integer.parseInt(sc.nextLine());
                if (age > 0) {
                    break;
                } else {
                    System.out.println("Age must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter a valid number.");
            }
        }

        System.out.print("Enter contact number: ");
        String contactNum = sc.nextLine();

        System.out.print("Enter chief complaint (reason for visit): ");
        String complaint = sc.nextLine();

        Patient patient = new Patient(name, age, contactNum);
        EmergencyRecord record = new EmergencyRecord(complaint, infoSource);

        fileHandler.writeRecord(record.toFileString(patient));

        System.out.println("Registration confirmed.");
        System.out.println("Generated Patient ID: " + record.getPatientID());
    }

    public static void runLab(Scanner sc) {
    	System.out.println("\n=== Laboratory Department ===");
    	
    	FileHandler fileHandler  = new FileHandler("lab.txt");
    	
    	System.out.print("Enter patient name: ");
    	String name = sc.nextLine();
    	
    	int age;
    	while (true) {
    		System.out.print("Enter patient age: ");
    		
    		try {
    			age = Integer.parseInt(sc.nextLine());
    			if (age > 0) {
    				break;
    			} else {
    				System.out.println("Age must be greater than 0.");
    			}
    		} catch (NumberFormatException e) {
    			System.out.println("Invalid age. Please enter a valid number");
    		}
    	}
    	
    	System.out.print("Enter Phone Number: ");
    	String contact = sc.nextLine();
    	
    	System.out.print("Enter Test Type: ");
    	String test = sc.nextLine();
    	
    	Patient patient = new Patient(name, age, contact);
    	LabStaff labStaff = new LabStaff("Billy", "LS001", "Lab Tech");
    	
    	labStaff.registerLabTest(patient, test, fileHandler);
    	
    	System.out.println("Lab test request complete");
    }
    
    public static void runRadiology(Scanner scnr) {
    	
    	System.out.println("\n=== Radiology Department ===");

        System.out.print("Enter patient name: ");
        String patientName = scnr.nextLine();
        
        Patient patient = new Patient(patientName);
        Doctor testDoctor = new Doctor("Dr. Ex", "ID123");
        
        Radiology radioImageDept = new Radiology();
		Machine testMRI = new Machine("coolbrand98345", "MRI");
		radioImageDept.addMachine(testMRI);
		radioImageDept.addDoctor(testDoctor);
		
		MachineOrders orders = testDoctor.makeMachineOrders(patient, "MRI");
		
        Patient testPatient = new Patient("Bobbo");
		MachineOrders testOrders = testDoctor.makeMachineOrders(testPatient, "MRI");
		radioImageDept.scheduleAppt(testPatient, testOrders, LocalDateTime.of(2026, 1, 1, 1, 0), LocalDateTime.of(2026, 1, 1, 3, 0));
		
		System.out.println(patient.getName() + " currently needs a(n) " + orders.getType() + ". Would you like to schedule an appointment with the Radiology and Imaging department? (y/n)");
		String ans = scnr.nextLine();
		while (!(ans.equals("y") || ans.equals("n"))) {
			ans = scnr.nextLine();
		}

		while (ans.equals("y")) {
			
			System.out.println("Please enter the year you would like to schedule your appointment:");
			int year = scnr.nextInt();
			
			System.out.println("Please enter the month you would like to schedule your appointment (1-12):");
			int month = scnr.nextInt();
			
			System.out.println("Please enter the day you would like to schedule your appointment (1-31):");
			int day = scnr.nextInt();
			
			System.out.println("Please enter the hour you would like your appointment to start (0-23). It will end two hours later:");
			int hour = scnr.nextInt();

			LocalDateTime start = LocalDateTime.of(year, month, day, hour, 0);
			LocalDateTime end = LocalDateTime.of(year, month, day, hour + 2, 0);

			boolean booked = radioImageDept.scheduleAppt(patient, orders, start, end);

			if (booked) {
				
				System.out.println("Great! Your appointment has been booked successfully.");
				break;
				
			} else {
				
				System.out.println("Sorry, there are no available machines at that time. Would you like to try to book another time? (y/n)");
				ans = scnr.nextLine();
				while (!(ans.equals("y") || ans.equals("n"))) {
					ans = scnr.nextLine();
				}
				
			}
		}

    	
    }
    
}
