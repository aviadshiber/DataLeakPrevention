package org.example.rules.conditions;

import lombok.val;
import org.junit.Test;

public class IBANConditionTest {

    @Test
    public void testIBANValidation(){
        String validText = "    FR7630006000011234567890189  ";
        String invalidText = "    12 BOFI 9000 0112 3456 78  ";
        val validator = new IBANCondition();
        assert validator.test(validText);
        assert !validator.test(invalidText);
    }

}