FROM maven:3.8.3-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn package

FROM tomcat
COPY --from=build /app/target/*.war $CATALINA_HOME/webapps/pp5.war
CMD ["catalina.sh", "run"]

