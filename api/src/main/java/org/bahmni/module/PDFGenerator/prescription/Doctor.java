package org.bahmni.module.PDFGenerator.prescription;

public class Doctor {
    private String name;
    private String contactInformation;
    private String uuid;

    public Doctor(String name, String contactInformation, String uuid) {
        this.name = name;
        this.contactInformation = contactInformation;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
