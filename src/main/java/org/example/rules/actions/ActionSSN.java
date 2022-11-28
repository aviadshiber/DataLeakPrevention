package org.example.rules.actions;

import org.example.apis.RuleAction;

public class ActionSSN implements RuleAction {

    @Override
    public void accept(String data) {
        System.out.printf("SSN side effect on data %s%n",data);
    }
}
