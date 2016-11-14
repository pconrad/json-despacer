#!/usr/bin/env bash

echo "Downloading json from Corgis website"
wget "https://think.cs.vt.edu/corgis/json/graduates/graduates.json?forcedownload=1" \
     -O graduates.json 



