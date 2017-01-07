1. What is this?
----------------
This repository contains a Spring-Boot autoconfiguration for OpenId and OAuth support.

- More details will be added later on how to use it.
- In the mean time, you may take a look at https://github.com/alexbt/appdirect-sampleapp to see how it's being used.

2. How to use
--------------
I'm currently in the process of setting up deployment/releases and automatic synchronisation to maven central. In the mean time, to use this library, you need to add the following repository:

	<repositories>
		<repository>
			<id>oss-releases</id>
			<name>oss-releases</name>
			<url>https://oss.sonatype.org/content/repositories/releases</url>
		</repository>
	</repositories>
	
And the following dependency:

	<dependency>
		<groupId>com.alexbt</groupId>
		<artifactId>springboot-autoconfigure-openid-oauth</artifactId>
		<version>1.0.9</version>
	</dependency>
