## Spring Boot 3 + JPA + Auth JWT

### Description

#### Simple implementation of Spring Boot 3 JPA + Auth + JWT with PostgreSQL database.<br />The app also uses Http-Only Cookies to set and authenticate REST API endpoints.

#### Note: Will update the readme soon🫡 


### creat db sara_system for 4 schema applicant , base-info , old-data , public

### default user admin 1qazZAQ!


## Encrypt
mvn jasypt:encrypt-value -Djasypt.encryptor.password=javatechie -Djasypt.plugin.value=Password

## Decrypt
mvn jasypt:decrypt-value -Djasypt.encryptor.password=javatechie -Djasypt.plugin.value=nObqvmVPYhxVaykMl09QVtGCQWjpd7al1RJhOsyz1eLkb6J2USMu9Fb//e4a6Vro

## run jar
java -Djasypt.encryptor.password=javatechie -jar spring-boot-application.jar