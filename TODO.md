Open points
===========

* ActivationOutstandingSchedulerTest missing test for successfull outstanding activation mail. Ensure schedule is _not_ purged.

* Use GreenMail for unit testing. This way we may get the last message received and thus may eliminate our MailSender stuff contained in testing.

* User related Tests use unencoded Passwords. Think of changing this, and what it provides.

* Integrate example admin page with spring security, does not work currently.

* Exponential back off for sending scheduled passwordResetAttempt mails. Use strategy pattern.

* Create schedule for password reset enabled accounts, where the password is not reset for say (5 hours): delete token.

* Firstname and lastname attributes for users table et al.

* Unregister, delete account logic

* Migrate type SQL:timestamp to JodaTime instance

* Provide way to disable app globally either via DB or with special file: google for better solution

* Local dummy mail server for running/testing on machines without SMTP. Check http://code.google.com/p/subethasmtp/.

EOF
