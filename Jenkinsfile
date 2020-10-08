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
        
    }
}
