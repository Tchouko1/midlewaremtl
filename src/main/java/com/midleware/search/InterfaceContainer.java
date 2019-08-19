package com.midleware.search;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class InterfaceContainer {

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();

            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            profile.setParameter(Profile.CONTAINER_NAME, "InterfaceContainer");

            ContainerController container = runtime.createAgentContainer(profile);
            try {
                AgentController ag = container.createNewAgent("interfaceAgent", InterfaceAgent.class.getName(), new Object[] {});
                ag.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
