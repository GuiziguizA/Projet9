pipeline{
    agent any
    triggers{
		pollSCM('* * * * *')
	}
    stages{
      stage ('Compile Stage') {
steps {
withMaven (maven: 'apache-maven-3.6.1') {
bat'mvn clean compile '
}
}
}
        stage("Test the source code")	{
            steps	{
            bat "mvn test"
            }
        }
         
        }
}
