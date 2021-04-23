package com.midleware.search;


import com.midleware.address.Address;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

import static com.midleware.utils.MidleWareUtils.SEARCH_ADDRESS;

public class SearchAgent extends Agent {
    private AID[] listAddress;
    private static  AID aidInterface;

    @Override
    protected void setup() {

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                ACLMessage aclMessage =  receive();
                if(aclMessage != null){
                    try {

                        // cet agent recois les informations de l'interface graphique
                        // et il envoi un ack a l'interface graphique
                        //TODO comment gerer la reception de plusieurs msg
                        Address address = (Address) aclMessage.getContentObject();
                        if (address.getNumero() !=null){
                            ACLMessage aclMessage2 = new ACLMessage(ACLMessage.PROPOSE);
                            if(aidInterface != null) {
                                aclMessage2.addReceiver(aidInterface);
                                aclMessage2.setContentObject(address);
                                //aclMessage2.setOntology("ui-address");
                                send(aclMessage2);
                            }

                           /* ACLMessage aclMessage1 = aclMessage.createReply();
                            aclMessage1.setPerformative(ACLMessage.INFORM);
                            aclMessage1.setContent("J'ai recu la station id : " + address.getNumero());
                            send(aclMessage1);*/
                            System.out.println("J'ai recu la station id : " + address.getNumero());
                        }
                        else {

                            System.out.println(address.getArrondissement());
                            ACLMessage aclMessage1 = aclMessage.createReply();
                            aidInterface = aclMessage.getSender();
                            aclMessage1.setPerformative(ACLMessage.INFORM);
                            aclMessage1.setContent("Arrondissement recu, traitement en cours!");
                            send(aclMessage1);

                            // Ensuite cet agent va consulter la liste des agents qui s'occupe des addresses
                            // qui ont publié leur service de type search-address
                            DFAgentDescription agentDescription = new DFAgentDescription();
                            ServiceDescription serviceDescription = new ServiceDescription();
                            serviceDescription.setType("address");
                            serviceDescription.setName(SEARCH_ADDRESS);
                            agentDescription.addServices(serviceDescription);

                            try {
                                DFAgentDescription[] dfAgentDescriptions = DFService.search(myAgent, agentDescription);
                                AID agentAddress = dfAgentDescriptions[0].getName();
                                ACLMessage message = new ACLMessage(ACLMessage.CFP);
                                message.setContentObject(address);
                                message.addReceiver(agentAddress);
                                send(message);

                            } catch (FIPAException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    block();
                }

            }
        });


    }
}