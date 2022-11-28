package org.example.apis;

import org.example.apis.domain.Rules;

import java.io.Reader;

@FunctionalInterface
public interface RuleReader {
    Rules readRules(Reader reader);
}
