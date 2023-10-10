# shopping-elephant
## Overview

## Service

## User Interface
**TBD**

## Controller


## Development
This application is built with the environment bellow;
### Back end
- OpenJDK 17.0.7
- Spring boot 3.1.1
- IntelliJ IDEA 2023.1.2
- embedded H2DB 2.2.220

### Front end
**TBD**

### Architecture
The architecture of this program is based on Domain driven development model.
There is only one aggregate model to manage the expiration date.

This application is built with the Clean Architecture, this is the first attempt for me to develop with this model.
- adapter : perform as a gateway that input in UI
- application : contains use-case, perform as a business logic
- domain : entity, model(enum), specification, that describes the knowledge of components


### Aggregate pattern