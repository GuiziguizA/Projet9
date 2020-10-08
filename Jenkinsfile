pipeline{
    agent any
    
    stages{
      stage ('Compile') {
steps {
withMaven (maven: 'apache-maven-3.6.3') {

bat'dir && cd src && mvn clean compile '
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
}