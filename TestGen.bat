@echo off
cd classes
java -cp \bin;. ParserJava %1 %2
cd ..
