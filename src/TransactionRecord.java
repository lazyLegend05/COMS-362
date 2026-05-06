public class TransactionRecord {
    private String transactionID;
    private String prescriptionID;
    private String action;
    private String timestamp;

    public TransactionRecord(String prescriptionID, String action) {
        this.transactionID = generateID();
        this.prescriptionID = clean(prescriptionID);
        this.action = clean(action);
        this.timestamp = java.time.LocalDateTime.now().withNano(0).toString();
    }

    private String generateID() {
        return "TRX" + System.currentTimeMillis();
    }

    private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().replace(",", " ");
    }

    public String toFileString() {
        return transactionID + "," + prescriptionID + "," + action + "," + timestamp;
    }
}
