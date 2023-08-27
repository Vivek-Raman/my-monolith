#!/bin/sh

# Upload this file to https://console.cloud.google.com/storage/browser/_details/artifact-jars/instance-startup.sh;tab=live_object?project=my-monolith-v3
# https://github.com/GoogleCloudPlatform/community/blob/master/archived/kotlin-springboot-compute-engine.md

SECRETS_FILE=secrets.sh
TARGET_FILE=my-monolith.jar

echo "Starting deployment!"

gsutil cp gs://artifact-jars/"${TARGET_FILE}" .

apt-get update
apt-get -y --force-yes install openjdk-17-jre

update-alternatives --set java /usr/lib/jvm/java-17-openjdk-amd64/jre/bin/java

gsutil cp gs://artifact-jars/"${SECRETS_FILE}" .
sh "${SECRETS_FILE}"
rm "${SECRETS_FILE}"

echo "Starting app!"

java -jar ./${TARGET_FILE} -Xmx512m -Dapplication.production=true -Dspring.main.banner-mode=off

echo "Done!"
