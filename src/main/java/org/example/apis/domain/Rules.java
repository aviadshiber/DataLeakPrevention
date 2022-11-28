package org.example.apis.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class Rules {
    List<RuleEntity> rules;
}
