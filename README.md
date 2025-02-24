### this is currently broken, as of my 24/02/2025 the compiler doesnt correctly run this code and just spits out errors. This might be a personal IDE problem. I am also aware of packages like "lombok" do this exact thing and better but I wanted to try it out my self


# AutoProperty Annotation Processor for Java

This project provides a custom annotation processor for automatically generating getter and setter methods for Java class fields annotated with `@AutoProperty`. It helps eliminate boilerplate code in Java classes.

## Features
- Automatically generates getter and setter methods for fields annotated with `@AutoProperty`.
- Reduces the need to manually write boilerplate getter and setter code in Java classes.

## Getting Started

To use the `@AutoProperty` annotation processor in your project, follow these steps:

### 1. Add the Processor to Your Project

You need to add the annotation processor to your Java project. If you are using Maven or Gradle, make sure the processor is included in the classpath.

### 2. Use the `@AutoProperty` Annotation

You can now annotate fields in your classes with `@AutoProperty`, and the processor will automatically generate getter and setter methods for them.

```java
import org.ging.AutoProperty;

public class Person {

    @AutoProperty
    private String name;

    @AutoProperty
    private int age;

    // The annotation processor will generate getter and setter methods for the above fields
}





