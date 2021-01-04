package org.bahmni.module.PDFGenerator.prescription;

public class Medicine {
    private String name;
    private String dose;
    private String unit;
    private String frequent;
    private String duration;
    private String quantity;
    private String additionalInformation;

    public Medicine(String name, String dose, String unit, String frequent, String duration, String quantity, String additionalInformation) {
        this.name = name;
        this.dose = dose;
        this.unit = unit;
        this.frequent = frequent;
        this.duration = duration;
        this.quantity = quantity;
        this.additionalInformation = additionalInformation;
    }

    @Override
    public String toString() {
        return this.name + "<!>" + this.dose + "<!>" + this.unit + "<!>" + this.frequent + "<!>" + this.duration + "<!>" + this.quantity + "<!>" + this.additionalInformation;
    }
}
