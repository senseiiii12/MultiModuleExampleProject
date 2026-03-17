import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
            }
            val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
            dependencies {
                add("implementation", libs.findLibrary("kotlinx.coroutines.core").get())
                add("testImplementation", libs.findLibrary("junit").get())
            }
        }
    }
}
 