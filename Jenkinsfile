pipeline{
    agent any
	
    triggers{
		pollSCM('* * * * *')
	}
    stages{
        stage("Compile the source code")	{
            steps	{
           
	sh "mvn clean compile"
            }
        }
    }
}
