package com.midleware.calcul;

import com.midleware.address.Address;
import com.midleware.polluant.Polluant;
import com.midleware.service.PolluantService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.midleware.utils.MidleWareUtils.SEARCH_CALCUL;



public class CalculAgent extends Agent {

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
                serviceDescription.setType("calcul");
                System.out.println("Je suis l'agent Calcul j'enregistre mes services");
                serviceDescription.setName(SEARCH_CALCUL);
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
                                Object[] polluantObject = (Object[]) aclMessage.getContentObject();

                                System.out.println("J'ai recu la liste des polluants");
                                System.out.println("Traitement en cours");

                                Double iqa = getIQA(polluantObject);

                                ACLMessage aclMessage1 = aclMessage.createReply();
                                aclMessage1.setPerformative(ACLMessage.INFORM);
                                aclMessage1.setContent("L'indice est :" +iqa);
                                send(aclMessage1);





                                break;
                            } catch (UnreadableException  e) {
                                e.printStackTrace();
                            }

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
    public Double getIQA(Object[] polluants ){
        double iqa = new PolluantService().getIQA(polluants);
        return iqa;

    }
}