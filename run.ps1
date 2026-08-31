# StockRoom needs Java 21. This PC's default `java` command is still Java 8.
$env:JAVA_HOME = Join-Path $env:USERPROFILE ".jdks\corretto-21.0.2"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path

Write-Host "Using Java from $env:JAVA_HOME"
java -version

# mvnw.cmd is the Maven Wrapper: it downloads Maven for you. You do not install Maven.
& "$PSScriptRoot\mvnw.cmd" spring-boot:run
