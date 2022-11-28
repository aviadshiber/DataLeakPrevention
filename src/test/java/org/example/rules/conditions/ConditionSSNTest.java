package org.example.rules.conditions;

import junit.framework.TestCase;
import lombok.val;

import java.util.List;

public class ConditionSSNTest extends TestCase {
    //condition should be stateless, so we can have single instance for all cases
    private final ConditionSSN condition = new ConditionSSN();
    public void testSSNHappyPath() {
        val validSSN = List.of("123-45-6789","856-45-6789");
        assert validSSN.stream().allMatch(condition);
    }
    public void testSSNSadPath(){
        val invalidSSN = List.of(
                "000-45-6789",
                "666-45-6789",
                "901-45-6789",
                "85-345-6789",
                "856-453-6789",
                "856-45-67891",
                "856-456789"
        );
        assert invalidSSN.stream().noneMatch(condition);
    }
}