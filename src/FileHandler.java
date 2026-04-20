import java.io.*;

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

    public void confirmAdmission(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            System.out.println("Invalid patient ID.");
            return;
        }
        System.out.println("Patient admitted successfully! ID: " + patientID);
    }

    public String getFileName() {
        return fileName;
    }
}