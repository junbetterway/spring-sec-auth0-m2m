# Java Spring Security Machine-To-Machine Communications using Auth0

Often times, a secure and authorized communication channel between different parts of an autonomous system is needed. For example, two backend services trying to communicate via REST API calls. For these cases, OAuth 2.0 provides the __client credentials grant flow__ to secure their communications.

In this repository, we will take a look at how the __client credentials grant flow__ from OAuth 2.0 can be used with __[Auth0](https://auth0.com/)__ for machine-to-machine (M2M) secured communications.

# What is Auth0?
__[Auth0](https://auth0.com/)__ helps anyone to do the following:

* Add authentication with multiple authentication sources, either social like Google, Facebook, Microsoft Account, LinkedIn, GitHub, Twitter, Box, Salesforce, among others, or enterprise identity systems like Windows Azure AD, Google Apps, Active Directory, ADFS or any SAML Identity Provider.
* Add authentication through more traditional username/password databases.
* Add support for linking different user accounts with the same user.
* Support for generating signed Json Web Tokens to call your APIs and flow the user identity securely.
* And more...

# Create a Free [Auth0](https://auth0.com/) Account
1. Go to __[Auth0](https://auth0.com/)__ and click __Sign Up__.
2. Use Google, GitHub or Microsoft Account to login.

# OAuth2 Resource Server
In the context of OAuth 2.0, a __Resource Server__ is an application that protects resources via OAuth tokens. These tokens are issued by an __Authorization server__, typically to a client application. The job of the __Resource Server__ is to validate the token before serving a resource to the client.

This repository treats this application as the __Resource Server__ and a REST API client tool (__e.g.__, Postman, Insomnia) will act as the other machine to simulate the Machine-To-Machine or M2M communications.

# Setup Auth0 API

Go to your __[Auth0](https://auth0.com/)__ Dashboard and make sure to follow these __[steps](https://auth0.com/docs/get-started/set-up-apis)__ on how to create an API on your dashboard. This will serve as the identifier of your trusted API (__e.g.__, external service) that is allowed to access your protected resources.

Make sure to update the __application.yml__ file below based on your API identifier above and your existing domain provided to you by Auth0 when you sign-up.

```
server:
  port: 8888
auth0:
  audience: {API_IDENTIFIER}
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://{DOMAIN}/
```

*__Note:__ The trailing slash is important on the issuer-uri component above.*

## Run the Spring Boot Application Using Spring Tool Suite (STS)
1. Download STS version 3.4.* (or better) from the [Spring website](https://spring.io/tools). STS is a free Eclipse bundle with many features useful for Spring developers.
2. Right-click on the project or the main application class then select "Run As" > "Spring Boot App"

## Testing The Protected API Resources
1. Using a REST API client such as Postman or cURL, issue a GET request to http://localhost:8888/api/account/public. One should receive the response:

```
{
    "name": "Jun King Minon",
    "remarks": "You DO NOT need to be authenticated here!!!",
    "balance": 5500
}
```

2. Next, issue a GET request to http://localhost:8888/api/account/private. One should receive a __401 Unauthorized__ response.
3. To test that your API is properly secured, you can obtain a test token in the __[Auth0](https://auth0.com/)__ Dashboard:
   3.1. Go to the __Machine to Machine Applications__ tab for the API you created above.
   3.2. Ensure that your API test application is marked as authorized.
   3.3. Click the __Test__ tab, then COPY TOKEN.
   3.4. Issue again a GET request to the http://localhost:8888/api/account/private, this time passing the token you obtained above as an Authorization header set to                     Bearer YOUR-API-TOKEN-HERE. One should then see the response:

```
{
    "name": "Jun King Minon",
    "remarks": "You can see this because you are authenticated!!!",
    "balance": 5500
}
```

4. Finally, to test that the http://localhost:8888/api/account/private-scoped is properly protected by the __read:acccounts__ scope, make a GET request to http://localhost:8888/api/account/private-scoped using the same token as above. One should see a __403 Forbidden__ response, as this token does not possess the  __read:acccounts__ scope.
5. To test that the above API is properly secured, go back to __[Auth0](https://auth0.com/)__ Dashboard:
   5.1. Go to the __Permissions__ tab for the API you created above.
   5.2. Add a permission of __read:acccounts__ and provide a description.
   5.3. Go to the __Machine to Machine Applications__ tab.
   5.4. Expand your authorized test application and select by ticking the __read:acccounts__ scope, then click __UPDATE__ and then __CONTINUE__.
   5.5. Click the __Test__ tab, then COPY TOKEN.
   5.6. Issue a GET request to http://localhost:8888/api/account/private-scoped, this time passing the token you obtained above (with the __read:acccounts__ scope) as an 
        Authorization header set to Bearer YOUR-API-TOKEN-HERE. One should see the response:

```
{
    "name": "Jun King Minon",
    "remarks": "You can see this because you are authenticated with a token granted the 'read:accounts' scope",
    "balance": 5500
}
```

## Powered By
Contact me at [junbetterway](mailto:jkpminon12@yahoo.com)

Happy coding!!!
