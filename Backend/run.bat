start cmd.exe /k "cd /d discovery-server & mvn spring-boot:run"
start cmd.exe /k "cd /d security & mvn spring-boot:run"
start cmd.exe /k "cd /d customer & mvn spring-boot:run"  
start cmd.exe /k "cd /d order  & mvn spring-boot:run"
start cmd.exe /k "cd /d orderitem & mvn spring-boot:run" 
start cmd.exe /k "cd /d product & mvn spring-boot:run"
echo done
pause 