package org.example.rules.conditions;

import org.example.apis.RuleCondition;
import org.example.rules.utils.RegexHelper;

public class ConditionMasterCard implements RuleCondition {
    private static final String REGEX = ".*(?:(?<mastercard>5[1-5][0-9]{14})).*";

    @Override
    public boolean test(String s) {
        return RegexHelper.isMatch(REGEX,s);
    }
}
