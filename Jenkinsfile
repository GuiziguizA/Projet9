pipeline{
    agent any
	 environment {
           MAVEN_HOME = tool('M3')
       }
    triggers{
		pollSCM('* * * * *')
	}

    stages{
	 stage("Maven") {
              sh '${MAVEN_HOME}/bin/mvn -B verify'
           }
        stage("Compile the source code")	{
            steps	{
        
	bat 'mvn compile'
            }
        }
    }
}
