package com.midleware.ui;


import com.midleware.address.Address;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class InterfaceAgent extends GuiAgent {

    InterfaceAgentGUI searchAgentGUI;
    @Override
    protected void setup() {
       searchAgentGUI = new InterfaceAgentGUI();
       searchAgentGUI.setInterfaceAgent(this);
       System.out.println("Demmarrage de l'agent recherche");

       // ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
       // addBehaviour(parallelBehaviour);
        addBehaviour(new CyclicBehaviour() {

            @Override
            public void action() {
                ACLMessage message = receive();
                if(message != null){

                    searchAgentGUI.showMessage("Sender" +message.getSender().getName(), true);
                    searchAgentGUI.showMessage("Acte de message : " +ACLMessage.getPerformative(message.getPerformative()), true);
                    searchAgentGUI.showMessage("Content  : " +message.getContent(), true);
                    searchAgentGUI.showMessage("Language  : " +message.getLanguage(), true);
                    searchAgentGUI.showMessage("Ontology  : " +message.getOntology(), true);
                }
                else
                    block();

            }
        });

        /*parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
           @Override
           public void action() {
               searchAgentGUI.showMessage("Bonne");
           }
       });

        parallelBehaviour.addSubBehaviour(new TickerBehaviour(this, 1000) {
            private  int counter;
           @Override
           protected void onTick() {
               counter++;
               searchAgentGUI.showMessage("Tiker bonne"+ counter);

           }
       });*/

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
