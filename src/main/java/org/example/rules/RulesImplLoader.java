package org.example.rules;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.apis.Rule;
import org.example.apis.RuleAction;
import org.example.apis.RuleCondition;
import org.example.apis.RulesClassLoader;
import org.example.apis.domain.RuleEntity;
import org.example.apis.domain.Rules;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.rules.utils.ClassLoaderHelper.loadClasses;

@Slf4j
public class RulesImplLoader implements RulesClassLoader {
    private final static String actionsPackage = org.example.rules.actions.PackageInfo.class.getPackageName();
    private final static String conditionsPackage = org.example.rules.conditions.PackageInfo.class.getPackageName();
    public List<Rule> load(Rules rules) {
        log.info("starting to load all rules classes");
        return rules.getRules().stream().map(ruleEntity -> {
            val conditions = loadConditionsClasses(ruleEntity);
            val actions = loadActionsClasses(ruleEntity);
            return new RuleContainer(ruleEntity.getName(), conditions, actions);
        }).collect(Collectors.toList());
    }

    private static List<RuleAction> loadActionsClasses(RuleEntity ruleEntity) {
        log.info("loading action classes given {}",ruleEntity);
        return loadClasses(ruleEntity.getActions(), actionsPackage, RuleAction.class);
    }

    private static List<RuleCondition> loadConditionsClasses(RuleEntity ruleEntity) {
        log.info("loading conditions classes given {}",ruleEntity);
        return loadClasses(ruleEntity.getConditions(), conditionsPackage, RuleCondition.class);
    }

}
