package com.midleware.dao;

import com.midleware.address.AddressStation;
import com.midleware.polluant.Polluant;
import com.midleware.polluant.SO2;

import java.util.List;

public interface PolluantDAO {
    List<Polluant> getAllPolluantByStationIDAndDate (String stationID, String date);

    List<SO2> getAllSO2ByStationIDAndDate (String stationID, String date);

}
