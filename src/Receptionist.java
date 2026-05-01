public class Receptionist {
    private String name;
    private String employeeID;

    public Receptionist(String name, String employeeID) {
        this.name = name;
        this.employeeID = employeeID;
    }

    public void registerEmergencyPatient(Patient patient, String complaint, boolean isUnconscious) {
        String infoSource;

        if (isUnconscious) {
            infoSource = "Companion";
            System.out.println("Patient is unconscious. Companion provides information.");
        } else {
            infoSource = "Patient";
        }

        EmergencyRecord record = new EmergencyRecord(complaint, infoSource);
        FileHandler fileHandler = new FileHandler("emergency_records.txt");
        fileHandler.writeRecord(record.toFileString(patient));

        System.out.println("Registration confirmed.");
        System.out.println("Generated Patient ID: " + record.getPatientID());
    }

    public void dischargePatient(String patientID, java.util.Scanner sc) {
        FileHandler fh = new FileHandler("emergency_records.txt");
        BedManager bedManager = new BedManager();

        Patient patient = fh.findPatient(patientID);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Patient found: " + patient.getName()
                + ", Age: " + patient.getAge());

        EmergencyDoctor doctor = null;
        String doctorName = "";
        while (doctor == null) {
            System.out.print("\nEnter attending doctor's name: ");
            doctorName = sc.nextLine().trim();
            System.out.print("Enter doctor ID: ");
            String doctorID = sc.nextLine().trim();

            doctor = EmergencyDoctor.validate(doctorID, doctorName);
            if (doctor == null) {
                System.out.println("Doctor not found. Please check the name and ID and try again.");
            }
        }

        boolean cleared = doctor.inspectPatient(patient, sc);
        if (!cleared) {
            System.out.println("Discharge cancelled. Patient requires further treatment.");
            return;
        }

        String prescription = doctor.prescribeMedicine(sc);

        System.out.print("\nConfirm discharge? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        while (!confirm.equals("yes") && !confirm.equals("no")) {
            System.out.print("Please enter yes or no: ");
            confirm = sc.nextLine().trim().toLowerCase();
        }

        if (!confirm.equals("yes")) {
            System.out.println("Discharge cancelled.");
            return;
        }

        DischargeRecord record = new DischargeRecord(patientID, doctorName, prescription);
        FileHandler dischargeFile = new FileHandler("discharge_records.txt");
        dischargeFile.writeRecord(record.toFileString(patient));

        boolean bedFreed = bedManager.freeBed(patientID);
        if (bedFreed) {
            System.out.println("Bed successfully freed.");
        } else {
            System.out.println("No bed was assigned to this patient.");
        }

        System.out.println("\nPatient " + patient.getName() + " discharged successfully!");
        System.out.println("Discharge ID: " + record.getDischargeID());
        System.out.println("Discharged by Dr. " + doctorName);
        System.out.println("Prescription: " + prescription);
    }

    public String getName() { return name; }
    public String getEmployeeID() { return employeeID; }
}