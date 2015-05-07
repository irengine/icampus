README for icampus
==========================
## Installation
+  Install Java from the Oracle website.
+  Install Maven (recommended).
+  Install Git from git-scm.com. We recommend you also use a tool like SourceTree if you are starting with Git.
+  Install Node.js from the Node.js website. This will also install npm, which is the node package manager we are using in the next commands.
+  Install Yeoman: npm install -g yo
+  Install Bower: npm install -g bower
+  Depending on your preferences, install either Grunt (recommended) with npm install -g grunt-cli
+  Install JHipster: npm install -g generator-jhipster

## Setup
+  npm install
+  bower install

## Running
+  mvn spring-boot:run
+  grunt serve

## Development ##
+  create new entity, yo jhipster:entity dummy
+  modify existing entity, mvn liquibase:diff

### liquibase setting
+  pom.xml

                 <configuration>
                     <changeLogFile>src/main/resources/config/liquibase/master.xml</changeLogFile>
                     <diffChangeLogFile>src/main/resources/config/liquibase/changelog/${maven.build.timestamp}_changelog
        -                    <driver></driver>
        -                    <url></url>
        +                    <driver>org.h2.Driver</driver>
        +                    <url>jdbc:h2:file:~/.h2/icampus;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1</url>
                     <defaultSchemaName></defaultSchemaName>
                     <username></username>
                     <password></password>
        -                    <referenceUrl>hibernate:spring:com.irengine.campus.domain?dialect=</referenceUrl>
        +                    <referenceUrl>hibernate:spring:com.irengine.campus.domain?dialect=org.hibernate.dialect.H2Dialect</
                     <verbose>true</verbose>
                     <logging>debug</logging>
                 </configuration>
+  src/main/resources/config/application-dev.yml

        -        url: jdbc:h2:mem:jhipster;DB_CLOSE_DELAY=-1
        +        url: jdbc:h2:file:~/.h2/icampus;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1

### Liquibase Configuration File ###

+ src/main/resources/config/liquibase/master.xml