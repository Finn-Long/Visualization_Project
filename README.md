# Visualization_Project

There are two parts of this project: The Log Server and the Log Transformer.

- The log transformer 
1. get raw log data in json format and transforms them to the universal log format defined in the project
2. Stores the transformed logs to firebase

- The Log Server
1. host a REST API to respond to request
2. Get log from the firebase database where the transformer stores log data

Get Request Endpoints:
- logs/get_all_logs
Get all the logs stored in the database
- /logs/{name}
Get logs by student name
- /logs/milestones
Get all the logs that are milestones
- /logs/{name}/milestones
Get the logs that are milestone and belongs to the specific student
