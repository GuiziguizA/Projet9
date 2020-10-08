pipeline{
    agent any
    triggers{
		pollSCM('* * * * *')
	}
    stages{
        stage("Compile the source code")	{
            steps	{
            bat 'chmod --recursive a+rwx ./'
            bat "./mvnw compile"
            }
        }
        stage("Test the source code")	{
            steps	{
            bat "./mvnw test"
            }
        }
         stage("Code coverage. Limiting the minimum score for lines coverage to 75%")	{
            steps	{
            bat "mvn test jacoco:report"
            publishHTML	(target:	[
				reportDir:	'target/site/jacoco',
				reportFiles:	'index.html',
				reportName:	"Code coverage report"
			])
            bat "mvn clean verify"
            
            }
        }
}
