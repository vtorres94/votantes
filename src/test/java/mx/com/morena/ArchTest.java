package mx.com.morena;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("mx.com.morena");

        noClasses()
            .that()
            .resideInAnyPackage("mx.com.morena.service..")
            .or()
            .resideInAnyPackage("mx.com.morena.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..mx.com.morena.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
