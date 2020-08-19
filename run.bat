cd C:\Users\kalyan.somisetty\Desktop\KalsRest
call mvn clean install -DskipTests
java -jar C:\Users\kalyan.somisetty\Desktop\KalsRest\target\kalsrest-1.0.0.BUILD-SNAPSHOT-exec.jar
@START http://localhost:8080/