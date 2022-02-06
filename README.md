# optily

To Run the application:
mvn spring-boot:run

Application will run using port 8080 http://localhost:8080

H2 database http://localhost:8080/h2-console/login.jsp    username=sa (no password required)

#APIs

GET /campaigngroup                                          --> Get all campaign groups

POST /campaigngroup/  Body { "name": "my campaign group"}   --> Create a new campaign group


------------------------------------------------------

GET /campaign?campaignGroupId=1                                                                 --> Get campaigns of group 1

POST /campaign?campaignGroupId=1   Body is List of Campaign                                    --> Create List of campaigns for group 1


-------------------------------------------------------


GET /optimisation/campaignGroup/{campaignGroupId}?number=5                 --> Get the latest 5 number optimisations belongs to  campaignGroupId

POST /optimisation/create?campaignGroupId=5                                --> Create optimisation for campaignGroupId with Id 5

POST /optimisation/accept?optimisationId=5                                 --> Accept optimisation with Id 5


-----------------------------------------------------------------
recommendation

GET /recommendation/optimization/{optimizationId}?number=5                 --> Get the latest 5 of recommendations that belongs to optimizationId



 


