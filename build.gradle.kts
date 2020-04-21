plugins {
    id("org.jetbrains.intellij") version "0.4.18"
    java
    kotlin("jvm") version "1.3.71"
    id("com.palantir.git-version") version "0.12.3"
}

group = "com.nowsprinting"

val gitVersion: groovy.lang.Closure<*> by extra
version = gitVersion()

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("io.mockk:mockk:1.9")
    testCompile("org.junit.jupiter", "junit-jupiter-api", "5.6.2")
    testRuntime("org.junit.jupiter", "junit-jupiter-engine", "5.6.2")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2020.1"
    setPlugins("git4idea")
}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform {
            includeEngines("junit-jupiter")
        }
    }
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
      Add change notes here.<br>
      <em>most HTML tags may be used</em>""")
}