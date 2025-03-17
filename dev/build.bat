@echo off
mkdir "../release" 2>nul
del /f /q "../release/adss_v0.jar" 2>nul
del /f /q "HelloWorld.class" 2>nul
echo Deleted class and jar files
javac HelloWorld.java
echo class file created
jar cfm "../release/adss_v0.jar" MANIFEST.MF HelloWorld.class
echo JAR file created in ../release/
pause
echo readme.txt: 
type ..\docs\readme.txt
pause