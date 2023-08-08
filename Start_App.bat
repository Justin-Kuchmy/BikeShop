start cmd.exe /k "cd /d backend/discovery-server & mvn spring-boot:run"
start cmd.exe /k "cd /d backend/security & mvn spring-boot:run"
start cmd.exe /k "cd /d backend/customer & mvn spring-boot:run"  
start cmd.exe /k "cd /d backend/order  & mvn spring-boot:run"
start cmd.exe /k "cd /d backend/orderitem & mvn spring-boot:run" 
start cmd.exe /k "cd /d backend/product & mvn spring-boot:run"
start cmd.exe /k "cd /d frontend & ng serve"
echo Running on http://localhost:4200
pause 