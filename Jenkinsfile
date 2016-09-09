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

  def switchContainer = { String serverName, String containerName, String dockerImageToUse, String appPort, String serverPort ->
      sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${serverName} docker pull ${dockerImageToUse}"
  
      sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${serverName} docker stop ${containerName} || true"
      sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${serverName} docker rm ${containerName} || true"
      sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${serverName} docker run -d --name ${containerName} -p ${serverPort}:${appPort} ${dockerImageToUse}"
  }

  stage("Deploy") {

    sshagent(credentials: ['jenkins-ci']) {
      switchContainer('192.168.42.11', 'dndViewer01', dockerImage, '8080', '8080')
      switchContainer('192.168.42.11', 'dndViewer02', dockerImage, '8080', '8081')
      switchContainer('192.168.42.11', 'dndViewer03', dockerImage, '8080', '8082')
    }
  }

  stage("Smoke-Test") {
    timeout(time: 30, unit: 'SECONDS') {
      sh 'until $(curl --silent --head --fail http://192.168.42.11:8080 > /dev/null); do printf \'.\'; sleep 1; done; curl http://192.168.42.11:8080 | grep \'ng-app="characterViewer"\''
    }
  }
}
