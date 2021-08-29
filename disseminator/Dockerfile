# we will use tomcat 9
FROM openjdk:8 
EXPOSE 8080
ADD target/disseminator.jar disseminator.jar
ENTRYPOINT ["Java","-jar","disseminator.jar"]
# copy the packaged war file into webapps directory of tomcat
#COPY target/expReimbursement.war /usr/local/tomcat/webapps/
# set the startup command to execute the tomcat server
#CMD ["catalina.sh", "run"]