package org.example.rules.actions;

import org.example.apis.RuleAction;

public class PrintToScreen implements RuleAction {
    @Override
    public void accept(String s) {
        System.out.printf("this is side effect caused by %s%n",s);
    }
}
