javac -cp bin;lib/* -d bin src/minirestwebservice/*.java
start java -cp bin;lib/* minirestwebservice.HTTPServer http://localhost:4434
;rem java -cp bin;lib/* minirestwebservice.TestClient http://localhost:4434 ich
start http://localhost:4434/test/regUser?name=ich^&age=20
start http://localhost:4434/test/regUser?name=du^&age=30
start http://localhost:4434/test/createEvent?desc="heute20:00fussballschauenbeimir"
start http://localhost:4434/test/getEvents
start http://localhost:4434/test/getUsers
start http://localhost:4434/test/addUsertoEvent?eventID=1^&userID=1
;rem start http://localhost:4434/test/addUsertoEvent?eventID=1^&userID=2
start http://localhost:4434/application.wadl
pause