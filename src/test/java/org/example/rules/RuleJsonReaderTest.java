package org.example.rules;

import junit.framework.TestCase;
import lombok.val;
import org.example.apis.RuleReader;
import org.example.apis.domain.RuleEntity;
import org.example.apis.domain.Rules;

import java.io.StringReader;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RuleJsonReaderTest extends TestCase {

    private static final String RULES_JSON_REPR = "{\n" +
            "  \"policy_rules\": [\n" +
            "    {\n" +
            "      \"name\":\"RuleIBAN\",\n" +
            "      \"conditions\" : [\"IBANCondition\"],\n" +
            "      \"actions\": [\"PrintToScreen\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"RuleSSN\",\n" +
            "      \"conditions\" : [\"SSNCondition\"],\n" +
            "      \"actions\": [\"PrintToScreen\"]\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    private static final RuleReader configReader = new RuleJsonReader();
    public void testJsonLoad() {
        val expectedRules =
                new Rules(
                        List.of(
                                //first rule
                                new RuleEntity(
                                "RuleIBAN",
                                List.of("IBANCondition")
                                ,List.of("PrintToScreen")),
                                //second rule
                                new RuleEntity("RuleSSN",
                                        List.of("SSNCondition"),
                                        List.of("PrintToScreen"))
                        )
                );

        val loadedRules = configReader.readRules(new StringReader(RULES_JSON_REPR));
        assertThat(expectedRules, equalTo(loadedRules));

    }
}