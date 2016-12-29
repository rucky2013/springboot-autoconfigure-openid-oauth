Table of Content
=================
1. [Continuous Deployment](#continuous-deployment)
2. [Repository Structure](#repository-structure)
3. [AppDirect Integration](#appdirect-integration)
4. [How to Build](#how-to-build)
5. [How to Launch](#how-to-launch)
6. [Assumptions](#assumptions)
7. [Design, Implementation & Technical Decisions](#design-implementation-technical-decisions)



<a name="continuous-deployment"/>
1) Continuous Deployment 
=============================
This github repository is "monitored" by Heroku and is automatically deployed to https://alexbt-appdirect-sampleapp.herokuapp.com at each commit.

To access the application, open a browser to https://alexbt-appdirect-sampleapp.herokuapp.com


<a name="repository-structure"/>
2) Repository structure
===========================
The repository is composed of 3 projects, built by an aggregator pom.xml (not a parent structure):

- appdirect-notifications-model;
- springboot-autoconfigure-oauth;
- sampleapp.

I set up this structure so that it is easy to build (in case anyone needs to rebuild it). After the challenge, I will convert this into 3 repositories and deploy the model and openid/oauth to maven centrl. Both projects will then be fully reusable for other Applications (not just this 'Sample Application').

<a name="appdirect-integration"/>
3) AppDirect Integration
=============================
The application supports several AppDirect integration points:

- **Create**: https://alexbt-appdirect-sampleapp.herokuapp.com/api/subscription/create/notification?url={eventUrl} 

- **Change**: https://alexbt-appdirect-sampleapp.herokuapp.com/api/subscription/change/notification?url={eventUrl}

- **Cancel**: https://alexbt-appdirect-sampleapp.herokuapp.com/api/subscription/cancel/notification?url={eventUrl}

- **Notice**: https://alexbt-appdirect-sampleapp.herokuapp.com/api/subscription/notice/notification?url={eventUrl}

- **User Assignment**: https://alexbt-appdirect-sampleapp.herokuapp.com/api/user/assignment/notification?url={eventUrl}

- **User Unassignment**: https://alexbt-appdirect-sampleapp.herokuapp.com/api/user/unassignment/notification?url={eventUrl}

- **OpenId**: https://alexbt-appdirect-sampleapp.herokuapp.com/login/openid/appdirect

- **OAuth**
    - For the purpose of this code challenge, the 'key' and 'secret' are kept in plain text in this repository ([application.yml](https://github.com/alexturcot/alexbt-appdirect-sampleapp/blob/master/sampleapp/src/main/resources/application.yml)).
    - OAuth Consumer Key: alexbts-sampleapp-145787
    - OAuth Consumer Secret: V88fbRJpfk3QRJzH


<a name="how-to-build"/>
4) How to Build
==================
Building and running the application requires:

- JDK 8
- Maven 3.x
- git
- Minimal console skills :)

After downloading the project from github, open a console into the unzipped folder and type:
    
    mvn clean install
    
If you wish to generate code analysis reports, you may type:
    
    mvn clean install site

This will generate several reports, (see `./target/site/project-reports.html`):

- `jacoco` for unit test coverage;
- `checkstyle` for coding convention;
- `findbugs` for static code analysis;
- `apidocs` for the javadoc API documentation.


<a name="how-to-launch"/>
5) How to Launch
================
After building the application, go to `./sampleapp/target` folder and type:

    java -jar sampleapp-1.0.0.jar



<a name="assumptions"/>
6) Assumptions
=================
- **Response HttpStatus**: All the answer (Success and Error) are returned with HttpStatus 200, except authorization errors which return 401s.

- **Creator**: I defined the 'creator' field as mandatory for 'Create' notification and optional for all other notifications;

- **Account**: I defined the payload.account field as mandatory for all notifications except 'Create';

- **Payload**: I assumed the 'payload' field was required for every notification;

- **Payload.User**: I assumed the 'Payload' User was always provided for 'Assign' and 'Unassign' User;

- **Company**: I was unsure if the Company field was always provided. I chose to support empty/null company field;

- **Company's fields**: Email, phoneNumber and website are identified as "required" in the documentation. However after setting empty values for these fields in my user profile, the integration tests were sending notifications with null values. I thus chose to support empty values for email, phoneNumber and website;

- **Concurrent Order Notification**: I tested concurrent Order sent from different users within the same company (I logged in twice with 2 different users and simultaneously sent Subscription Order Notifications from the 'Integration Tests' page. While the first Order notification was being processed, the second notification immediately returned with an error. AppDirect remained consistent and only the first Order was considered valid. However, my application received both Order event and created 2 distinct subscriptions for 2 users.
I did not cover for this scenario. I can only suppose I could reject simultaneous Order from the same Company, however I did not see this requirement anywhere and wouldn't want to implement such restriction for such a corner case without a deeper understanding.  
 
 

<a name="design-implementation-technical-decisions"/>
7) Design, Implementation & Technical Decisions
====================================
- **Focus**: My main focus during development was to ensure that the code is testable;

- **3 Projects**: I split the application into 3 projects: OAuth/OpenId configuration, AppDirect Notification Model and the actual Application. The OAuth/OpenId and Notification Model would be reusable for the development of another Marketplace Application. I reunited the 3 projects into a single repository just for the purpose of this challenge. I'll later split them into 3 distinct repositories: each would have its own development and deployment lifecycle;

- **Model Project**: I kept the Notification Model agnostic of any framework, so that it can be used with non-spring applications;

- **OpenId/OAuth Project**: This project is a Spring Boot Auto-configuration. It kicks in just by adding its dependency to a Spring Boot project;

- **SampleApp Project**: The actual Application project. It contains a ReactJS web UI and a Spring Boot 'backend' (with spring-data-mongodb).

- **Account Identifier**: I used a generated UUID for the unique Account Identifier;

- **Application configuration**: Most (if not all) application configuration is done in the Spring Boot's [application.yml](https://github.com/alexturcot/alexbt-appdirect-sampleapp/blob/master/sampleapp/src/main/resources/application.yml);

- **Static Code Analysis**: The build generates checkstyle, findbugs and jacoco reports. I used them to keep the code clean, avoid obvious bugs and ensure critical components were tested;

- **TDD**: I somewhat followed the TDD methodology, not so much writing tests 'first' but still writing unit tests as I designed & wrote code.

- **CSRF**: I chose to disable CSRF for the purpose of this Sample Application;

- **Oauth Key and Secret**: These values are hardcoded in the application's configuration for the purpose of this challenge. A better alternative would have been to define the values as Heroku environment variables;

- **MongoDb**: The application is deployed on heroku as an executable spring boot jar and uses a MongoDB instance on mlab.com. For the purpose of this challenge, the user/password are hardcoded in the configuration. A better alternative would be to define the password as an Heroku environment variable;

- **Continuous deployment**: Throughout the development, the Application was continuously being deployed to Heroku at every commit on GitHub (same setup using bitbucket with 'pipelines' during development).

