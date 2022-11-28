package org.example.rules.conditions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.apis.RuleCondition;
import org.example.rules.utils.RegexHelper;

@Data
@AllArgsConstructor
public class ConditionSSN implements RuleCondition {

    public static final String REGEX_PATTERN = ".*((\\d){9}|(\\d{3}-\\d{2}-\\d{4})|\\d{3}(\\s)+\\d{2}(\\s)+\\d{4}).*";

    @Override
    public boolean test(String data) {
        return RegexHelper.isMatch(REGEX_PATTERN,data);
    }
}
