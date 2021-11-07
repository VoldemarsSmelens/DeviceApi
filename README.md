# DeviceApi
## Startup
### Requirements
- Java 11 (Added JAVA_HOME in environment variables)
- Maven 3 (Added in path)

### Execution (Two options)
- Run StartUp.bat
- From project root folder run in CLI: mvn spring-boot:run -Dspring-boot.run.profiles=dev

### Links for application
- Swagger: http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
  - User: user
  - Password: user1234
- H2 db: http://localhost:8080/h2console/login.jsp
  - User: db
  - Password: db1234
  
## Default data provided for easier testing 
###(Can be removed by removeAllDevices method)
Response of getAllDevices for default data

  
    "deviceType": "GATEWAY",
    "macAddress": "10:10:10:10:10:02",
    "upLinkDeviceAddress": "10:10:10:10:10:01"
 
    "deviceType": "GATEWAY",
    "macAddress": "10:10:10:10:10:22",
    "upLinkDeviceAddress": "10:10:10:10:10:21"

    "deviceType": "SWITCH",
    "macAddress": "10:10:10:10:10:03",
    "upLinkDeviceAddress": "10:10:10:10:10:01"
 
    "deviceType": "SWITCH",
    "macAddress": "10:10:10:10:10:04",
    "upLinkDeviceAddress": "10:10:10:10:10:03"

    "deviceType": "ACCESS_POINT",
    "macAddress": "10:10:10:10:10:01",
    "upLinkDeviceAddress": null
 
    "deviceType": "ACCESS_POINT",
    "macAddress": "10:10:10:10:10:21",
    "upLinkDeviceAddress": null
  
###Topology provided as JSON structure

Response of getAllTopologies for default data (indented)

    [
        {
            "connectedDevices": [
                {
                    "deviceType": "GATEWAY",
                    "macAddress": "10:10:10:10:10:02"
                },
                {
                    "connectedDevices": [
                        {
                            "deviceType": "SWITCH",
                            "macAddress": "10:10:10:10:10:04"
                        }
                    ],
                    "deviceType": "SWITCH",
                    "macAddress": "10:10:10:10:10:03"
                }
            ],
            "deviceType": "ACCESS_POINT",
            "macAddress": "10:10:10:10:10:01"
        },
        {
            "connectedDevices": [
                {
                    "deviceType": "GATEWAY",
                    "macAddress": "10:10:10:10:10:22"
                }
            ],
            "deviceType": "ACCESS_POINT",
            "macAddress": "10:10:10:10:10:21"
        }
    ]
    
##Test coverage

![TestCoverageImage](TestCoverage.jpg?raw=true)


