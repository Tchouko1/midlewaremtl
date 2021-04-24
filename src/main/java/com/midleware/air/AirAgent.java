package com.midleware.air;

import com.midleware.address.Address;
import com.midleware.polluant.Polluant;
import com.midleware.service.PolluantService;
import com.midleware.utils.MidleWareSQL;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.List;

import static com.midleware.utils.MidleWareUtils.SEARCH_POLLUANT;
import static com.midleware.utils.MidleWareUtils.SEARCH_CALCUL;


public class AirAgent extends Agent {

    @Override
    protected void setup() {

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);

        parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {

                DFAgentDescription agentDescription = new DFAgentDescription();
                agentDescription.setName(getAID());
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("air");
                System.out.println("Je suis l'agent AIR j'enregistre mes services");
                serviceDescription.setName(SEARCH_POLLUANT);
                agentDescription.addServices(serviceDescription);

                try {
                    DFService.register(myAgent, agentDescription);
                } catch (FIPAException fe){
                    fe.printStackTrace();
                }

            }
        });

        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if(aclMessage != null){
                    switch (aclMessage.getPerformative()){
                        case ACLMessage.CFP :
                            try {
                                Address address = (Address) aclMessage.getContentObject();
                                System.out.println("J'ai recu la proposition pour rechercher la liste des polluants pour la station:" +
                                        ""+address.getNumero() + " pour le : "+ address.getDate());
                                System.out.println("Envoie les polluants vers l'agent calcul");
                                ACLMessage aclMessage1 = aclMessage.createReply();
                                aclMessage1.setPerformative(ACLMessage.INFORM);
                                aclMessage1.setContent("J'ai recu la proposition pour rechercher la liste des polluants pour la station:" +
                                         ""+address.getNumero() + " pour le : "+ address.getDate());
                                send(aclMessage1);


                                //- envoie la liste des polluants a l'agent calcul....
                                DFAgentDescription agentDescription = new DFAgentDescription();
                                ServiceDescription serviceDescription = new ServiceDescription();
                                serviceDescription.setType("calcul");
                                serviceDescription.setName(SEARCH_CALCUL);
                                agentDescription.addServices(serviceDescription);
                                DFAgentDescription[] dfAgentDescriptions = DFService.search(myAgent, agentDescription);
                                AID agentAddress = dfAgentDescriptions[0].getName();
                                ACLMessage aclMessage2 = new ACLMessage(ACLMessage.CFP);
                                aclMessage2.setContentObject(getPolluantByStationIDAndDate(address.getNumero(),address.getDate()).toArray());
                                aclMessage2.addReceiver(agentAddress);
                                send(aclMessage2);



                                break;
                            } catch (UnreadableException | IOException | FIPAException e) {
                                e.printStackTrace();
                            }

                        case ACLMessage.ACCEPT_PROPOSAL:

                            // pour recuperer le resultat du calcul

                            break;
                        case ACLMessage.INFORM :
                            aclMessage.getContent();
                            System.out.println(aclMessage.getContent()+" Maintenant je dois l'envoyer a l'agent Search");
                            break;


                        default:

                            break;

                    }

                }
                else {
                    block();

                }
            }
        });



    }


    // recupere le poluant
    public List<Polluant> getPolluantByStationIDAndDate(String stationId, String date ){
        List<Polluant> polluantList = new PolluantService().getAllPolluantByStationIDAndDate(stationId, date);
        return polluantList;

    }
}