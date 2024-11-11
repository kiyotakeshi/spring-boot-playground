import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.SchemaMappingType

plugins {
	kotlin("plugin.spring") version "1.9.24"
	kotlin("jvm") version "1.9.24"

	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	id("io.gitlab.arturbosch.detekt") version ("1.23.6")
    id("nu.studer.jooq") version "9.0"

    idea
}

group = "kiyotakeshi.com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

idea.module {
    generatedSourceDirs.addAll(listOf(
        File("${project.rootDir}/src/main/jooq"),
    ))
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("org.postgresql:postgresql")

	implementation("net.logstash.logback:logstash-logback-encoder:8.0")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.michael-bull.kotlin-result:kotlin-result:2.0.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework:spring-webflux")
    testImplementation("org.springframework.graphql:spring-graphql-test")
	// testImplementation("org.assertj:assertj-core")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    jooqGenerator("org.postgresql:postgresql")
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jooq {
    // https://github.com/etiennestuder/gradle-jooq-plugin/blob/main/example/configure_jooq_version_from_spring_boot/build.gradle#L29C1-L30C1
    version = dependencyManagement.importedProperties["jooq.version"]
    configurations {
        create("main") {
            // https://github.com/etiennestuder/gradle-jooq-plugin?tab=readme-ov-file#generating-the-jooq-sources
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                logging = Logging.INFO
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    // compose.yml
                    url = "jdbc:postgresql://localhost:35432/mydatabase"
                    user = "myuser"
                    password = "secret"
                }
                generator.apply {
                    // https://github.com/jOOQ/jOOQ/tree/version-3.19.1/jOOQ-codegen/src/main/java/org/jooq/codegen
                    name = "org.jooq.codegen.KotlinGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        schemata.addAll(
                            arrayOf(
                                SchemaMappingType().withInputSchema("playground"),
                            )
                        )
                    }
                    generate.apply {
                        // https://www.jooq.org/xsd/jooq-codegen-3.19.0.xsd
                        isKotlinNotNullPojoAttributes = true
                        isKotlinNotNullRecordAttributes = true
                        isKotlinNotNullInterfaceAttributes = true
                    }
                    target.apply {
                        packageName = "kiyotakeshi.com.example.generated.jooq"
                        // directory = "build/generated-src/jooq/main"  // default (can be omitted)
                        directory = "${project.rootDir}/src/main/jooq"
                    }
                }
            }
        }
    }
}

// https://detekt.dev/docs/gettingstarted/gradle#kotlin-dsl-3
detekt {
	toolVersion = "1.23.6"

	// https://detekt.dev/docs/gettingstarted/cli#use-the-cli
	// --auto-correct, The additional 'formatting' rule set, added with '--plugins', does support it and needs this flag.
	autoCorrect = true

	// detekt config に対して上書きしたいものだけを config/detekt/detekt.yml に記載する
	buildUponDefaultConfig = true
	config.setFrom("config/detekt/detekt.yml", "config/detekt/detekt-formatting.yml")

	// https://detekt.dev/docs/gettingstarted/gradle/#dependencies
	configurations.all {
		resolutionStrategy.eachDependency {
			if (requested.group == "org.jetbrains.kotlin") {
				useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
			}
		}
	}
}
