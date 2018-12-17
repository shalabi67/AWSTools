#Optimize Cost
This project is about optimizing aws cost by stooped or removing not used resources. the project is structuered based on Team, 
Environment(Live, stable, development, testing) and Projects.

##API examples
###Teams resources
####stop all resources for al teams
http://localhost:8080/teams/resources?action=stop

####list resources for all teams
http://localhost:8080/teams/resources

####start all resources for al teams
http://localhost:8080/teams/resources?action=start

###Team resources
####List resources for specific team
http://localhost:8080/teams/resources/pf1

####stop all resources for a team
http://localhost:8080/teams/resources/pf1?action=stop

####start all resources for a team
http://localhost:8080/teams/resources/pf1?action=start