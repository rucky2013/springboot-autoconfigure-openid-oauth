Table of contents
=================
1. [Continuous Deployment](#continuous-deployment)
2. [Repository structure](#repository-structure)
3. [AppDirect Integration](#appdirect-integration)
4. [How to Build](#how-to-build)
5. [How to Launch](#how-to-launch)
6. [Assumptions](#assumptions)
7. [Design, Implementation & Technical Decisions](#design-implementation-technical-decisions)
8. [Tests](#tests)



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

I setup this structure so that it is easy to build (in case anyone needs to rebuild it). After the challenge, I will convert this into 3 repositories. The appdirect-notifications-model and springboot-autoconfigure-oauth will then be fully reusable for other Applications (not just this 'Sample Application').

<a name="appdirect-integration"/>
3) AppDirect Integration
=============================
The application supports several AppDirect integration points:

**create:**
https://alexbt-appdirect-sampleapp.herokuapp.com/api/subscription/create/notification?url={eventUrl} 

**change:**
https://alexbt-appdirect-sampleapp.herokuapp.com/api/subscription/change/notification?url={eventUrl}

**cancel:**
https://alexbt-appdirect-sampleapp.herokuapp.com/api/subscription/cancel/notification?url={eventUrl}

**status/notice**
https://alexbt-appdirect-sampleapp.herokuapp.com/api/subscription/notice/notification?url={eventUrl}

**User Assignment**
https://alexbt-appdirect-sampleapp.herokuapp.com/api/user/assignment/notification?url={eventUrl}

**User Unassignment**
https://alexbt-appdirect-sampleapp.herokuapp.com/api/user/unassignment/notification?url={eventUrl}

**OpenId URL (to support Login initiated by the Marketplace)**
https://alexbt-appdirect-sampleapp.herokuapp.com/login/openid/appdirect?login=login

**OAuth key and secret**
- For the purpose of this code challenge, the 'key' and 'secret' are kept in plain text in this repository (application.yml).
OAuth Consumer Key: testing-133596
OAuth Consumer Secret: IkFEuaOGAYIivsEP


<a name="how-to-build"/>
4) How to build
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
- HttpStatus 200: All the answer (success and failure) are returned with HttpStatus 200, except authorization error which returns 401s.

- Creator: I defined the 'creator' field as mandatory for Create notification (optional for all other notification);

- Account: I defined payload.account as mandatory for all notification except Create;

- Payload: I assumed the 'payload' section was required for every notification;

- Payload.User: I assumed the 'Payload' User was always provided for 'Assign' and 'Unassign' User;

- Payload.Configuration: I did not include the Configuration field in the Notification model. I was unsure of its structure and the Integration Tests did not include it;

- Company: I was unsure if the Company field was always provided. I chose to support empty/null company field;

- Company's fields: Email, phoneNumber and website are identified as "required". However after setting empty values for these fields in my user profile, the integration tests was sending notification with null values. I chose to support empty values for email, phoneNumber and website;

- Concurrent Order Notification: I tested concurrent Order sent from different user within the same company (I logged in twice with 2 different users, and simultaneously sent a 'Subscription Order Event' from the 'Integration Tests' tool. While the first Order notification was being processed, the second notification immediately returned with an error. AppDirect remained consistent and only the first Order was considered valid. However, my application received both Order event and created 2 distinct subscriptions for 2 users.
I did not cover for this scenario, I can only suppose I could reject simultaneous Order from the same Company, however I did not see this requirement anywhere and wouldn't to implement such restriction for a corner case.  
 
 

<a name="design-implementation-technical-decisions"/>
7) Design, Implementation & Technical Decisions
====================================
- Focus: My main focus during development was to ensure that the code is testable, simple and reusable;

- 3 Projects: I split the application into 3 projects: OAuth/OpenId configuration, AppDirect Notification Model and the actual Application. The OAuth/OpenId and Notification Model would be reusable fit the development of another Marketplace Application. I reunited the 3 projects into a single repository just for the purpose of this challenge. I'll later split them into 3 distincts repository: each would have its own development and deployment lifecycle;

- Model Project: I kept the Notification Model agnostic of any framework, so that is can be used with non-spring applications;

- OpenId/OAuth Project: This project is a Spring Boot Autoconfiguration, it kicks in just by adding its dependency to a Spring Boot project;

- Account Identifier: I used a generated UUID for the unique Account Identifier;

- Application configuration: Most (if not all) application configuration is done in the Spring Boot's application.yml;

- Static Code Analysis: The build generates checkstyle, findbugs and jacoco reports. I used them to keep the code clean, avoid obvious bugs and ensure critical components were tests;

- TDD: I somewhat followed the TDD methodology, not so much writing tests 'first' but still writing unit tests as I designed & wrote code.

- CSRF: I chose to disable CSRF for the purpose of this Sample Application;

- Oauth Key and Secret: These values are hardcoded in the application's configuration for the purpose of this challenge. A better alternative would have been define the values as Heroku environment variables;

- Deployed: The application is deployed on heroku as an executable spring boot jar and uses a MongoDB instance on mlab.com. For the purpose of this challenge, the user/password are hardcoded in the configuration. A better alternative would be to define the password as an Heroku environment variable;

- Continuous deployment: Throughout the development, the Application was continuously being deployed to Heroku at every commit (was using bitbucket with pipelines during development).



<a name="tests"/>
8) Tests (TODO)
====================
###Unit tests
###Integration Reports
###Account section
- Upcoming Invoice
- Suspend Account
- Reactivate Account
- Cancel Account

###Run Ping Tests
