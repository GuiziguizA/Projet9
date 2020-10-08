pipeline{
    agent any
    triggers{
		pollSCM('* * * * *')
	}
    stages{
      stage ('Compile') {
steps {
withMaven (maven: 'apache-maven-3.6.3') {
bat'mvn clean compile '
}
}
}
            stage ('Test') {
steps {
withMaven (maven: 'apache-maven-3.6.3') {
bat'mvn test '
}
}
}
}
