package com.midleware.service;

import FIPA.DateTime;
import com.midleware.dao.PolluantDAOImpl;
import com.midleware.polluant.IQA;
import com.midleware.polluant.PM;
import com.midleware.polluant.Polluant;
import com.midleware.polluant.SO2;
import com.midleware.utils.MidleWareUtils;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

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

        /*List<Integer> listSIO3 = new ArrayList<>();
        List<Integer> listNo2 = new ArrayList<>();
        List<Integer> listCO = new ArrayList<>();
        List<Integer> listPM2 = new ArrayList<>();
        List<Integer> listSO2 = new ArrayList<>();*/
        List<IQA> listValuePolluant = new ArrayList<>();
        int normeO3 = 82;
        int normeNO2 = 213;
        int normeCO = 30;
        int normePM2 = 35;
        int normeSO2 = 190;
        for(Object obj : polluants){

            Polluant p = (Polluant) obj;

            //- calcul sous indice o3
            int sIO3 = (int)((Double.parseDouble(p.getO3())/normeO3) * 50) + 1;
            listValuePolluant.add(new IQA("O3", sIO3));

            //- calcul sous indice no2
            int sINO2 = (int)((Double.parseDouble(p.getNo2())/normeNO2) * 50) + 1;
            listValuePolluant.add(new IQA("NO2", sINO2));


            int sICO = 0;
            //- calcul sous indice no2
            if(!p.getCo().isEmpty()) {
                sICO = (int) ((Double.parseDouble(p.getCo()) / normeCO) * 50) + 1;
                listValuePolluant.add(new IQA("CO", sICO));
            }

            //-- calcul sous indice PM2
            List<PM> pmList = p.getPm();
            double moyPm = 0.d;
            for(PM pm : pmList){
                moyPm += Double.parseDouble(pm.getPm());
            }
             moyPm = moyPm/3;

            int sIPM = (int)((moyPm/normePM2) * 50) + 1;
            listValuePolluant.add(new IQA("PM", sIPM));

            //-- calcul sous indice SO2
            List<SO2> so2List = p.getSo2();
            double moySO2 = 0.d;
            for(SO2 so2 : so2List){
                moySO2 += Double.parseDouble(so2.getSo2());
            }
            moySO2 = moySO2/10;
            int sISO2 = (int)((moySO2/normeSO2) * 50) + 1;
            listValuePolluant.add(new IQA("SO2", sIO3));







            System.out.println("***************************************************************");
            System.out.println("Date: "+p.getDate()+" La valeur du sous-indice de o3 est "+ sIO3);
            System.out.println("Date: "+p.getDate()+" La valeur du sous-indice de NO2 est "+ sINO2);
            System.out.println("Date: "+p.getDate()+" La valeur du sous-indice de CO est "+ sICO);
            System.out.println("Date: "+p.getDate()+" La valeur du sous-indice de PM est "+ sIPM);
            System.out.println("Date: "+p.getDate()+" La valeur du sous-indice de SO2 est "+ sISO2);





        }
        System.out.println("***************************************************************");

        IQA iqa = listValuePolluant.stream().max(Comparator.comparing(v -> v.getValue())).get();

        System.out.println("Le polluant "+ iqa.getPolluant() + " La valeur: "+iqa.getValue());

        return 0.d;
    }

}
