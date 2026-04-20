import java.util.Scanner;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Hospital Management System ===");
        System.out.println("1. Pharmacy Department");
        System.out.println("2. Emergency Care Department");
        System.out.println("3. Laboratory Department");
        System.out.println("4. Radiology / Imaging Department");
        System.out.println("5. ICU Department");
        System.out.print("Choose department: ");

        int choice = readPositiveInt(sc);

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
            case 5:
                runICU(sc);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        sc.close();
    }

    public static void runPharmacy(Scanner sc) {
        System.out.println("\n=== Pharmacy Department ===");
        System.out.println("1. Dispense Medication");
        System.out.println("2. Restock Medicine");
        System.out.print("Choose pharmacy operation: ");

        int pharmacyChoice = readPositiveInt(sc);
        Pharmacist pharmacist = new Pharmacist("Aadi", "P001");

        switch (pharmacyChoice) {
            case 1:
                System.out.print("Enter patient name: ");
                String patientName = readNonEmptyString(sc);

                System.out.print("Enter medicine name: ");
                String medicineName = readNonEmptyString(sc);

                System.out.print("Enter quantity prescribed: ");
                int quantity = readPositiveInt(sc);

                Patient patient = new Patient(patientName);
                pharmacist.dispenseMedication(patient, medicineName, quantity);
                break;

            case 2:
                System.out.print("Enter medicine name to restock: ");
                String restockMedicine = readNonEmptyString(sc);

                System.out.print("Enter quantity to add: ");
                int restockQty = readPositiveInt(sc);

                pharmacist.restockMedicine(restockMedicine, restockQty);
                break;

            default:
                System.out.println("Invalid pharmacy option.");
        }
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
        String name = readNonEmptyString(sc);

        int age;
        while (true) {
            System.out.print("Enter patient age: ");
            age = readPositiveInt(sc);
            if (age > 0) {
                break;
            }
        }

        System.out.print("Enter contact number: ");
        String contactNum = readNonEmptyString(sc);

        System.out.print("Enter chief complaint (reason for visit): ");
        String complaint = readNonEmptyString(sc);

        Patient patient = new Patient(name, age, contactNum);
        EmergencyRecord record = new EmergencyRecord(complaint, infoSource);

        fileHandler.writeRecord(record.toFileString(patient));

        System.out.println("Registration confirmed.");
        System.out.println("Generated Patient ID: " + record.getPatientID());
    }

    public static void runLab(Scanner sc) {
        System.out.println("\n=== Laboratory Department ===");

        FileHandler fileHandler = new FileHandler("lab.txt");

        System.out.print("Enter patient name: ");
        String name = readNonEmptyString(sc);

        int age;
        while (true) {
            System.out.print("Enter patient age: ");
            age = readPositiveInt(sc);
            if (age > 0) {
                break;
            }
        }

        System.out.print("Enter Phone Number: ");
        String contact = readNonEmptyString(sc);

        System.out.print("Enter Test Type: ");
        String test = readNonEmptyString(sc);

        Patient patient = new Patient(name, age, contact);
        LabStaff labStaff = new LabStaff("Billy", "LS001", "Lab Tech");

        labStaff.registerLabTest(patient, test, fileHandler);

        System.out.println("Lab test request complete");
    }

    public static void runRadiology(Scanner scnr) {
        System.out.println("\n=== Radiology Department ===");

        System.out.print("Enter patient name: ");
        String patientName = readNonEmptyString(scnr);

        Patient patient = new Patient(patientName);
        Doctor testDoctor = new Doctor("Dr. Ex", "ID123");

        Radiology radioImageDept = new Radiology();
        Machine testMRI = new Machine("coolbrand98345", "MRI");
        radioImageDept.addMachine(testMRI);
        radioImageDept.addDoctor(testDoctor);

        MachineOrders orders = testDoctor.makeMachineOrders(patient, "MRI");

        Patient testPatient = new Patient("Bobbo");
        MachineOrders testOrders = testDoctor.makeMachineOrders(testPatient, "MRI");
        radioImageDept.scheduleAppt(
                testPatient,
                testOrders,
                LocalDateTime.of(2026, 1, 1, 1, 0),
                LocalDateTime.of(2026, 1, 1, 3, 0)
        );

        System.out.println(patient.getName() + " currently needs a(n) " + orders.getType()
                + ". Would you like to schedule an appointment with the Radiology and Imaging department? (y/n)");
        String ans = scnr.nextLine().trim().toLowerCase();

        while (!(ans.equals("y") || ans.equals("n"))) {
            System.out.print("Please enter y or n: ");
            ans = scnr.nextLine().trim().toLowerCase();
        }

        while (ans.equals("y")) {
            System.out.println("Please enter the year you would like to schedule your appointment:");
            int year = readPositiveInt(scnr);

            System.out.println("Please enter the month you would like to schedule your appointment (1-12):");
            int month = readPositiveInt(scnr);

            System.out.println("Please enter the day you would like to schedule your appointment (1-31):");
            int day = readPositiveInt(scnr);

            System.out.println("Please enter the hour you would like your appointment to start (0-23). It will end two hours later:");
            int hour = readPositiveInt(scnr);

            LocalDateTime start = LocalDateTime.of(year, month, day, hour, 0);
            LocalDateTime end = LocalDateTime.of(year, month, day, hour + 2, 0);

            boolean booked = radioImageDept.scheduleAppt(patient, orders, start, end);

            if (booked) {
                System.out.println("Great! Your appointment has been booked successfully.");
                break;
            } else {
                System.out.println("Sorry, there are no available machines at that time. Would you like to try to book another time? (y/n)");
                ans = scnr.nextLine().trim().toLowerCase();
                while (!(ans.equals("y") || ans.equals("n"))) {
                    System.out.print("Please enter y or n: ");
                    ans = scnr.nextLine().trim().toLowerCase();
                }
            }
        }
    }

    public static void runICU(Scanner sc) {
        System.out.println("\n=== ICU Department ===");

        System.out.print("Enter nurse username: ");
        String username = readNonEmptyString(sc);

        System.out.print("Enter staffID: ");
        String staffID = readNonEmptyString(sc);

        ICUNurse nurse = new ICUNurse(username, staffID);

        System.out.print("Enter patient name: ");
        String name = readNonEmptyString(sc);

        System.out.print("Enter age: ");
        int age = readPositiveInt(sc);

        System.out.print("Enter phone number: ");
        String phone = readNonEmptyString(sc);

        System.out.print("Enter department transferred from: ");
        String transferredFrom = readNonEmptyString(sc);

        System.out.print("Enter reason for ICU admission: ");
        String reason = readNonEmptyString(sc);

        System.out.print("Enter room/bed number: ");
        String room = readNonEmptyString(sc);

        ICURecord record = new ICURecord(transferredFrom, reason);
        record.assignBed(room);

        Patient patient = new Patient(name, age, phone);
        FileHandler fh = new FileHandler("icuPatients.txt");

        nurse.admitPatient(patient, record, fh);
    }

    public static int readPositiveInt(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                } else {
                    System.out.print("Please enter a number greater than 0: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a valid number: ");
            }
        }
    }

    public static String readNonEmptyString(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.print("Input cannot be empty. Please try again: ");
        }
    }
}