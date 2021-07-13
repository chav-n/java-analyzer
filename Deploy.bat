@echo off
erase \tmp\classes\* /q
pushd src
call javac1 %1.java -d \tmp\classes
cd \tmp\classes
jar cvfe \bin\%1.jar %1 *
popd
