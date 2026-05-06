import java.util.Scanner;

public class PharmacyMenu {
    public static void run(Scanner sc) {
        boolean running = true;
        Pharmacist pharmacist = new Pharmacist("Aadi", "P001");

        while (running) {
            System.out.println("\n=== Pharmacy Department ===");
            System.out.println("1. Dispense Medication");
            System.out.println("2. Restock Medicine");
            System.out.println("3. Add New Medicine");
            System.out.println("4. Process Prescription Order");
            System.out.println("5. Exit Pharmacy Department");
            System.out.print("Choose pharmacy operation: ");

            int pharmacyChoice = readPositiveInt(sc);

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

                case 3:
                    System.out.print("Enter new medicine name: ");
                    String newMedicineName = readNonEmptyString(sc);

                    System.out.print("Enter starting quantity: ");
                    int newMedicineQty = readPositiveInt(sc);

                    pharmacist.addNewMedicine(newMedicineName, newMedicineQty);
                    break;

                case 4:
                    pharmacist.processPrescriptionOrder(sc);
                    break;

                case 5:
                    running = false;
                    System.out.println("Exiting Pharmacy Department.");
                    break;

                default:
                    System.out.println("Invalid pharmacy option.");
            }
        }
    }

    private static int readPositiveInt(Scanner sc) {
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

    private static String readNonEmptyString(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.print("Input cannot be empty. Please try again: ");
        }
    }
}
