package com.jenzz.noop.processor.rules;

import com.jenzz.noop.processor.exceptions.RuleException;

import javax.lang.model.element.Element;

public interface Rule<E extends RuleException> {

    void validate(Element element) throws E;
}