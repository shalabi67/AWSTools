#Optimize Cost
This project is about optimizing aws cost by stooped or removing not used resources. the project is structuered based on Team, 
Environment(Live, stable, development, testing) and Projects.

##API Resources examples
###Teams resources
####stop all resources for al teams
http://localhost:8080/teams/resources?action=stop

####list resources for all teams
http://localhost:8080/teams/resources

####start all resources for all teams
http://localhost:8080/teams/resources?action=start

###API Team resources example
####List resources for specific team
http://localhost:8080/teams/resources/pf1

####stop all resources for a team
http://localhost:8080/teams/resources/pf1?action=stop

####start all resources for a team
http://localhost:8080/teams/resources/pf1?action=start


###API Team Environment resources example
####list all resources for a team for an environment live
http://localhost:8080/teams/resources/backend/environments/live

####Stop all resources for a team for an environment live
http://localhost:8080/teams/resources/backend/environments/live?action=stop

####start all resources for backend team for an environment live
http://localhost:8080/teams/resources/backend/environments/live?action=start
