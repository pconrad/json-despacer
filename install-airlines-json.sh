#!/usr/bin/env bash

echo "Downloading Airlines json"
wget "https://think.cs.vt.edu/corgis/json/airlines/airlines.json?forcedownload=1"
mv "airlines.json?forcedownload=1"  airlines.json


