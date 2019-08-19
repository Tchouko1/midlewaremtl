package com.midleware;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;

public class MainContainer {
    public static void main(String[] args) {

        try {
            Runtime runtime = Runtime.instance();

            // Show Jade ui com.midleware.controller
            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "true");
            Profile profileImpl = new ProfileImpl(properties);

            // Create the main container
            AgentContainer mainContainer = runtime.createMainContainer(profileImpl);

            // Start the main container
            mainContainer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
