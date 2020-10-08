pipeline{
    agent any
    
    stages{
      stage ('Compile') {
steps {
withMaven (maven: 'C:\\Program Files\\apache-maven-3.6.3') {
bat'mvn clean compile '
}
}
}
            stage ('Test') {
steps {
withMaven (maven: 'C:\\Program Files\\apache-maven-3.6.3') {
bat'mvn test '
}
}
}
}
}