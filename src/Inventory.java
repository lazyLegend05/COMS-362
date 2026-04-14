import java.io.*;
import java.util.ArrayList;

public class Inventory {
    private String fileName = "inventory.txt";

    public boolean checkAvailability(String medicineName, int requiredQty) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int qty = Integer.parseInt(parts[1]);

                if (name.equalsIgnoreCase(medicineName)) {
                    return qty >= requiredQty;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
        }
        return false;
    }

    public void updateStock(String medicineName, int quantityUsed) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int qty = Integer.parseInt(parts[1]);

                if (name.equalsIgnoreCase(medicineName)) {
                    qty -= quantityUsed;
                    line = name + "," + qty;
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error updating inventory.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing updated inventory.");
        }
    }
}
