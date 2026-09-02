@echo off
setlocal
if defined MAVEN_HOME (
    "%MAVEN_HOME%\bin\mvn.cmd" %*
) else if exist "%USERPROFILE%\.maven\apache-maven-3.9.6\bin\mvn.cmd" (
    "%USERPROFILE%\.maven\apache-maven-3.9.6\bin\mvn.cmd" %*
) else (
    mvn %*
)
