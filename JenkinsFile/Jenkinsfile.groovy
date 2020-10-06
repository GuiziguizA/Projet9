pipeline {
    agent any
     tools {
        maven "Maven"
    }

    stages {

	stage('build'){
	git url: 'https://github.com/GuiziguizA/Projet9.git'
	steps{
		
	
			bat 'mvn clean compile'

				
			}
		}
	stage('test'){
	git url: 'https://github.com/GuiziguizA/Projet9.git'
	steps{
		
			bat 'mvn test'

				
			}
		}
	}
    }