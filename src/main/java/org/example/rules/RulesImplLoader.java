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
            val conditions = loadClassesGivenRuleEntity(ruleEntity, conditionsPackage, RuleCondition.class);
            val actions = loadClassesGivenRuleEntity(ruleEntity, actionsPackage, RuleAction.class);
            return new RuleContainer(ruleEntity.getName(), conditions, actions);
        }).collect(Collectors.toList());
    }

    private static <OUT> List<OUT> loadClassesGivenRuleEntity(RuleEntity ruleEntity, String packagePath, Class<OUT> protocol){
        log.info("loading action classes given {}",ruleEntity);
        return loadClasses(ruleEntity.getActions(), packagePath, protocol);
    }

}
