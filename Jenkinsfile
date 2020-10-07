pipeline{
    agent any
	tools{
	maven "apache-maven-3.6.3"
	}
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
