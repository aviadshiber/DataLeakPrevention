package org.example.apis.domain;

import lombok.Value;

import java.util.List;

@Value
public class RuleEntity {
    String name;
    List<String> conditions;
    List<String> actions;
}
