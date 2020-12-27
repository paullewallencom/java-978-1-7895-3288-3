FROM payara/micro:5-SNAPSHOT
COPY target/advanced-service.war /opt/payara/deployments

ENV JAEGER_SERVICE_NAME advanced-service
ENV JAEGER_DISABLE_GLOBAL_TRACER true

ENTRYPOINT ["java", "-Xmx128m", "-client", "-jar", "/opt/payara/payara-micro.jar"]
CMD ["--noCluster", "--deploymentDir", "/opt/payara/deployments"]
