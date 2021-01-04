package org.bahmni.module.PDFGenerator.prescription;

import org.openmrs.Provider;

public class Doctor extends Provider {
    private String name;
    private String contactInformation;

    public Doctor(String name, String contactInformation) {
        this.name = name;
        this.contactInformation = contactInformation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}
