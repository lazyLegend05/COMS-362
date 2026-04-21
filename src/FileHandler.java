import java.io.*;
import java.util.*;

public class FileHandler {
    private String fileName;

    public FileHandler(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
        this.fileName = fileName.trim();
    }

    public boolean writeRecord(String record) {
        if (record == null || record.trim().isEmpty()) {
            System.out.println("Cannot save an empty record.");
            return false;
        }

        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(record);
            bw.newLine();
            System.out.println("Record saved successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("Error saving record.");
            return false;
        }
    }

    public void readRecords() {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No records found. File does not exist.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean hasRecords = false;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    System.out.println(line);
                    hasRecords = true;
                }
            }

            if (!hasRecords) {
                System.out.println("No records found. File is empty.");
            }
        } catch (IOException e) {
            System.out.println("Error reading records.");
        }
    }

    public String findRecord(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    return line;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return null;
    }

    public void updateRecord(String id, String updatedLine) {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    lines.add(updatedLine);
                    found = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }
        if (!found) {
            System.out.println("Record not found for update.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    public void confirmAdmission(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            System.out.println("Invalid patient ID.");
            return;
        }
        System.out.println("Patient admitted successfully! ID: " + patientID);
    }

    public void confirmAssignment() {
        System.out.println("Nurse assigned to patient successfully.");
    }

    public String getFileName() {
        return fileName;
    }
}
