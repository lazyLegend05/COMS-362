import java.io.*;

public class EmergencyDoctor {
    private String name;
    private String doctorID;

    public EmergencyDoctor(String name, String doctorID) {
        this.name = name;
        this.doctorID = doctorID;
    }

    public String getName() { return name; }
    public String getDoctorID() { return doctorID; }

    // checks the file and returns an EmergencyDoctor if found, null if not
    public static EmergencyDoctor validate(String doctorID, String name) {
        String filePath = "/Users/tmagikar/Desktop/COMS 3620/COMS-362/src/emergencyDoctors.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 2) continue;
                if (parts[0].trim().equalsIgnoreCase(doctorID.trim()) &&
                        parts[1].trim().equalsIgnoreCase(name.trim())) {
                    return new EmergencyDoctor(parts[1].trim(), parts[0].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading doctors file.");
        }
        return null;
    }

    public boolean inspectPatient(Patient patient, java.util.Scanner sc) {
        System.out.println("\nDr. " + name + " is inspecting " + patient.getName() + "...");
        System.out.print("Enter doctor's notes on patient condition: ");
        String notes = sc.nextLine().trim();

        System.out.print("Is patient ready to be discharged? (yes/no): ");
        String ready = sc.nextLine().trim().toLowerCase();
        while (!ready.equals("yes") && !ready.equals("no")) {
            System.out.print("Please enter yes or no: ");
            ready = sc.nextLine().trim().toLowerCase();
        }

        if (!ready.equals("yes")) {
            System.out.println("Dr. " + name + " has determined patient is not ready for discharge.");
            return false;
        }

        System.out.println("Dr. " + name + " has cleared " + patient.getName() + " for discharge.");
        return true;
    }

    public String prescribeMedicine(java.util.Scanner sc) {
        System.out.print("Enter discharge medication prescribed by Dr. " + name + ": ");
        String medicine = sc.nextLine().trim();
        System.out.print("Enter dosage instructions: ");
        String dosage = sc.nextLine().trim();
        System.out.println("Prescription recorded: " + medicine + " - " + dosage);
        return medicine + " (" + dosage + ")";
    }
}