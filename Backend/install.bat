start cmd.exe /k "cd /d discovery-server & mvn clean install"
start cmd.exe /k "cd /d security & mvn clean install"
start cmd.exe /k "cd /d customer & mvn clean install"  
start cmd.exe /k "cd /d order  & mvn clean install"
start cmd.exe /k "cd /d orderitem & mvn clean install" 
start cmd.exe /k "cd /d product & mvn clean install"
echo done
pause 