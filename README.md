# sparkjava-corgis-airlines-demo


Minimal demo of SparkJava using mustache templating and a bootstrap based UI.

Requires Java 1.8, and Maven (`mvn` command)

To build, use `mvn package`

To run, use `java -jar target/spark-template-mustache-2.4-SNAPSHOT.jar`

Demo on Heroku at: https://sparkjava-corgis-airlines-demo.herokuapp.com/

# JSON documentation

The JSON API used here is json-simple.

It appears that the current version of this in found in the following repo, but I'm not 100% confident
this is the authoritative source:

* https://github.com/fangyidong/json-simple


Javadoc for it can be found at several places, though again it isn't clear which, if any, is an authoritative
source; these appear to be volunteer efforts that could disappear at any time:

* http://alex-public-doc.s3.amazonaws.com/json_simple-1.1/index.html
* http://juliusdavies.ca/json-simple-1.1.1-javadocs/index.html?overview-summary.html

# Test of Corgis ReadJson

This sequence of commands:

```
./install-airlines-json.sh
./install-airlines.sh
mvn package
java -cp target/spark-template-mustache-2.4-SNAPSHOT.jar org.pconrad.corgis.airlines.demos.ReadJson

java -cp target/spark-template-mustache-2.4-SNAPSHOT.jar org.pconrad.corgis.airlines.model.AirlineDB
```

