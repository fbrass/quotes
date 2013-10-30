Open points
===========

* 404 page
* Generic error page
* Check TODOs in source code
* User related Tests use unencoded Passwords. Think of changing this, and what it provides.
* Integrate example admin page with spring security, does not work currently.
* Firstname and lastname attributes for users table et al.
* Unregister, delete account logic
* Provide way to disable app globally either via DB or with special file: google for better solution
* Local dummy mail server for running/testing on machines without SMTP. Check http://code.google.com/p/subethasmtp/
  or use GreenMail for integration testing. This way we may get the last message received and thus may eliminate our
  MailSender stuff contained in testing.
* Fill README.md with content and in particular instructions how to run and develop in Eclipse and IntelliJ
* Create resource translation mechanism via DB (bootstrap on initial loading of the app)
* Finish user administration
* Update to spring-security 3.1.4.RELEASE, consider deprecated PasswordEncoder.
* Fix GWT warnings visible in devmode.
* Discover GWT testing.
* Generify displaying list of quotes.
* Make whole page controlled by GWT.
* Rename packages like 'listauthorquotes' to 'author' …

EOF
