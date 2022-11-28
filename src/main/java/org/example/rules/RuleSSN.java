package org.example.rules;

import org.example.apis.RuleAction;
import org.example.apis.RuleCondition;

import java.util.List;

public class RuleSSN extends RuleContainer {

    public RuleSSN(String name, List<RuleCondition> conditions, List<RuleAction> actions) {
        super(name, conditions, actions);
    }
}
