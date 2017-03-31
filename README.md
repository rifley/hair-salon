# _Hair-Salon_

#### _Hair-Salon, 03-31-2017_

#### By _*Riley Watts*_

## Description
_This website will allow employers to add employees to their web roster and even go further to add a list of clients to each individual employee_


## Specifications

| Behavior                   | Input Example     | Output Example    |
| -------------------------- | -----------------:| -----------------:|
|add stylist|Janet|Stylists: Janet|
|add client to a specific stylist|Todd|Stylist:Janet, Client:Todd|
|delete stylist|Janet-d| |
|delete client|Todd-d| |


## Technologies

_Java_
_Gradle_
_VelocityTemplateEngine_
_Spark_


## Setup/Installation Requirements

* _Clone the repository_
* _In your command terminal, navigate to this repository_
* _Type "postgres"_
* _Open a new terminal window and type "psql"_
* _now type "CREATE DATABASE hair_salon;"_
* _In your original terminal window, type "psql media < hair_salon.sql_
* _Type "gradle run"_ 
* _Open browser and go to localhost:4567_


### License

Copyright (c) 2017 *Riley Watts*

This software is licensed under the MIT license.
