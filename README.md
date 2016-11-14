# sparkjava-corgis-graduates-demo

A demo of:

* a [SparkJava webapp](http://pconrad-webapps.github.io/topics/sparkjava/),
* that provides a front end to a backend [MongoDB database](http://pconrad-webapps.github.io/topics/mongodb/),
* loaded with JSON data from the [Virginia Tech Corgis Project](https://think.cs.vt.edu/corgis/json/index.html)

# Requires Java 1.8, and Maven (`mvn` command)

To build, use `mvn package`

To run, use `java -jar target/CorgisGraduatesWebapp-1.0-SNAPSHOT.jar`


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
    ./install-corgis-jar.sh food_access
    ```

    After you do, check the repo directory.  You should see that there is a new
    subdirectory for your library, as shown here:

    ```
    $ ls repo/edu/vt/cs/corgis/
    food_access   graduates
    $
    ```

    You will want need to `git add` all of the files in the new subdirectory
    (in this example `food_access`) into the github repository.  This makes it available
    to Maven when you push the repository to Heroku.
    You will need to use `git add -f ` to add the `.jar` file in that directory.

3. Change the model files under  `src/main/java/org/pconrad/corgis/graduates` by
   renaming the `graduates` package, then finding the files under `models`, both
   `GraduateDB.java` and `GradMajorPlus.java`.   You will need to consult the
   JSON documentation and the Javadoc for the dataset you are using to determine what fields
   are accessible.

4. Under `src/main/java/org/pconrad/corgis/graduates`, locate the file
    `webapp/CorgisGraduatesWebapp.java`. This is the "controller" in your application.
    Rename both the package, file, and class
    as needed, and edit the contents.  You'll want to change the routes to ones that
    make sense for your application.

    For example, you might end up with:

    ```
    src/main/java/org/pconrad/corgis/food_access/webapp/CorgisFoodAccessWebapp.java
    ```

    You will need to coordinate the changes here with changes to the model and view.


5. To change the "View", change the template files in src/main/resources/templates as follows:

    * In `nav.mustache`, change the routes to ones that match the ones you put in the
        new version of `CorgisGraduatesWebapp.java`.
    * Change the name and contents of files such as `lookup.majorcode.mustache` and
      `lookup.majorcode.result.mustache` to ones that make sense for your application.
    * Coordinate the content with the contents of tje

6. Edit the pom.xml to change the dependency for the corgis jar file needed for
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

7. Also, in the pom.xml, change the name of:

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


8. Edit the `Procfile` to specify the name of the `.jar` file.

   For example, instead of:

   ```
   web:    java -jar target/CorgisGraduatesWebapp-1.0-SNAPSHOT.jar
   ```

   You might have:

   ```
   web:    java -jar target/CorgisFoodAccessWebapp-1.0-SNAPSHOT.jar
   ```

9. Edit this README to replace the command to run the app.


10. If you don't already have a MongoDB database set up called `corgis`, and a
    document set up for your data with the same name as the dataset (e.g. `airlines`,
    `graduates`, `foodaccess`), then set that up now.  Consult
    the [pconrad.webapps MongoDB](https://pconrad-webapps.github.io/topics/mongodb)
    documentation as needed.

11. Copy from the `env.sh.EXAMPLE` to `env.sh` and edit the values inside.
    Then use this shell command to define the needed environment variables:

    ```
    . env.sh
    ```

12. Upload the data from the .json file to the MongoDB server using a command such as
    the following.  Change the name of the collection and json file as needed:

    ```
    java -cp target/CorgisGraduatesWebapp-1.0-SNAPSHOT.jar org.pconrad.corgis.utilities.UploadToMongoDB corgis graduates graduates.json
    ```

