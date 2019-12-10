package com.midleware.address;

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

import java.sql.SQLException;


public class AddressAgent extends Agent {

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
                serviceDescription.setType("address");
                serviceDescription.setName("search-address");
                agentDescription.addServices(serviceDescription);

                try {
                    DFService.deregister(myAgent);
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
                             String stationID = getStationID(aclMessage.getContent());
                             System.out.println("J'ai recu la proposition pour rechercher la borne qui correspond a l'adresse:"+aclMessage.getContent());
                             System.out.println("La station id de " + aclMessage.getContent() + " est : " + stationID);
                             System.out.println("Envoie de la station id vers l'agent recherche");
                             ACLMessage aclMessage1 = aclMessage.createReply();
                             aclMessage1.setPerformative(ACLMessage.INFORM);
                             aclMessage1.setContent(stationID);
                             send(aclMessage1);
                               break;

                         case ACLMessage.ACCEPT_PROPOSAL:

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
