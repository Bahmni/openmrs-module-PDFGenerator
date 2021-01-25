package org.bahmni.module.PDFGenerator.prescription.dao;

import org.openmrs.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface DrugOrderDao {

    List<DrugOrder> getPrescribedDrugOrders(List<String> visitUuids);

    List<Order> getActiveOrders(Patient patient, OrderType orderType, CareSetting careSetting, Date asOfDate, Set<Concept> conceptsToFilter,
                                Set<Concept> conceptsToExclude, Date startDate, Date endDate, Collection<Encounter> encounters);

}
