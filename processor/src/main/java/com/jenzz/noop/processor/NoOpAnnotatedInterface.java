package com.jenzz.noop.processor;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.lang.model.element.ElementKind.METHOD;
import static javax.lang.model.element.Modifier.*;

class NoOpAnnotatedInterface {

    private final TypeElement interfaceElement;

    NoOpAnnotatedInterface(TypeElement interfaceElement) {
        this.interfaceElement = interfaceElement;
    }

    String simpleName() {
        return interfaceElement.getSimpleName().toString();
    }

    TypeName interfaceType() {
        return TypeName.get(interfaceElement.asType());
    }

    /**
     * @return <code>null</code> means package-private
     */
    Modifier visibility() {
        Set<Modifier> modifiers = interfaceElement.getModifiers();
        if (modifiers.contains(PUBLIC)) {
            return PUBLIC;
        } else if (modifiers.contains(PROTECTED)) {
            return PROTECTED;
        } else if (modifiers.contains(PRIVATE)) {
            return PRIVATE;
        } else {
            return null;
        }
    }

    List<ExecutableElement> methods() {
        List<ExecutableElement> methods = new ArrayList<>();
        for (Element element : interfaceElement.getEnclosedElements()) {
            if (element.getKind() == METHOD) {
                methods.add((ExecutableElement) element);
            }
        }
        return methods;
    }
}
