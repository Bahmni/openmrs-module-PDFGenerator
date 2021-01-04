package org.bahmni.module.PDFGenerator.prescription;

public class Medicine {
    private String name;
    private String dose;
    private String frequent;
    private String duration;
    private String quantity;
    private String additionalInformation;

    public Medicine(String name, String dose, String frequent, String duration, String quantity, String additionalInformation) {
        this.name = name;
        this.dose = dose;
        this.frequent = frequent;
        this.duration = duration;
        this.quantity = quantity;
        this.additionalInformation = additionalInformation;
    }
}
