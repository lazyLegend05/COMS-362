public class ICUNurse {
    private String name;
    private String staffID;

    public ICUNurse(String name, String staffID) {
        this.name = name;
        this.staffID = staffID;
    }

    public void admitPatient(Patient p, ICURecord rec, FileHandler fh) {
        fh.writeRecord(rec.toFileString(p));
        fh.confirmAdmission(rec.getPatientID());
        System.out.println("Admitted by: " + name);
    }
}
