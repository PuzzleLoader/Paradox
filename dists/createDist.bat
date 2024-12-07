@echo off

set /p paradoxVersion=Paradox Version:
set /p cosmicReachVersion=Cosmic Reach Version:

java -Djbsdiff.compressor=gz -jar %~dp0jbsdiff-1.0-all.jar diff %~dp0../lib/Cosmic-Reach.jar %~dp0../build/libs/Paradox-1.0-SNAPSHOT-all.jar %~dp0jarpatch.patch
cd %~dp0
tar -v -a -c -f %~dp0paradox-%paradoxVersion%-alpha+%cosmicReachVersion%.zip jarpatch.patch jbsdiff-1.0-all.jar
del %~dp0jarpatch.patch