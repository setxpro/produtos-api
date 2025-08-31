FROM quay.io/wildfly/wildfly:37.0.0.Final-jdk17

COPY target/ROOT.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh","-b","0.0.0.0"]