FROM tomcat:8.5.43-jdk8
ADD ./library-rest/target/library-rest.war /usr/local/tomcat/webapps
ADD ./tomcat-users.xml /usr/local/tomcat/conf