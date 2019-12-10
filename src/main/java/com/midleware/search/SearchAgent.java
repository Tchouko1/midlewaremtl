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

public class SearchAgent extends Agent {
    private AID[] listAddress;

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
                        // et il envoi une reponse a l'interface graphique
                        //TODO comment gerer la reception de plusieurs msg
                        Address address = (Address) aclMessage.getContentObject();
                        String stationId = aclMessage.getContent();
                        if(address !=null) {

                            System.out.println(address.getArrondissement());
                            ACLMessage aclMessage1 = aclMessage.createReply();
                            aclMessage1.setPerformative(ACLMessage.INFORM);
                            aclMessage1.setContent("Arrondissement recu, traitement en cours!");
                            send(aclMessage1);

                            // Ensuite cet agent va consulter la liste des agents address
                            // qui ont publie leur service de type search-adress

                            DFAgentDescription agentDescription = new DFAgentDescription();
                            ServiceDescription serviceDescription = new ServiceDescription();
                            serviceDescription.setType("address");
                            serviceDescription.setName("search-address");

                            try {
                                DFAgentDescription[] dfAgentDescriptions = DFService.search(myAgent, agentDescription);
                                AID agentAddress = dfAgentDescriptions[0].getName();
                                ACLMessage message = new ACLMessage(ACLMessage.CFP);
                                message.setContent(address.getArrondissement());
                                message.addReceiver(agentAddress);
                                send(message);


                            } catch (FIPAException e) {
                                e.printStackTrace();
                            }

                        }
                        else if(stationId !=null){

                            System.out.println("J'ai recu la station id : " + stationId);


                        }


                    } catch (UnreadableException e) {
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
