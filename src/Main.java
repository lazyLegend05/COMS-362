import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter patient name: ");
        String patientName = sc.nextLine();

        System.out.print("Enter medicine name: ");
        String medicineName = sc.nextLine();

        System.out.print("Enter quantity prescribed: ");
        int quantity = Integer.parseInt(sc.nextLine());

        Patient patient = new Patient(patientName);
        Pharmacist pharmacist = new Pharmacist("Aadi", "P001");

        pharmacist.dispenseMedication(patient, medicineName, quantity);
    }
}
