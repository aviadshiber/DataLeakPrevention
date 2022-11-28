package org.example.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.apis.ReportFormat;
import org.example.apis.Rule;
import org.example.apis.RuleCondition;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@Slf4j
public class RuleEvaluator {
    private ReportFormat formatter;

    public void evaluate(Rule rule, String data, Writer out) throws IOException {
        val conditions = rule.conditions();
        val actions = rule.actions();
        int i = 0;
        //assuming : if more actions than conditions they are ignored
        for(RuleCondition condition : conditions){
            //safeguard against unmatched action to condition - fail fast
            if(i >= actions.size()) throw new RuntimeException(String.format("Missing acton in rule %s", rule));
            if(condition.test(data)){
                val action = actions.get(i);
                log.debug("Rule {} was found, about to execute side effect on data {}",rule.name(),data);
                action.accept(data);
                writeToOutput(rule, data, out);
            }
            i++;
        }

    }

    private void writeToOutput(Rule rule, String data, Writer out) throws IOException {
        val timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").format(new java.util.Date());
        out.append(formatter.format(timestamp, rule, data));
    }
}
