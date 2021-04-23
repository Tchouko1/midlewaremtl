package com.midleware.air;

import com.midleware.address.Address;
import com.midleware.utils.MidleWareSQL;
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
import java.sql.SQLException;

import static com.midleware.utils.MidleWareUtils.SEARCH_POLLUANT;


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
                          //  try {
                              /*  Address address = (Address) aclMessage.getContentObject();
                                String stationID = getStationID(address.getArrondissement());
                                System.out.println("J'ai recu la proposition pour rechercher la borne qui correspond a l'adresse:"+aclMessage.getContent());
                                System.out.println("La station id de " + address.getArrondissement() + " est : " + stationID);
                                System.out.println("Envoie de la station id vers l'agent recherche");
                                ACLMessage aclMessage1 = aclMessage.createReply();
                                aclMessage1.setPerformative(ACLMessage.INFORM);
                                address.setNumero(stationID);
                                aclMessage1.setContentObject(address);
                                send(aclMessage1);*/
                                break;
                          /*  } catch (UnreadableException | IOException e) {
                                e.printStackTrace();
                            }
*/

                        case ACLMessage.ACCEPT_PROPOSAL:

                            break;
                        case ACLMessage.INFORM :
                            System.out.println("Merci de m'informer!!!");
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
    public String getStationID(String arrondissementVille ){

        MidleWareSQL.makeJDBCConnection();
        String stationID = MidleWareSQL.getStationIDFromDB(arrondissementVille);
        try {
            MidleWareSQL.getPreparedStatement().close();
            MidleWareSQL.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return stationID;

    }
}