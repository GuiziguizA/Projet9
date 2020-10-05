pipeline {
    agent any
   
    stages {

	stage('compile App'){
	
	steps{
		withMaven(maven : 'apache-maven-3.6.3'){
	
			bat 'mvn clean compile'

				}
			}
		}
	stage('Test app'){
	
	steps{
		withMaven(maven : 'apache-maven-3.6.3'){
	
			bat 'mvn test'

				}
			}
		}
	}
    }