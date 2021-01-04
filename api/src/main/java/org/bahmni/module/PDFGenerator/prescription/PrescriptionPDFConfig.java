package org.bahmni.module.PDFGenerator.prescription;

import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component("prescriptionPDFConfig")
public class PrescriptionPDFConfig {

    private Properties properties;

    public Properties getProperties() throws IOException {
        if(properties == null) {
            properties = new Properties();
            loadProperties();
        }
        return properties;
    }

    private void loadProperties() throws IOException {
        String resourceName = "prescription-template.properties";
        final File file = new File(OpenmrsUtil.getApplicationDataDirectory(), resourceName);
        final InputStream inputStream;
        if (file.exists()) {
            inputStream = new FileInputStream(file);
        } else {
            inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
            if (inputStream == null)
                throw new IOException();
        }
        properties.load(inputStream);
    }
}
