package org.example.apis;

import java.util.List;

public interface Rule {
    String name();
    List<RuleAction> actions();
    List<RuleCondition> conditions();
}
