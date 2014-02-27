Spring Security ACL
============

To run this example, follow the next steps:

- create a database called "security" in your mysql server
- run the acl_mysql.sql script into your new database. You can run this using the source command like this: source path.sql
- Open the UserTest class and chance the @Transactional by @TransactionConfiguration. Run the test and back it again to @Transactional. Check out on spring.io the difference between both annotations.
- Do the same above on MenuTest class.
- Now run the application with the following maven command: compile -Djetty.port=8081 jetty:run
