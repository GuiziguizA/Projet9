pipeline{
    agent any
	
    triggers{
		pollSCM('* * * * *')
	}
node {
    git url: 'https://github.com/joe_user/simple-maven-project-with-tests.git'
    def mvnHome = tool 'M3'
   bat "${mvnHome}\\bin\\mvn -B verify"
}
    stages{
        stage("Compile the source code")	{
            steps	{
        
	bat 'mvn compile'
            }
        }
    }
}
