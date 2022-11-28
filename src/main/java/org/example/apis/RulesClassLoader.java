package org.example.apis;

import org.example.apis.domain.Rules;

import java.util.List;

public interface RulesClassLoader {
    List<Rule> load(Rules rules);
}
