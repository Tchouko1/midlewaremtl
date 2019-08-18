package com.midleware.address;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;


public class AddressAgent extends Agent {

    @Override
    protected void setup() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID("SearchAgent",AID.ISLOCALNAME));
        message.setContent("Hello the world....");
        send(message);

    }
}
