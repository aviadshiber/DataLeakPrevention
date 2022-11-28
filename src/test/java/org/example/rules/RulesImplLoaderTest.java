package org.example.rules;

import lombok.val;
import org.example.apis.RulesClassLoader;
import org.example.apis.domain.RuleEntity;
import org.example.apis.domain.Rules;
import org.example.rules.conditions.ConditionSSN;
import org.junit.Test;

import java.util.List;

public class RulesImplLoaderTest {


    @Test
    public void testSameImplIsLoaded() {
        RulesClassLoader loader = new RulesImplLoader();
        val rules = loader.load(new Rules(
                List.of(
                        new RuleEntity(
                                "RuleSSN",
                                List.of("ConditionSSN"),
                                List.of("ActionSSN")
                        )
                )
        ));
        assert rules != null;
        assert rules.size() > 0;
        val conditionList =  rules.get(0).conditions();
        assert conditionList!=null;
        assert conditionList.size() > 0;
        val firstCondition = conditionList.get(0);
        val expectedImpl = new ConditionSSN();
        val validSSN = "123-45-6789";
        assert firstCondition.test("123-45-6789") == expectedImpl.test(validSSN);
        val invalidSSN = "000-45-6789";
        assert firstCondition.test(invalidSSN) == expectedImpl.test(invalidSSN);
    }
}