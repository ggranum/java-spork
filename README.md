# Java-Spork: Random utility classes for Client-Server Java development

## Use
The code in this repository is most likely to be more useful if copy-pasted into your own projects and modified accordingly. 

As of 2018, you can now use Gradle to point to a Github dependency. See below for more.   
 
See /common-examples for a demonstration of various toys. 
  
Compile and run by executing 

```
./gradlew clean build
```

You can open this project directly in IntelliJ with no need to do a 'project import'. The project files that JetBrains recommends be shared are committed to source control.

## What's here

* Multi-project Gradle 'template' project.
   * Includes including properties from a 'local' (not in source control) file, 'gradle.local.properties'. Validates presence of required properties. See {projectRoot}/build.gradle, as well as {projectRoot}/buildSrc. 
* Builder Pattern (ExampleModel.java) generated via the [BuilderGenerator IntelliJ plugin](https://github.com/ggranum/java-builder-gen)
* Hibernate Validations - generated by above plugin, then tweaked. 
  * Does NOT use JSR-349, and this project does NOT require a JavaEE library dependency. Nor will it ever.
  * However, ValidationException has been 'tweaked' to handle the message parameter '${validatedValue}'.
* TestNG
   * With Hamcrest and Mockito. Though, no examples of Mockito. Oops.
* Log4j2

## MIT Licensed
Do with it what you like. But don't blame me if it breaks. 
