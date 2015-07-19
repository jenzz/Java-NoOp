package com.jenzz.noop.processor.rules;

import com.jenzz.noop.processor.exceptions.NotAnInterfaceException;

import javax.lang.model.element.Element;

import static javax.lang.model.element.ElementKind.INTERFACE;

public class InterfaceRule implements Rule<NotAnInterfaceException> {

    @Override
    public void validate(Element element) throws NotAnInterfaceException {
        if (element.getKind() != INTERFACE) {
            throw new NotAnInterfaceException(element.getSimpleName() + " is not an interface");
        }
    }
}
