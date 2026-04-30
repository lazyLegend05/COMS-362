public class DischargeRecord {
    private String dischargeID;
    private String patientID;
    private String dischargeDate;
    private String dischargeTime;
    private String doctorName;
    private String prescription;

    public DischargeRecord(String patientID, String doctorName, String prescription) {
        this.dischargeID = "DC" + System.currentTimeMillis();
        this.patientID = patientID;
        this.dischargeDate = java.time.LocalDate.now().toString();
        this.dischargeTime = java.time.LocalTime.now().toString();
        this.doctorName = doctorName;
        this.prescription = prescription;
    }

    public String getDischargeID() { return dischargeID; }

    public String toFileString(Patient p) {
        return dischargeID + "," + patientID + "," + p.getName()
                + "," + p.getAge() + "," + dischargeDate + "," + dischargeTime
                + "," + doctorName + "," + prescription;
    }
}