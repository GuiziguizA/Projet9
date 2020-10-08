node {
    def os = System.properties['os.name'].toLowerCase()
    echo "OS: ${os}"
    if (os.contains("linux")) {
      sh "mvn install" 
    } else {
      bat "mvn install"
    }
}

