package com.jenzz.noop.processor;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import java.util.List;

import static com.jenzz.noop.processor.Utils.decapitalize;
import static com.jenzz.noop.processor.Utils.defaultValue;
import static com.squareup.javapoet.MethodSpec.overriding;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.lang.model.type.TypeKind.VOID;

class CodeGenerator {

    private static final String CLASS_PREFIX = "NoOp";
    private static final String INSTANCE = "INSTANCE";
    private static final String MISSING_SINGLETON_JAVADOC = "<b>Note:</b> Does not provide a singleton INSTANCE due to presence of Generics.\n";

    private final NoOpAnnotatedInterface noOpAnnotatedInterface;

    CodeGenerator(NoOpAnnotatedInterface noOpAnnotatedInterface) {
        this.noOpAnnotatedInterface = noOpAnnotatedInterface;
    }

    TypeSpec brewJava() {
        TypeSpec.Builder classBuilder = classBuilder(className());

        addVisibility(classBuilder);
        addGenerics(classBuilder);
        addInterface(classBuilder);
        addMethods(classBuilder);

        if (hasGenerics()) {
            addMissingSingletonJavaDoc(classBuilder);
        } else {
            addSingleton(classBuilder);
        }

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
        for (TypeMirror typeMirror : typeMirrors) {
            TypeVariableName typeVariableName = TypeVariableName.get((TypeVariable) typeMirror);
            classBuilder.addTypeVariable(typeVariableName);
        }
    }

    private void addInterface(TypeSpec.Builder classBuilder) {
        TypeName interfaceType = noOpAnnotatedInterface.interfaceType();
        classBuilder.addSuperinterface(interfaceType);
    }

    private void addMethods(TypeSpec.Builder classBuilder) {
        for (ExecutableElement method : noOpAnnotatedInterface.methods()) {
            MethodSpec.Builder methodBuilder = overriding(method);
            TypeKind returnType = method.getReturnType().getKind();
            if (returnType != VOID) {
                methodBuilder.addStatement("return " + defaultValue(returnType));
            }
            classBuilder.addMethod(methodBuilder.build());
        }
    }

    private void addMissingSingletonJavaDoc(TypeSpec.Builder classBuilder) {
        classBuilder.addJavadoc(MISSING_SINGLETON_JAVADOC);
    }

    private void addSingleton(TypeSpec.Builder classBuilder) {
        addSingletonField(classBuilder);
        addSingletonMethod(classBuilder);
    }

    private void addSingletonField(TypeSpec.Builder classBuilder) {
        FieldSpec singletonField = FieldSpec.builder(noOpAnnotatedInterface.interfaceType(), INSTANCE)
                .addModifiers(PUBLIC, STATIC, FINAL)
                .initializer("new " + className() + "()")
                .build();
        classBuilder.addField(singletonField);
    }

    private void addSingletonMethod(TypeSpec.Builder classBuilder) {
        MethodSpec singletonMethod = MethodSpec.methodBuilder(decapitalize(className()))
                .addModifiers(PUBLIC, STATIC)
                .returns(noOpAnnotatedInterface.interfaceType())
                .addStatement("return INSTANCE")
                .build();
        classBuilder.addMethod(singletonMethod);
    }

    private String className() {
        return CLASS_PREFIX + noOpAnnotatedInterface.simpleName();
    }

    private boolean hasGenerics() {
        return !noOpAnnotatedInterface.typeArguments().isEmpty();
    }
}
