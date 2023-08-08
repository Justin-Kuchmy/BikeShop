start cmd.exe /k "cd /d backend/discovery-server & mvn clean install"
start cmd.exe /k "cd /d backend/security & mvn clean install"
start cmd.exe /k "cd /d backend/customer & mvn clean install"  
start cmd.exe /k "cd /d backend/order  & mvn clean install"
start cmd.exe /k "cd /d backend/orderitem & mvn clean install" 
start cmd.exe /k "cd /d backend/product & mvn clean install"
echo done
pause 