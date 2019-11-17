package com.midleware.address;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


public class AddressAgent extends Agent {

    @Override
    protected void setup() {
        //ACLMessage message = new ACLMessage(ACLMessage.INFORM);
       // message.addReceiver(new AID("InterfaceAgent",AID.ISLOCALNAME));
        //message.setContent("Hello the world....");
       // send(message);
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("address");
        serviceDescription.setName("search-address");
        agentDescription.addServices(serviceDescription);

        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException fe){
            fe.printStackTrace();
        }

    }
}
