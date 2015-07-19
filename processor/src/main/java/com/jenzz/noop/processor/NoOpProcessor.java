package com.jenzz.noop.processor;

import com.google.auto.service.AutoService;
import com.jenzz.noop.annotation.NoOp;
import com.jenzz.noop.processor.exceptions.RuleException;
import com.jenzz.noop.processor.exceptions.UnnamedPackageException;
import com.jenzz.noop.processor.rules.InterfaceRule;
import com.jenzz.noop.processor.rules.Rule;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.jenzz.noop.processor.Utils.packageName;
import static javax.lang.model.SourceVersion.latestSupported;

@AutoService(Processor.class)
public class NoOpProcessor extends AbstractProcessor {

    private static final Rule[] RULES =
            new Rule[]{
                    new InterfaceRule()
            };

    private final ElementValidator elementValidator = new ElementValidator(RULES);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Messager.init(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            add(NoOp.class.getCanonicalName());
        }};
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(NoOp.class)) {

            // annotation is only allowed on classes, so we can safely cast here
            TypeElement annotatedClass = (TypeElement) annotatedElement;
            try {
                elementValidator.validate(annotatedClass);
            } catch (RuleException e) {
                Messager.error(annotatedClass, e.getMessage());
                return true;
            }

            try {
                generateCode(annotatedClass);
            } catch (IOException | UnnamedPackageException e) {
                Messager.error(annotatedElement, "Couldn't generate no-op implementation for %s: %s",
                        annotatedClass, e.getMessage());
            }
        }

        return true;
    }

    private void generateCode(TypeElement annotatedClass) throws IOException, UnnamedPackageException {
        String packageName = packageName(processingEnv.getElementUtils(), annotatedClass);

        NoOpAnnotatedInterface noOpAnnotatedInterface = new NoOpAnnotatedInterface(annotatedClass);
        CodeGenerator codeGenerator = new CodeGenerator(noOpAnnotatedInterface);
        TypeSpec generatedClass = codeGenerator.brewJava();

        JavaFile javaFile = JavaFile.builder(packageName, generatedClass).build();
        javaFile.writeTo(processingEnv.getFiler());
    }
}
