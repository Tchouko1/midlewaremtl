package com.midleware.service;

import FIPA.DateTime;
import com.midleware.dao.PolluantDAOImpl;
import com.midleware.polluant.PM;
import com.midleware.polluant.Polluant;
import com.midleware.polluant.SO2;
import com.midleware.utils.MidleWareUtils;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PolluantService {

    private static final int TEN_MINUTES = 10 * 60 * 1000;


    public List<Polluant> getAllPolluantByStationIDAndDate(String stationID, String date){

        List<Polluant> polluants = new PolluantDAOImpl().getAllPolluantByStationIDAndDate(stationID, date);

        List<Polluant> lPolluantTMP = new ArrayList<>(polluants);

        List<SO2> so2s = new PolluantDAOImpl().getAllSO2ByStationIDAndDate(stationID, date);



        for (Polluant polluant : polluants) {

            String[] hour = polluant.getDate().split("\\s");

            if (!hour[1].equalsIgnoreCase("24:00")) {
                LocalTime localTime = LocalTime.parse(hour[1]);

                LocalTime minusMinute = localTime.plusMinutes(0);

                LocalTime minusHour = localTime.plusHours(0);
                //-
                List<SO2> so2List = new ArrayList<>(0);

                for (int i = 0; i <= 9; i++) {

                    for (SO2 so2 : so2s) {
                        if (so2.getHour().equalsIgnoreCase(minusMinute.toString())) {

                            so2List.add(so2);
                            minusMinute = minusMinute.minusMinutes(1);

                            break;

                        }

                    }

                }
                polluant.setSo2(so2List);


                List<PM> pmList = new ArrayList<>(0);
                for (int i = 0; i <= 2; i++) {

                    for (Polluant polluantPM : lPolluantTMP) {
                        if (polluantPM.getHour().equalsIgnoreCase(minusHour.toString())) {

                            PM lPM = new PM(polluantPM.getPm2_5(), polluantPM.getDate());
                            pmList.add(lPM);
                            minusHour = minusHour.minusHours(1);

                            break;

                        }

                    }

                }

                polluant.setPm(pmList);


            }

        }




        return polluants;


    }


    public Double getIQA(Object[] polluants){
        return 0.d;
    }
}
