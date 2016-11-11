#!/usr/bin/env bash

mvn clean -U 

if [ ! -f airlines-1.0.jar ]; then
  echo "Downloading jar"
  wget https://think.cs.vt.edu/corgis/java/airlines/dist/airlines-doc-1.jar?forcedownload\=1
  mv "airlines-doc-1.jar?forcedownload=1"  airlines-1.0.jar
fi

mvn deploy:deploy-file \
   -Durl=file:`pwd`/repo -Dfile=airlines-1.0.jar -DgroupId=edu.vt.cs.corgis \
   -DartifactId=airlines -Dpackaging=jar -Dversion=1.0

mvn -U initialize

