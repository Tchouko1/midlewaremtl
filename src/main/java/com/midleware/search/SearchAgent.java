package com.midleware.search;


import com.midleware.address.Address;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class SearchAgent extends Agent {

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
                        Address address = (Address) aclMessage.getContentObject();
                        System.out.println(address.getArrondissement());


                        ACLMessage aclMessage1 = aclMessage.createReply();
                        aclMessage1.setPerformative(ACLMessage.INFORM);
                        aclMessage1.setContent("Arrondissement recu, traitement en cours!");
                        send(aclMessage1);
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
