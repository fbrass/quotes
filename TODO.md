Open points
===========

* Remove unused messages.properties.

* Password reset logic
* Let user enter passwords twice to ensure he/she/it meant really the right password!
* Implement own UserService for spring security to prevent circumventing the logic via the DB:
    http://stackoverflow.com/questions/10607696/spring-security-custom-userdetails

* Rename test classes according to their corresponding target classes.

* Check salting of password: http://static.springsource.org/spring-security/site/docs/3.1.x/reference/core-services.html#d0e3021

* Eliminate email in activation link, as it degrades entropy?

* Exponential back off for sending scheduled passwordResetAttempt mails

* Create schedule for registered but not activated users, drop user if not activated in time interval since passwordResetAttempt (e.g. 14 days)

* Maybe create a security package to better separate the sec

* Firstname and lastname attributes for users table et al.
* Unregister, delete account logic
* Migrate type SQL:timestamp to JodaTime instance
* Provide way to disable app globally either via DB or with special file: google for better solution
* Migrate hardcoded text in JSP to Messages.properties?

EOF
