package com.midleware.search;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class SearchContainer {

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();

            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            profile.setParameter(Profile.CONTAINER_NAME, "SearchContainer");

            ContainerController container = runtime.createAgentContainer(profile);
            try {
                AgentController ag = container.createNewAgent("searchAgent", SearchAgent.class.getName(), new Object[] {});
                ag.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
