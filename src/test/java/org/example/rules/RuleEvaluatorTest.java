package org.example.rules;

import lombok.SneakyThrows;
import lombok.val;
import org.example.apis.Rule;
import org.example.apis.RuleAction;
import org.example.apis.RuleCondition;
import org.example.rules.utils.NullWriter;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class RuleEvaluatorTest {


    @Test
    @SneakyThrows
    public void ruleEvaluatorTest(){
        RuleEvaluator ruleEvaluator = new RuleEvaluator((timestamp, context, data, args) -> String.format("[%s] - found [rule:%s] given \"%s\"%n",timestamp,context.name(),data));
        AtomicInteger counter = new AtomicInteger(0);
        Rule mockRule = new Rule(){
            @Override
            public String name() {
                return "test-rule";
            }

            @Override
            public List<RuleAction> actions() {
                return List.of(s -> {
                    counter.incrementAndGet();
                });
            }

            @Override
            public List<RuleCondition> conditions() {
                return List.of(s -> s.length() % 2 == 0);
            }
        };
        val writer = new NullWriter();
        ruleEvaluator.evaluate(mockRule,"0000",writer);
        ruleEvaluator.evaluate(mockRule,"0",writer);

        assertEquals("should be evaluated once (counter inc only once)", 1, counter.get());
    }


}