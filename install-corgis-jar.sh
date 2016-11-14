#!/usr/bin/env bash

function usage {
    echo "Usage: install-corgis-jar.sh lib-name"
    echo "  Example: install-corgis-jar.sh airlines"
}

function download {
    wgetStatus=0
    targetjar="$1-1.0.jar"
    if [[ ! -f "$targetjar" ]]; then
	url="https://think.cs.vt.edu/corgis/java/$1/dist/$1-doc-1.jar?forcedownload=1"

	echo "Downloading $targetjar from: $url"

	wget $url -O $targetjar
	wgetStatus=$?
	[[ "$wgetStatus" -ne 0 ]] && rm -f "$targetjar" || echo "ok"
	echo "wgetStatus=$wgetStatus"
    fi
    return $wgetStatus
}

function mvn_deploy {
    mvn clean -U     
    mvn deploy:deploy-file \
	-Durl=file:`pwd`/repo -Dfile="$1-1.0.jar" -DgroupId=edu.vt.cs.corgis \
	-DartifactId="$1" -Dpackaging=jar -Dversion=1.0    
    mvn -U initialize
}

function main {
    download $1
    [ $? -ne 0 ] ||  mvn_deploy $1
}

[ "$#" -ne 1 ] && ( usage && exit 1 ) || main "$1"
