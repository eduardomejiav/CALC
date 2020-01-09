#!/bin/bash
sh ./shutdown.sh
echo "====================================================="
echo "====================================================="
echo "====================================================="
echo "Starting... Build Application Jar"
mvn clean install -f $PWD/myApp/pom.xml
sleep 5
echo "End... Build Application Jar"
echo "====================================================="
echo "====================================================="
echo "====================================================="
echo "Starting... Build docker images"
docker-compose build
sleep 5
echo "End... Build docker images"
echo "====================================================="
echo "====================================================="
echo "====================================================="
echo "Starting... print docker-compose config"
docker-compose config
echo "End... print docker-compose config"
echo "====================================================="
echo "====================================================="
echo "====================================================="
echo "Starting... docker-compose"
docker-compose up -d
echo "End... docker-compose"
echo "====================================================="
echo "====================================================="
echo "====================================================="
docker-compose logs -f