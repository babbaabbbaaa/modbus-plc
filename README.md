## Project Description and Goals

MODBUS-PLC is a project which is using Modbus protocol, using react to display the plc data and interaction with plc. 
This project is a tools for manufacturing. 
Currently, this project supports stamping and die-casting, this two scenarios, next step, maybe we can support more scenarios. 


## Change Log
- 1.0 support the modbus protocols 
- 1.1 added the React features in project 
- 1.2 added the heart-beat with plc
- 1.3 add RBAC support(spring security) in project to add the config 
- 1.4 add stamping manufacturing 
- 1.5 add dis-casting manufacturing
- 2.0(latest) upgrade the project to springboot 3.0 and jdk 17

## Dynamic PLC data visualization
Communicate with PLC via Modbus, fetch data from PLC in real-time
Created a diagram to display the data from PLC with data validation, show the error data by different color (like red)

## Heart-beat with PLC
Created a heart-beat with plc from frontend to check if the PLC connection is alive. 

## Bytearray data converted into different data type
- int
- short
- long
- string
Using ByteBuffer to do the converter. 

## Interaction with PLC
There will be a manufacture issue prompted from plc. We can find the issues from web page and do the fix from web page and the fix will be sync to plc via modbus protocol.


## Building Guideline

### Stamping 
- frontend: npm install; npm run build-stamping
- backend: mvn clean package -Dapplication.final.name=plc_stamping

### Die-Casting
- frontend: npm install; npm run build-casting
- backend: mvn clean package -Dapplication.final.name=plc_casting

## Install Guideline
- MySQL: 8.0, JPA will create the table automatically
- JDK: 17
- java -Dspring.profiles.active=dev,{stamping|casting} -jar {jar-name} 



