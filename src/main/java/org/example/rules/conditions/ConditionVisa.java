package org.example.rules.conditions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.apis.RuleCondition;
import org.example.rules.utils.RegexHelper;

@AllArgsConstructor
@Data
public class ConditionVisa implements RuleCondition {
    private final static String REGEX = ".*(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)).*";
    @Override
    public boolean test(String s) {
        return RegexHelper.isMatch(REGEX,s);
    }
}
