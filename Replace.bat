@echo off
cd classes
java Replacer %1.java > %1.tmp
cd ..
