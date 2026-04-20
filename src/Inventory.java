import java.io.*;
import java.util.ArrayList;

public class Inventory {
    private final String fileName = "src/inventory.txt";

    public boolean checkAvailability(String medicineName, int requiredQty) {
        if (!isValidMedicineName(medicineName)) {
            System.out.println("Invalid medicine name.");
            return false;
        }

        if (requiredQty <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) {
                    System.out.println("Skipping invalid inventory line: " + line);
                    continue;
                }

                String name = parts[0].trim();
                int qty;

                try {
                    qty = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid quantity in inventory line: " + line);
                    continue;
                }

                if (name.equalsIgnoreCase(medicineName.trim())) {
                    return qty >= requiredQty;
                }
            }

            System.out.println("Medicine not found in inventory.");
        } catch (FileNotFoundException e) {
            System.out.println("Inventory file not found.");
        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
        }

        return false;
    }

    public boolean updateStock(String medicineName, int quantityUsed) {
        if (!isValidMedicineName(medicineName)) {
            System.out.println("Invalid medicine name.");
            return false;
        }

        if (quantityUsed <= 0) {
            System.out.println("Quantity used must be greater than 0.");
            return false;
        }

        ArrayList<String> updatedLines = new ArrayList<>();
        boolean medicineFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) {
                    System.out.println("Skipping invalid inventory line: " + line);
                    continue;
                }

                String medName = parts[0].trim();
                int stock;

                try {
                    stock = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid quantity in inventory line: " + line);
                    continue;
                }

                if (medName.equalsIgnoreCase(medicineName.trim())) {
                    medicineFound = true;

                    if (stock < quantityUsed) {
                        System.out.println("Insufficient stock. Available: " + stock);
                        return false;
                    }

                    stock -= quantityUsed;
                    line = medName + "," + stock;
                }

                updatedLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Inventory file not found.");
            return false;
        } catch (IOException e) {
            System.out.println("Error updating inventory.");
            return false;
        }

        if (!medicineFound) {
            System.out.println("Medicine not found in inventory.");
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing updated inventory.");
            return false;
        }

        return true;
    }

    // Iteration 2 feature
    public boolean restockMedicine(String medicineName, int quantityToAdd) {
        if (!isValidMedicineName(medicineName)) {
            System.out.println("Invalid medicine name.");
            return false;
        }

        if (quantityToAdd <= 0) {
            System.out.println("Restock quantity must be greater than 0.");
            return false;
        }

        ArrayList<String> updatedLines = new ArrayList<>();
        boolean medicineFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) {
                    System.out.println("Skipping invalid inventory line: " + line);
                    continue;
                }

                String medName = parts[0].trim();
                int stock;

                try {
                    stock = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid quantity in inventory line: " + line);
                    continue;
                }

                if (medName.equalsIgnoreCase(medicineName.trim())) {
                    stock += quantityToAdd;
                    line = medName + "," + stock;
                    medicineFound = true;
                }

                updatedLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Inventory file not found.");
            return false;
        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
            return false;
        }

        // If medicine does not already exist, add it as a new entry
        if (!medicineFound) {
            updatedLines.add(medicineName.trim() + "," + quantityToAdd);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing updated inventory.");
            return false;
        }

        System.out.println("Medicine restocked successfully.");
        return true;
    }

    private boolean isValidMedicineName(String medicineName) {
        return medicineName != null && !medicineName.trim().isEmpty();
    }
}