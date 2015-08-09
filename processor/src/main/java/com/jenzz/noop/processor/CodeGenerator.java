package com.jenzz.noop.processor;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;

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
        TypeSpec.Builder classBuilder = classBuilder(className);

        addVisibility(classBuilder);
        addGenerics(classBuilder);
        addInterface(classBuilder);
        addMethods(classBuilder);

        return classBuilder.build();
    }

    private void addVisibility(TypeSpec.Builder classBuilder) {
        Modifier visibilityModifier = noOpAnnotatedInterface.visibility();
        if (visibilityModifier != null) {
            classBuilder.addModifiers(visibilityModifier);
        }
    }

    private void addGenerics(TypeSpec.Builder classBuilder) {
        List<? extends TypeMirror> typeMirrors = noOpAnnotatedInterface.typeArguments();
        if (!typeMirrors.isEmpty()) {
            for (TypeMirror typeMirror : typeMirrors) {
                TypeVariableName typeVariableName = TypeVariableName.get(typeMirror.toString());
                classBuilder.addTypeVariable(typeVariableName);
            }
        }
    }

    private void addInterface(TypeSpec.Builder classBuilder) {
        TypeName interfaceType = noOpAnnotatedInterface.interfaceType();
        classBuilder.addSuperinterface(interfaceType);
    }

    private void addMethods(TypeSpec.Builder classBuilder) {
        for (ExecutableElement method : noOpAnnotatedInterface.methods()) {
            MethodSpec.Builder builder = overriding(method);
            TypeKind returnType = method.getReturnType().getKind();
            if (returnType != VOID) {
                builder.addStatement("return " + defaultValue(returnType));
            }
            classBuilder.addMethod(builder.build());
        }
    }
}
