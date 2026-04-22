import java.io.*;
import java.util.ArrayList;

public class Inventory {
    private String fileName = "src/inventory.txt";

    public boolean checkAvailability(String medicineName, int requiredQty) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                int qty = Integer.parseInt(parts[1].trim());

                if (name.equalsIgnoreCase(medicineName)) {
                    return qty >= requiredQty;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in inventory file.");
        }
        return false;
    }

    public void updateStock(String medicineName, int quantityUsed) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String medName = parts[0].trim();
                int stock = Integer.parseInt(parts[1].trim());

                if (medName.equalsIgnoreCase(medicineName)) {
                    stock -= quantityUsed;
                    if (stock < 0) {
                        stock = 0;
                    }
                    line = medName + "," + stock;
                }

                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error updating inventory.");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in inventory file.");
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
