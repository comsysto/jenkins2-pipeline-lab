#!groovy
node {

  /*
   * We will use the git commit id as a unique identifier for our current build.
   */
  String gitCommitId

  stage("Checkout") {
    git branch: 'feature/jenkins-poll-test', poll: true, url: 'https://github.com/Endron/dnd5-char-viewer.git'

    /*
     * As the current version of the plugin does not grant us direct access to
     * output of the shell commands we have to pipe the output into a file and
     * then read the file.
     */
    sh 'git rev-parse HEAD > git.id'
    gitCommitId = readFile('git.id')
  }

  def jenkinsServerName = '192.168.42.10'
  def dockerRegistryPort = '5000'
  def dockerImage = "${jenkinsServerName}:${dockerRegistryPort}/dndviewer:${gitCommitId.substring(0, 5)}"

  stage("Build") {
    sh "./gradlew clean build"
    sh "docker build . --tag ${dockerImage}"
    sh "docker push ${dockerImage}"

/*    publishHTML(target: [
            allowMissing         : false,
            alwaysLinkToLastBuild: true,
            keepAll              : false,
            reportDir            : 'build/reports/tests',
            reportFiles          : 'test/index.html',
            reportName           : 'Unit tests report'])  */
  }

  def switchContainer = { String serverName, List<String> credentials, String containerName, String dockerImageToUse, String appPort, String serverPort ->
    sshagent(credentials: credentials) {
      sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${serverName} docker pull ${dockerImageToUse}"
  
      sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${serverName} docker stop ${containerName} || true"
      sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${serverName} docker rm ${containerName} || true"
      sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${serverName} docker run -d --name ${containerName} -p ${serverPort}:${appPort} ${dockerImageToUse}"
    }
  }

  stage("Deploy") {
    def serverName = '192.168.42.11'
    def credentials = ['jenkins-ci']
    def appPort = '8080'
   
   def containers = [
     [name: 'dndViewer01', serverPort: '8081'],
     [name: 'dndViewer02', serverPort: '8082'],
     [name: 'dndViewer03', serverPort: '8083']
   ]
   for (def container : containers) {
     switchContainer(serverName, credentials, container.name, dockerImage, appPort, container.serverPort)
   }

  }

  def checkEndpoint = { String url ->
    timeout(time: 30, unit: 'SECONDS') {
      sh "until \$(curl --silent --head --fail ${url} > /dev/null); do printf \'.\'; sleep 1; done; curl ${url} | grep \'ng-app=\"characterViewer\"\'"
    }  
  }

  stage("Smoke-Test") {
    checkEndpoint('http://192.168.42.11:8081')
  }
}
