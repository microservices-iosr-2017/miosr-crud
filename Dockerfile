FROM java:openjdk-8-jre

ADD target/miosr-crud-0.0.1-SNAPSHOT.jar .

CMD java -Dspring.config.location=/etc/appconfig/config.properties -jar miosr-crud-0.0.1-SNAPSHOT.jar
