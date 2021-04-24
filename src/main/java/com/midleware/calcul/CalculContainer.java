package com.midleware.calcul;

import com.midleware.air.AirAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;


public class CalculContainer {

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();

            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            profile.setParameter(Profile.CONTAINER_NAME, "CalculContainer");

            ContainerController container = runtime.createAgentContainer(profile);
            try {
                AgentController ag = container.createNewAgent("calculAgent", CalculAgent.class.getName(), new Object[] {});
                ag.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
