package org.example.rules;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.apis.Rule;
import org.example.apis.RuleReader;
import org.example.apis.domain.RuleEntity;
import org.example.apis.domain.Rules;

import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class RuleJsonReader implements RuleReader {
    private static final String ROOT_OBJECT = "policy_rules";

    @Override
    public Rules readRules(Reader reader) {
        log.info("reading rules configurations from json reader, with root object {}",ROOT_OBJECT);
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        JsonArray listOfRules = jsonObject.getAsJsonArray(ROOT_OBJECT);
        Gson g = new Gson();
        List<RuleEntity> rules = g.fromJson(listOfRules,
                new TypeToken<List<RuleEntity>>() {}.getType());
        return new Rules(rules.stream().filter(Objects::nonNull).collect(Collectors.toList()));
    }
}
