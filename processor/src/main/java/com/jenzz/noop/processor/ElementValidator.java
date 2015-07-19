package com.jenzz.noop.processor;

import com.jenzz.noop.processor.exceptions.RuleException;
import com.jenzz.noop.processor.rules.Rule;

import javax.lang.model.element.Element;

class ElementValidator {

    private final Rule[] rules;

    public ElementValidator(Rule... rules) {
        this.rules = rules;
    }

    public void validate(Element element) throws RuleException {
        for (Rule rule : rules) {
            rule.validate(element);
        }
    }
}
