javac -cp bin;lib/* -d bin src/minirestwebservice/*.java
start java -cp bin;lib/* minirestwebservice.HTTPServer http://localhost:4434
java -cp bin;lib/* minirestwebservice.TestClient http://localhost:4434

pause