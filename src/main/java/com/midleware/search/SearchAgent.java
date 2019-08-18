package com.midleware.search;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class SearchAgent extends Agent {

    @Override
    protected void setup() {
        ACLMessage  message = blockingReceive();
        System.out.println(message.getContent());
    }
}
