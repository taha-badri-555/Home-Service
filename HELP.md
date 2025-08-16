# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.3/maven-plugin/build-image.html)
* [Spring Boot Testcontainers support](https://docs.spring.io/spring-boot/3.5.3/reference/testing/testcontainers.html#testing.testcontainers)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/3.5.3/reference/actuator/index.html)
* [Spring Batch](https://docs.spring.io/spring-boot/3.5.3/how-to/batch.html)
* [Spring Cache Abstraction](https://docs.spring.io/spring-boot/3.5.3/reference/io/caching.html)
* [Contract Stub Runner](https://docs.spring.io/spring-cloud-contract/reference/project-features-stubrunner.html)
* [Contract Verifier](https://docs.spring.io/spring-cloud-contract/reference/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.3/reference/using/devtools.html)
* [htmx](https://github.com/wimdeblauwe/htmx-spring-boot)
* [Spring Modulith](https://docs.spring.io/spring-modulith/reference/)
* [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/)
* [Spring gRPC](https://docs.spring.io/spring-grpc/reference/index.html)
* [Testcontainers](https://java.testcontainers.org/)
* [Embedded LDAP Server](https://docs.spring.io/spring-boot/3.5.3/reference/data/nosql.html#data.nosql.ldap.embedded)
* [Validation](https://docs.spring.io/spring-boot/3.5.3/reference/io/validation.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.3/reference/web/servlet.html)
* [Spring Web Services](https://docs.spring.io/spring-boot/3.5.3/reference/io/webservices.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.5.3/reference/web/reactive.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)
* [Caching Data with Spring](https://spring.io/guides/gs/caching/)
* [htmx](https://www.youtube.com/watch?v=j-rfPoXe5aE)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Additional Links

These additional references should also help you:

* [Various sample apps using Spring gRPC](https://github.com/spring-projects/spring-grpc/tree/main/samples)

### Testcontainers support

This project
uses [Testcontainers at development time](https://docs.spring.io/spring-boot/3.5.3/reference/features/dev-services.html#features.dev-services.testcontainers).

Testcontainers has been configured to use the following Docker images:

Please review the tags of the used images and set them to the same as you're running in production.

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

