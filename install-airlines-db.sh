#!/usr/bin/env bash

echo "Downloading Airlines db"
wget "https://think.cs.vt.edu/corgis/java/airlines/airlines.db?forcedownload=1"
mv "airlines.db?forcedownload=1"  airlines.db


