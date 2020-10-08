pipeline{
    agent any
	
    triggers{
		pollSCM('* * * * *')
	}
node {
    git url: 'https://github.com/GuiziguizA/Projet9.git'
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
