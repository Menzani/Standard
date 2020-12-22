call "C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\vcvarsall.bat" x64
cl /I "%JAVA_HOME%\include" /I "%JAVA_HOME%\include\win32" /Fe"..\res\Standard_64" /LD Native_windows.cpp
call "C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\vcvarsall.bat" x86
cl /I "%JAVA_HOME%\include" /I "%JAVA_HOME%\include\win32" /Fe"..\res\Standard_32" /LD Native_windows.cpp

del Native_windows.obj
del ..\res\Standard_32.exp
del ..\res\Standard_32.lib
del ..\res\Standard_64.exp
del ..\res\Standard_64.lib
