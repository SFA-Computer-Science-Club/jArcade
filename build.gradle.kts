plugins {
    id("java-library")
    id("idea")
}

group = "org.goose"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation(files("src/libs/Raylib-J.jar"))
    implementation(files("src/libs/Jamepad.jar"))
    //api(fileTree("src/libs") { include("*.jar") })
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.goose.Main"
    }
    from(configurations.compileClasspath.get().map { if (it.isDirectory()) it else zipTree(it) })
}
