#1. What is this?
-----------------
This repository contains a Spring-Boot autoconfiguration for OpenId and OAuth support.

- More details will be added later on how to use it.
- In the mean time, you may take a look at https://github.com/alexbt/appdirect-sampleapp to see how it's being used.

#2. How to use
--------------
At the moment, this library has only been deployed to maven central as SNAPSHOT. 
To use it, you need to add the following repository:

	<repositories>
		<repository>
			<id>central-snapshot</id>
			<name>central-snapshot</name>
			<url>https://oss.sonatype.org/content/repositories/snapshots</url>
		</repository>
	</repositories>
	
And the following dependency:

	<dependency>
		<groupId>com.alexbt</groupId>
		<artifactId>springboot-autoconfigure-openid-oauth</artifactId>
		<version>1.0.8-SNAPSHOT</version>
	</dependency>
