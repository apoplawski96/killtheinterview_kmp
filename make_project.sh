#!/bin/bash

# List of Gradle tasks to execute (excluding :composeApp:testClasses and :wasmApp:testClasses)
GRADLE_TASKS=(
  ":composeApp:assembleDebug"
#  ":composeApp:assembleDebugUnitTest"
#  ":composeApp:assembleDebugAndroidTest"
#  ":composeApp:assemble"
)

# Gradle command
GRADLE_COMMAND="./gradlew"

# Build the command string
COMMAND="$GRADLE_COMMAND ${GRADLE_TASKS[*]}"

# Run the Gradle command
echo "Running Gradle tasks: ${GRADLE_TASKS[*]}"
$COMMAND

# Check if the command succeeded
if [ $? -eq 0 ]; then
  echo "Gradle tasks completed successfully."
else
  echo "Gradle tasks failed."
  exit 1
fi
