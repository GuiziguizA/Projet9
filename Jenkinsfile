pipeline{
    agent any
    triggers{
		pollSCM('* * * * *')
	}
    stages{
        stage("Compile the source code")	{
            steps	{
        sh "mvn -version"    
	sh "mvn clean compile"
            }
        }
    }
}
