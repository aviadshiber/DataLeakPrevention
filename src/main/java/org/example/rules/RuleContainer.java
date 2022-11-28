package org.example.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.apis.Rule;
import org.example.apis.RuleAction;
import org.example.apis.RuleCondition;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Builder
public class RuleContainer implements Rule {
    private String name;
    private List<RuleCondition> conditions;
    private List<RuleAction> actions;


    @Override
    public String name() {
        return name;
    }

    @Override
    public List<RuleAction> actions() {
        return actions;
    }

    @Override
    public List<RuleCondition> conditions() {
        return conditions;
    }
}
