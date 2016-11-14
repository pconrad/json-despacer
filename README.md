# sparkjava-corgis-graduates-demo

A demo of:

* a [SparkJava webapp](http://pconrad-webapps.github.io/topics/sparkjava/),
* that provides a front end to a backend [MongoDB database](http://pconrad-webapps.github.io/topics/mongodb/),
* loaded with JSON data from the [Virginia Tech Corgis Project](https://think.cs.vt.edu/corgis/json/index.html)

# Requires Java 1.8, and Maven (`mvn` command)

To build, use `mvn package`

To run, use `java -jar target/spark-template-mustache-2.4-SNAPSHOT.jar`

Demo on Heroku at: https://sparkjava-corgis-graduates-demo.herokuapp.com/


# How to adapt this to another Corgis dataset

1. Edit the file [download-json.sh](download-json.sh) and change the URL to download from,
   and the name of the dataset from `graduates.json` to whatever dataset you are downloading.

   For example, instead of:

   ```
   wget "https://think.cs.vt.edu/corgis/json/graduates/graduates.json?forcedownload=1" \
     -O graduates.json
   ```

   You might have:

   ```
   wget "https://think.cs.vt.edu/corgis/json/food_access/food_access.json?forcedownload=1" \
        -O food-access.json
   ```

2. To download and install the `.jar` for the library, run the `./install-corgis-jar.sh`
    script, passing in the name of the library as a parameter.

    For example:

    ```
    ./install-corgis-jar.sh food-access
    ```

    After you do, check the repo directory.  You should see that there is a new
    subdirectory for your library, as shown here:

    




7. Edit the pom.xml to change the dependency for the corgis jar file needed for
   the library you are accessing.  For example, instead of:

    ```xml
      <dependency>
        <groupId>edu.vt.cs.corgis</groupId>
        <artifactId>graduates</artifactId>
        <version>1.0</version>
      </dependency>
    ```

    You might have:
    

    ```xml
      <dependency>
        <groupId>edu.vt.cs.corgis</groupId>
        <artifactId>food-access</artifactId>
        <version>1.0</version>
      </dependency>
    ```

8. Also, in the pom.xml, change the name of:

    * The `.jar` file you are creating
    * The main class that jar file will execute

    For example, instead of:

    ```
    <artifactId>CorgisGraduatesWebapp</artifactId>
    ...
    <name>corgis-graduates-webapp</name>
    ...
      <mainClass>org.pconrad.corgis.graduates.webapp.CorgisGraduatesWebapp</mainClass>

    ```

    You might have:

    ```
    <artifactId>CorgisFoodAccessWebapp</artifactId>
    ...
    <name>corgis-food-access-webapp</name>
    ...
      <mainClass>org.pconrad.corgis.graduates.webapp.CorgisFoodAccessWebapp</mainClass>
    ```


9. Edit the `Procfile` to specify the name of the `.jar` file.

   For example, instead of:

   ```
   web:    java -jar target/CorgisGraduatesWebapp-1.0-SNAPSHOT.jar
   ```

   You might have:

   ```
   web:    java -jar target/CorgisFoodAccessWebapp-1.0-SNAPSHOT.jar
   ```




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

