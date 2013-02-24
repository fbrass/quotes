Open points
===========

* User related Tests use unencoded Passwords. Think of changing this, and what it provides.

* Integrate example admin page with spring security, does not work currently.

* Exponential back off for sending scheduled passwordResetAttempt mails. Use strategy pattern.

* Create schedule for registered but not activated users, drop user if not activated in time interval since passwordResetAttempt (3 hours)

* Firstname and lastname attributes for users table et al.

* Unregister, delete account logic

* Migrate type SQL:timestamp to JodaTime instance

* Provide way to disable app globally either via DB or with special file: google for better solution

* Local dummy mail server for running/testing on machines without SMTP.

EOF
