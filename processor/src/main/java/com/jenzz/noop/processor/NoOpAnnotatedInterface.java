package com.jenzz.noop.processor;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.util.ElementFilter.methodsIn;

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

    List<? extends TypeMirror> typeArguments() {
        return ((DeclaredType) interfaceElement.asType()).getTypeArguments();
    }

    List<ExecutableElement> methods() {
        List<ExecutableElement> methods = new ArrayList<>();
        addMethodsRecursively(interfaceElement, methods);
        return methods;
    }

    private static void addMethodsRecursively(TypeElement interfaceElement, List<ExecutableElement> methods) {
        methods.addAll(methodsIn(interfaceElement.getEnclosedElements()));

        List<? extends TypeMirror> interfaces = interfaceElement.getInterfaces();
        for (TypeMirror currentInterface : interfaces) {
            TypeElement currentInterfaceElement = (TypeElement) ((DeclaredType) currentInterface).asElement();
            addMethodsRecursively(currentInterfaceElement, methods);
        }
    }
}
