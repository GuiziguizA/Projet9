pipeline{
    agent any
    triggers{
		pollSCM('* * * * *')
	}
    stages{
        stage("Compile the source code")	{
            steps	{
            bat 'chmod --recursive a+rwx ./'
            bat "mvn compile"
            }
        }
        stage("Test the source code")	{
            steps	{
            bat "mvn test"
            }
        }
         
        }
}
