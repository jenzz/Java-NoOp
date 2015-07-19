package com.jenzz.noop.processor;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;

import static com.jenzz.noop.processor.Utils.defaultValue;
import static com.squareup.javapoet.MethodSpec.overriding;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.type.TypeKind.VOID;

class CodeGenerator {

    private static final String CLASS_PREFIX = "NoOp";

    private final NoOpAnnotatedInterface noOpAnnotatedInterface;

    CodeGenerator(NoOpAnnotatedInterface noOpAnnotatedInterface) {
        this.noOpAnnotatedInterface = noOpAnnotatedInterface;
    }

    TypeSpec brewJava() {
        String className = CLASS_PREFIX + noOpAnnotatedInterface.simpleName();
        TypeSpec.Builder builder = classBuilder(className);

        Modifier visibilityModifier = noOpAnnotatedInterface.visibility();
        if (visibilityModifier != null) {
            builder.addModifiers(visibilityModifier);
        }

        builder.addSuperinterface(noOpAnnotatedInterface.interfaceType());
        for (ExecutableElement method : noOpAnnotatedInterface.methods()) {
            builder.addMethod(generateMethod(method));
        }

        return builder.build();
    }

    private MethodSpec generateMethod(ExecutableElement method) {
        MethodSpec.Builder builder = overriding(method);
        TypeKind returnType = method.getReturnType().getKind();
        if (returnType != VOID) {
            builder.addStatement("return " + defaultValue(returnType));
        }
        return builder.build();
    }
}
