## modbus-plc

MODBUS-PLC is a project which is using Modbus protocol, using react to display the plc data and interaction with plc. 
This project is a tools for manufacturing. 
Currently, this project supports stamping and die-casting, this two scenarios, next step, maybe we can support more scenarios. 


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

