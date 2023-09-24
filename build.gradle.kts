import org.apache.tools.ant.taskdefs.condition.Os
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import java.util.*

plugins {
    java
    `maven-publish`
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springdoc.openapi-gradle-plugin") version "1.7.0"
}

group = "cc.tonny"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenLocal()
    maven { url = uri("https://mirrors.huaweicloud.com/repository/maven/") }
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    runtimeOnly("org.liquibase:liquibase-core")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:r2dbc-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    if (Os.isFamily(Os.FAMILY_MAC) && System.getProperty( "os.arch" ).lowercase(Locale.getDefault()).startsWith("aarch")) {
        runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.97.Final:osx-aarch_64")
    }


    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("io.r2dbc:r2dbc-h2")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:r2dbc")
    testImplementation("com.squareup.okhttp3:mockwebserver")
    testImplementation("org.springframework.cloud:spring-cloud-contract-stub-runner")
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

extra["springCloudVersion"] = "2022.0.4"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    if ((System.getenv("SPRING_PROFILES_ACTIVE") ?: "".contains("contract")) == false) {
        exclude("cc/tonny/orderservice/contract/**")
    }

    if ((System.getenv("SPRING_PROFILES_ACTIVE") ?: "".contains("docker")) == true) {
        exclude("**/*Embedded*")
    } else {
        exclude("**/*Docker*")
    }

    useJUnitPlatform()
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
    if (System.getProperty( "os.arch" ).lowercase(Locale.getDefault()).startsWith("aarch")) {
        builder.set("dashaun/builder:tiny")
    } else {
        builder.set("paketobuildpacks/builder:tiny")
    }

    imageName.set(project.name)
    environment.put("BP_JVM_VERSION", "17.*")

    if (System.getenv().containsKey("HTTP_PROXY")) {
        environment.put("HTTP_PROXY", System.getenv("HTTP_PROXY"))
    }
    if (System.getenv().containsKey("HTTPS_PROXY")) {
        environment.put("HTTPS_PROXY", System.getenv("HTTPS_PROXY"))
    }
}

publishing {
    publications {
        create<MavenPublication>("bootJava") {
            artifact(tasks.named("bootJar"))
        }
    }
}