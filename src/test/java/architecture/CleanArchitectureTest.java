package architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "deathcounter", importOptions = ImportOption.DoNotIncludeTests.class)
public class CleanArchitectureTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()
            .layer("Gui").definedBy("deathcounter.gui..")
            .layer("Controllers").definedBy("deathcounter.controller..")
            .layer("Service").definedBy("deathcounter.service..")
            .layer("Domain").definedBy("deathcounter.domain..")

            .whereLayer("Gui").mayNotBeAccessedByAnyLayer()
            .whereLayer("Controllers").mayOnlyBeAccessedByLayers("Gui")
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controllers")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Service");
}
