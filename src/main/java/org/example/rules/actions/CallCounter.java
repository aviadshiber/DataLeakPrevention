package org.example.rules.actions;

import org.example.apis.RuleAction;

import java.util.concurrent.atomic.AtomicInteger;

public class CallCounter implements RuleAction {
    AtomicInteger counter;
    public CallCounter(){
        counter = new AtomicInteger(0);
    }

    @Override
    public void accept(String s) {
        if(counter.incrementAndGet()>=100){
            System.out.println("------------THRESHOLD REACHED SENDING ALERT!!!---------------");
            counter.set(0);
        }
    }

    public int count(){
        return counter.get();
    }
}
