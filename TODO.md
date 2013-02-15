Open points
===========

* Password forget logic
* Implement own UserService for spring security to prevent circumventing the logic via the DB:
    http://stackoverflow.com/questions/10607696/spring-security-custom-userdetails
* Firstname and lastname attributes for users table et al.
* Unregister, delete account logic
* Exponential back off for sending scheduled registration mails
* Migrate type SQL:timestamp to JodaTime instance
* Provide way to disable app globally either via DB or with special file: google for better solution
* Check salting of password: http://static.springsource.org/spring-security/site/docs/3.1.x/reference/core-services.html#d0e3021
* Eliminate email in activation link, as it degrades entropy.

EOF
