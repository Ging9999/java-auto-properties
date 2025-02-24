package org.ging;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@javax.annotation.processing.SupportedAnnotationTypes("org.ging.AutoProperty")
@javax.annotation.processing.SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AutoPropertyProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends javax.lang.model.element.TypeElement> annotations, javax.annotation.processing.RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(AutoProperty.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                VariableElement field = (VariableElement) element;
                String fieldName = field.getSimpleName().toString();
                String fieldType = field.asType().toString();
                String className = ((TypeElement) field.getEnclosingElement()).getQualifiedName().toString();

                // Debugging - print out details of field
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generating methods for " + className);

                // Generate getter and setter methods
                String getter = generateGetter(fieldName, fieldType);
                String setter = generateSetter(fieldName, fieldType);

                // Debugging - print the generated getter and setter
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generated Getter: " + getter);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generated Setter: " + setter);

                // Output the generated code to the Person.java class file
                try {
                    JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(className);
                    try (Writer writer = sourceFile.openWriter()) {
                        writer.write("package " + className.substring(0, className.lastIndexOf('.')) + ";\n\n");
                        writer.write("public class " + className.substring(className.lastIndexOf('.') + 1) + " {\n");
                        writer.write("    private " + fieldType + " " + fieldName + ";\n");
                        writer.write(getter);  // Write the getter method
                        writer.write(setter);  // Write the setter method
                        writer.write("\n}");
                    }
                } catch (IOException e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error generating methods: " + e.getMessage());
                }
            }
        }
        return false;
    }

    // Helper method to generate getter method
    private String generateGetter(String fieldName, String fieldType) {
        String methodName = "get" + capitalize(fieldName);
        return "    public " + fieldType + " " + methodName + "() { return this." + fieldName + "; }\n";
    }

    // Helper method to generate setter method
    private String generateSetter(String fieldName, String fieldType) {
        String methodName = "set" + capitalize(fieldName);
        return "    public void " + methodName + "(" + fieldType + " " + fieldName + ") { this." + fieldName + " = " + fieldName + "; }\n";
    }

    // Helper method to capitalize the first letter of the field name
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
