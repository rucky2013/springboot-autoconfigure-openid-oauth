(1) OSX: Install https://gpgtools.org/

	. gpg --gen-key
	. 
	
(2) add source and javadoc plugins

(3) mvn clean install

(4) gpg -ab xxx-${VERSION}.pom xxx-${VERSION}.jar xxx-${VERSION}-source.jar xxx-${VERSION}-javadoc.jar

(5) jar -cvf bundle.jar *.jar *.asc *.pom

(6) Upload bundle: https://oss.sonatype.org/#staging-upload





