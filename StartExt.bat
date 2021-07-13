@echo on
cd classes
java StartExtension %1 >> ..\src\Extension%2.java
cd ..
