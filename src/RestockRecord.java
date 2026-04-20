public class RestockRecord {
    private String recordID;
    private String medicineName;
    private int quantityAdded;
    private String date;
    private String time;

    public RestockRecord(String medicineName, int quantityAdded) {
        this.recordID = generateID();
        this.medicineName = medicineName;
        this.quantityAdded = quantityAdded;
        this.date = java.time.LocalDate.now().toString();
        this.time = java.time.LocalTime.now().toString();
    }

    private String generateID() {
        return "RS" + System.currentTimeMillis();
    }

    public String toFileString() {
        return recordID + "," + medicineName + "," + quantityAdded + "," + date + "," + time;
    }

    public String getRecordID() {
        return recordID;
    }
}