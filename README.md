# IoT-Simulator
IoT sensor device simulator

Simulation of three IoT sensor devices, that record information about time, location, and time linked to the ID of the sensing device. this information once recorded is sent to a machine which is connected to a processor machine via java socket. 

The information is packed and sent from the user socket to the server, where the information is unpacked and store in a relational database. 

Once this is done, the following queries are performed over the database to collect the shown information.

**QUERY 1: The maximum temperatures measured for every device**

*castedData.groupBy("device-id").max(TEMPERATURE).orderBy(ID).show();*

<p align = "center">
  <img src= "https://github.com/RonMen10/IoT-Simulator/blob/main/images/q1.png" width = "300" height = "150" / >

**QUERY 2 :The amount of data points aggregated for every device.**

*castedData.groupBy("device-id").count().show();*

<p align = "center">
  <img src= "https://github.com/RonMen10/IoT-Simulator/blob/main/images/q2.png" width = "260" height = "130" / >



**QUERY 3 :The highest temperature measured on a given day for every device.**

*String givenDay = "09/06/2020";*
 
 *Dataset<Row> castedData2 = castedData.where(col("timestamp").$greater$eq(timestampConverter(givenDay)));*
  
  *castedData2.where(col("timestamp").$less$eq(timestampConverter(givenDay) + 86400)).groupBy("device-id").max(TEMPERATURE).orderBy(ID).show();*

<p align = "center">
  <img src= "https://github.com/RonMen10/IoT-Simulator/blob/main/images/q3.png" width = "300" height = "150" / >
