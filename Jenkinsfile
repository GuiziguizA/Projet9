pipeline{
    agent any
	
    triggers{
		pollSCM('* * * * *')
	}
    stages{
        stage("Compile the source code")	{
            steps	{
        
	bat 'mvn compile'
            }
        }
    }
}
