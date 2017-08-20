#/bin/sh

echo Searching for action bundle...
bundle=$(ls -A target/xup-openwhisk-examples*.jar 2> /dev/null)
if [[ -z "$bundle" ]]
then
  (>&2 echo "Error: bundle is missing, start build first!")
  exit 1;
fi

echo Searching for generated rewhisk script ...
script=$(ls -A target/generated-sources/rewhisk.sh 2> /dev/null)
if [[ -z "$script" ]]
then
  (>&2 echo "Error: Rewhisk script is missing, start build first!")
  exit 1;
fi

. $script "$bundle" 
