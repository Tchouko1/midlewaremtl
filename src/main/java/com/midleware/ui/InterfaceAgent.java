package com.midleware.ui;


import com.midleware.address.Address;
import com.midleware.dao.AddressStationDAOImpl;
import com.midleware.dao.PolluantDAO;
import com.midleware.dao.PolluantDAOImpl;
import com.midleware.service.PolluantService;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

import static com.midleware.utils.MidleWareUtils.SEARCH_POLLUANT;

public class InterfaceAgent extends GuiAgent {

    InterfaceAgentGUI searchAgentGUI;
    @Override
    protected void setup() {
        searchAgentGUI = new InterfaceAgentGUI();
        searchAgentGUI.setInterfaceAgent(this);
        System.out.println("Demmarrage de l'agent d'interface");

        // ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        // addBehaviour(parallelBehaviour);
        addBehaviour(new CyclicBehaviour() {

            @Override
            public void action() {
                ACLMessage message = receive();
                if(message != null){

                    switch (message.getPerformative()){
                        case ACLMessage.INFORM:



                            searchAgentGUI.showMessage("Sender: " +message.getSender().getName(), true);
                            searchAgentGUI.showMessage("Act de message : " +ACLMessage.getPerformative(message.getPerformative()), true);
                            searchAgentGUI.showMessage("Content  : " + message.getContent(), true);
                            break;

                            case ACLMessage.PROPOSE:

                                searchAgentGUI.showMessage("Sender: " +message.getSender().getName(), true);
                                searchAgentGUI.showMessage("Act de message : " +ACLMessage.getPerformative(message.getPerformative()), true);
                                try {
                                    Address address = (Address) message.getContentObject();
                                    searchAgentGUI.showMessage("Content  : J'ai recu la station id " + address.getNumero(), true);

                                    // Ensuite cet agent va envoyer le station iD ainsi que la date a l'agent air
                                    // qui ont publié leur service de type SEARCH_POLLUANT
                                    DFAgentDescription agentDescription = new DFAgentDescription();
                                    ServiceDescription serviceDescription = new ServiceDescription();
                                    serviceDescription.setType("air");
                                    serviceDescription.setName(SEARCH_POLLUANT);
                                    agentDescription.addServices(serviceDescription);
                                    DFAgentDescription[] dfAgentDescriptions = DFService.search(myAgent, agentDescription);
                                    AID agentAddress = dfAgentDescriptions[0].getName();
                                    ACLMessage aclMessage = new ACLMessage(ACLMessage.CFP);
                                    aclMessage.setContentObject(address);
                                    aclMessage.addReceiver(agentAddress);
                                    send(aclMessage);

                                } catch (UnreadableException | IOException | FIPAException e) {
                                    e.printStackTrace();
                                }

                                break;
                    }


                }
                else
                    block();

            }
        });


    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        switch (guiEvent.getType()){

            case 1 :
                ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
                Address address = (Address) guiEvent.getParameter(0);
                aclMessage.addReceiver(new AID(address.getAgent(), AID.ISLOCALNAME));
                try {
                    aclMessage.setContentObject(address);
                    aclMessage.setOntology("ui-address");
                    send(aclMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}

