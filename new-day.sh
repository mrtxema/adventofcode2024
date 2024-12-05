#!/bin/bash
if [ $# -eq 0 ]
  then
    echo "Missing day folder argument"
    exit 1
fi

DAY=$1
echo Creating module $DAY

mkdir $DAY
cp -r template/* $DAY
echo "include '$DAY'" >> settings.gradle

echo Module $DAY created
