package org.example.rules.conditions;

import lombok.val;
import org.junit.Test;

public class ConditionCreditCardTest {

    @Test
    public void testMasterCard() {
        val validMasterCard = "5336122222221758";
        val invalidMasterCard = "4536122222221758";
        val masterValidator = new ConditionMasterCard();
        assert masterValidator.test(validMasterCard);
        assert !masterValidator.test(invalidMasterCard);
    }

    @Test
    public void testVisa(){
        val validVisa = "4536122222221758";
        val invalidVisa = "5336122222221758";
        val visaValidator = new ConditionVisa();
        assert visaValidator.test(validVisa);
        assert !visaValidator.test(invalidVisa);
    }
}