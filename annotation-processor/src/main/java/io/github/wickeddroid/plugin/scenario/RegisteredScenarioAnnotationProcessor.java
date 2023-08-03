package io.github.wickeddroid.plugin.scenario;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("io.github.wickeddroid.plugin.scenario.RegisteredScenario")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class RegisteredScenarioAnnotationProcessor extends AbstractProcessor {
  @Override
  public boolean process(
          final Set<? extends TypeElement> annotations,
          final RoundEnvironment roundEnv
  ) {
    for (final var annotation : annotations) {
      final var annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

      try {
        this.generateScenarioRegistration(annotatedElements);
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
    return false;
  }

  private void generateScenarioRegistration(
          final Set<? extends Element> element) throws IOException {
    final var packageName = "io.github.wickeddroid.plugin.scenario";
    final var fileObject = processingEnv.getFiler().createSourceFile("ScenarioRegistration");

    try (final var out = new PrintWriter(fileObject.openWriter())) {
      out.println("package " + packageName + ";");
      out.println();
      element.forEach(scenario -> out.println("import io.github.wickeddroid.plugin.scenario.scenarios." + scenario.getSimpleName().toString() + ";"));
      out.println("""                            
              import io.github.wickeddroid.api.scenario.GameScenario;
              import java.util.Map;
              import java.util.HashMap;
              import team.unnamed.inject.InjectAll;
              import team.unnamed.inject.InjectIgnore;
              import team.unnamed.inject.Singleton;
                            
              @InjectAll
              @Singleton
              public class ScenarioRegistration {
                @InjectIgnore
                private final Map<String, GameScenario> scenarios = new HashMap<>();
              """);
      element.stream()
              .filter(clazz -> clazz.getKind() == ElementKind.CLASS)
              .forEach(scenario -> out.println("  private " + scenario.getSimpleName().toString() + " " + convertString(scenario.getSimpleName().toString()) + ";"));
      out.println();
      out.println("  public void registerScenarios() {");
      element.stream()
              .filter(clazz -> clazz.getKind() == ElementKind.CLASS)
              .forEach(scenario -> out.println("      this.registerScenario(" + convertString(scenario.getSimpleName().toString()) + ");"));
      out.println("""
                }
                
                public void registerScenario(final GameScenario scenario) {
                    this.scenarios.put(scenario.getKey(), scenario);
                }
                
                public Map<String, GameScenario> getScenarios() {
                    return this.scenarios;
                }
              """);
      out.println("}");
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  private String convertString( String s ) {
    final var n = s.length();
    final var ch = s.toCharArray();
    var c = 0 ;
    var ctr = 0;
    for (int i = 0; i < n; i++) {
      if( i == 0 ) {
        ch[i] = Character.toLowerCase(ch[i]);
      }

      if (ch[ i ] == ' ') {
        ctr++;
        ch[i + 1] = Character.toUpperCase(ch[i + 1]);
      } else {
        ch[c++] = ch[i];
      }
    }

    return String.valueOf( ch, 0, n - ctr);
  }
}
