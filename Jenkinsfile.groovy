pipeline {
    agent any
     tools {
        maven "Maven"
    }

    stages {

	stage('compile App'){
	git url: 'https://github.com/GuiziguizA/Projet9.git'
	steps{
		
	
			bat 'mvn clean compile'

				
			}
		}
	stage('Test app'){
	git url: 'https://github.com/GuiziguizA/Projet9.git'
	steps{
		
			bat 'mvn test'

				
			}
		}
	}
    }