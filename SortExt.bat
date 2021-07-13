@echo off
pushd classes
java InnerClassSorter ..\src\Extension%1.java ..\%2.java> ..\src\Extension%1.tmp
popd
