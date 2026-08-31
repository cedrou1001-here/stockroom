# Starts StockRoom. Do not double-click this file — Windows will open Notepad.
# In Cursor's terminal or PowerShell:
#   cd "C:\Users\Master Cedrik\Projects\stockroom"
#   .\run.ps1
#
# Pins Java 21 (Corretto) for this window even if PATH is messy.
$env:JAVA_HOME = Join-Path $env:USERPROFILE ".jdks\corretto-21.0.2"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path

Write-Host "Using Java from $env:JAVA_HOME"
java -version

# mvnw.cmd downloads Maven for you. You do not install Maven yourself.
& "$PSScriptRoot\mvnw.cmd" spring-boot:run
