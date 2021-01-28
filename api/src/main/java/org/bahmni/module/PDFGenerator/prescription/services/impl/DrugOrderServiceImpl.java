package org.bahmni.module.PDFGenerator.prescription.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bahmni.module.PDFGenerator.prescription.dao.DrugOrderDao;
import org.bahmni.module.PDFGenerator.prescription.services.DrugOrderService;
import org.openmrs.*;
import org.openmrs.api.OrderService;
import org.openmrs.api.PatientService;
import org.openmrs.module.emrapi.utils.HibernateLazyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DrugOrderServiceImpl implements DrugOrderService {

    @Autowired
    DrugOrderDao drugOrderDao;

    @Autowired
    @Qualifier("patientService")
    PatientService openmrsPatientService;

    @Autowired
    OrderService orderService;

    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public List<DrugOrder> getPrescribedDrugOrders(List<String> visitUuids) {
        log.warn("Coming in Service");
        return drugOrderDao.getPrescribedDrugOrders(visitUuids);
    }

    @Override
    public List<DrugOrder> getActiveDrugOrders(String patientUuid, Date startDate, Date endDate) {
        return getActiveDrugOrders(patientUuid, new Date(), null, null, startDate, endDate, null);
    }

    private List<DrugOrder> getActiveDrugOrders(String patientUuid, Date asOfDate, Set<Concept> conceptsToFilter,
                                                Set<Concept> conceptsToExclude, Date startDate, Date endDate, Collection<Encounter> encounters) {
        Patient patient = getPatient(patientUuid);
        CareSetting careSettingByName = orderService.getCareSettingByName(CareSetting.CareSettingType.OUTPATIENT.toString());
        List<Order> orders = drugOrderDao.getActiveOrders(patient, orderService.getOrderTypeByName("Drug order"),
                careSettingByName, asOfDate, conceptsToFilter, conceptsToExclude, startDate, endDate, encounters);
        return mapOrderToDrugOrder(orders);
    }

    private List<DrugOrder> mapOrderToDrugOrder(List<Order> orders){
        HibernateLazyLoader hibernateLazyLoader = new HibernateLazyLoader();
        List<DrugOrder> drugOrders = new ArrayList<>();
        for(Order order: orders){
            order = hibernateLazyLoader.load(order);
            if(order instanceof DrugOrder) {
                drugOrders.add((DrugOrder) order);
            }
        }
        return drugOrders;
    }

    @Override
    public Patient getPatient(String patientUuid) {
        return openmrsPatientService.getPatientByUuid(patientUuid);
    }
}
