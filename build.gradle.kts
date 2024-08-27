plugins {
	kotlin("plugin.spring") version "1.9.24"
	kotlin("jvm") version "1.9.24"

	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	id("io.gitlab.arturbosch.detekt") version ("1.23.6")
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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	implementation("net.logstash.logback:logstash-logback-encoder:8.0")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework:spring-webflux")
    testImplementation("org.springframework.graphql:spring-graphql-test")
	// testImplementation("org.assertj:assertj-core")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

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
