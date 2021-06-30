@ECHO OFF
SET mypath=%~dp0
java --module-path %mypath%javafx\lib --add-modules javafx.controls,javafx.fxml -jar %mypath%out\artifacts\testJavafx_jar\testJavafx.jar -cli