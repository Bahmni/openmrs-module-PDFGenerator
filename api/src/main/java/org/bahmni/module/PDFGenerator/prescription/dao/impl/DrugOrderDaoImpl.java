package org.bahmni.module.PDFGenerator.prescription.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.bahmni.module.PDFGenerator.prescription.dao.DrugOrderDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.openmrs.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class DrugOrderDaoImpl implements DrugOrderDao {

    private SessionFactory sessionFactory;

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    public DrugOrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<DrugOrder> getPrescribedDrugOrders(List<String> visitUuids) {

        if (visitUuids != null && visitUuids.size() != 0) {
            Session currentSession = getCurrentSession();
            Query query = currentSession.createQuery("select d1 from DrugOrder d1, Encounter e, Visit v where d1.encounter = e and e.visit = v and v.uuid in (:visitUuids) " +
                    "and d1.voided = false and d1.action != :discontinued and " +
                    "not exists (select d2 from DrugOrder d2 where d2.voided = false and d2.action = :revised and d2.encounter = d1.encounter and d2.previousOrder = d1)" +
                    "order by d1.dateActivated desc");
            query.setParameterList("visitUuids", visitUuids);
            query.setParameter("discontinued", Order.Action.DISCONTINUE);
            query.setParameter("revised", Order.Action.REVISE);
            log.warn("Coming in Dao");
            return (List<DrugOrder>) query.list();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Order> getActiveOrders(Patient patient, OrderType orderType, CareSetting careSetting, Date asOfDate,
                                       Set<Concept> conceptsToFilter, Set<Concept> conceptsToExclude, Date startDate, Date endDate, Collection<Encounter> encounters) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient is required when fetching active orders");
        }
        if (asOfDate == null) {
            asOfDate = new Date();
        }
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Order.class);
        criteria.add(Restrictions.eq("patient", patient));
        if (CollectionUtils.isNotEmpty(encounters)) {
            criteria.add(Restrictions.in("encounter", encounters));
        }
        if (careSetting != null) {
            criteria.add(Restrictions.eq("careSetting", careSetting));
        }

        if (CollectionUtils.isNotEmpty(conceptsToFilter)) {
            criteria.add(Restrictions.in("concept", conceptsToFilter));
        }
        if (CollectionUtils.isNotEmpty(conceptsToExclude)) {
            criteria.add(Restrictions.not(Restrictions.in("concept", conceptsToExclude)));
        }
        criteria.add(Restrictions.eq("orderType", orderType));
        criteria.add(Restrictions.le("dateActivated", asOfDate));
        criteria.add(Restrictions.eq("voided", false));
        criteria.add(Restrictions.ne("action", Order.Action.DISCONTINUE));
        if (startDate != null) {
            criteria.add(Restrictions.or(Restrictions.ge("scheduledDate", startDate), Restrictions.ge("autoExpireDate", startDate)));
            if (endDate == null) {
                endDate = new Date();
            }
            criteria.add(Restrictions.le("scheduledDate", endDate));
        }

        Disjunction dateStoppedAndAutoExpDateDisjunction = Restrictions.disjunction();
        Criterion stopAndAutoExpDateAreBothNull = Restrictions.and(Restrictions.isNull("dateStopped"), Restrictions
                .isNull("autoExpireDate"));
        dateStoppedAndAutoExpDateDisjunction.add(stopAndAutoExpDateAreBothNull);
        Criterion autoExpireDateEqualToOrAfterAsOfDate = Restrictions.and(Restrictions.isNull("dateStopped"), Restrictions.ge("autoExpireDate", asOfDate));

        dateStoppedAndAutoExpDateDisjunction.add(autoExpireDateEqualToOrAfterAsOfDate);

        dateStoppedAndAutoExpDateDisjunction.add(Restrictions.ge("dateStopped", asOfDate));

        criteria.add(dateStoppedAndAutoExpDateDisjunction);

        return criteria.list();
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
