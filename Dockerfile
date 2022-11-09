FROM tomcat:8.5.43-jdk8
ADD ./library-rest/target/library.war /usr/local/tomcat/webapps