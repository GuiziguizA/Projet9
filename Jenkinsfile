pipeline{
    agent any
	 
    triggers{
		pollSCM('* * * * *')
	}

    stages{
	 

        
        
	stage("Compile the source code")	{
            steps	{
            sh 'chmod --recursive a+rwx ./'
            sh "./mvnw compile"
            }
            }
        
    }
}
