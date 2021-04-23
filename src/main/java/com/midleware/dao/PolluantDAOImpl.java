package com.midleware.dao;

import com.midleware.address.AddressStation;
import com.midleware.polluant.Polluant;
import com.midleware.polluant.SO2;
import com.midleware.utils.SessionUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class PolluantDAOImpl implements PolluantDAO{


    @Override
    public List<Polluant> getAllPolluantByStationIDAndDate(String stationID, String date) {

        Session session = null;
        Transaction transaction = null;
        List<Polluant> polluantList = new ArrayList<>(0);
        try {

        session = SessionUtil.getSession();
        Criteria criteria = session.createCriteria(Polluant.class);
        criteria.add(Restrictions.like("date", "%"+date+"%"));
        criteria.add(Restrictions.eq("station_id", stationID));
        polluantList = criteria.list();

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();


        } finally {
            if (session != null) {
                session.close();
            }
        }


        return polluantList;
    }

    @Override
    public List<SO2> getAllSO2ByStationIDAndDate(String stationID, String date) {
        Session session = null;
        Transaction transaction = null;
        List<SO2> so2List = new ArrayList<>(0);
        try {

            session = SessionUtil.getSession();
            Criteria criteria = session.createCriteria(SO2.class);
            criteria.add(Restrictions.like("date", "%"+date+"%"));
            criteria.add(Restrictions.eq("station_id", stationID));
            so2List = criteria.list();

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();


        } finally {
            if (session != null) {
                session.close();
            }
        }


        return so2List;
    }
}
