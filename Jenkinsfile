pipeline{
    agent any
    triggers{
		pollSCM('* * * * *')
	}
    stages{
       withMaven (maven:' apache-maven-3.6.3') {
bat'mvn install '
        }
        stage("Test the source code")	{
            steps	{
            bat "mvn test"
            }
        }
         
        }
}
