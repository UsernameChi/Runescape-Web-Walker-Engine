plugins {
    kotlin("jvm")
    id("io.freefair.lombok")
}

group = "scripts"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        setUrl("https://gitlab.com/api/v4/projects/20741387/packages/maven")
    }
}

dependencies {
    compileOnly("org.tribot:tribot-script-sdk:+")
    compileOnly("org.tribot:tribot-client:+")
    compileOnly(files("allatori-annotations-7.5.jar"))
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    main {
        java.srcDirs("src")
    }
}