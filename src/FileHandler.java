import java.io.*;

public class FileHandler {
    private String fileName;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public void writeRecord(String record) {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(record);
            bw.newLine();
            System.out.println("Record saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving record.");
        }
    }

    public void readRecords() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No records found.");
        }
    }

    public void confirmAdmission(String patientID) {
        System.out.println("Patient admitted successfully! ID: " + patientID);
    }
}