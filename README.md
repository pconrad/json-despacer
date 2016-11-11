# sparkjava-corgis-airlines-demo


Minimal demo of SparkJava using mustache templating and a bootstrap based UI.

Requires Java 1.8, and Maven (`mvn` command)

To build, use `mvn package`

To run, use `java -jar target/spark-template-mustache-2.4-SNAPSHOT.jar`

Demo on Heroku at: https://sparkjava-corgis-airlines-demo.herokuapp.com/


# Test of Corgis ReadJson

This sequence of commands:

```
./install-airlines-json.sh
./install-airlines.sh
mvn package
java -cp target/original-spark-template-mustache-2.4-SNAPSHOT.jar org.pconrad.corgis.airlines.demos.ReadJson
```

