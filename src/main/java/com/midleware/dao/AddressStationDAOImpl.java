package com.midleware.dao;

import com.midleware.address.AddressStation;
import org.hibernate.Query;
import org.hibernate.Session;
import com.midleware.utils.SessionUtil;

import java.util.List;

public class AddressStationDAOImpl implements AddressStationDAO {

    @Override
    public List<AddressStation> getAllAddress() {
        Session session = SessionUtil.getSession();
        Query query = session.createQuery("from Address_Station");
        List<AddressStation> addressStationList = query.list();
        return addressStationList;
    }
}
