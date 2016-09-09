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

  def dockerTag = "192.168.42.10:5000/dndviewer:${gitCommitId}"

  stage("Build") {
    sh "./gradlew clean build"
    sh "docker build . --tag ${dockerTag}"
    sh "docker push ${dockerTag}"

/*    publishHTML(target: [
            allowMissing         : false,
            alwaysLinkToLastBuild: true,
            keepAll              : false,
            reportDir            : 'build/reports/tests',
            reportFiles          : 'test/index.html',
            reportName           : 'Unit tests report'])  */
  }

  stage("Deploy") {

    sshagent(credentials: ['jenkins-ci']) {
      sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.11 mkdir -p dnd5-char-viewer'
      sh 'scp -o StrictHostKeyChecking=no build/libs/*.jar ubuntu@192.168.42.11:dnd5-char-viewer/'

      sh "ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.11 docker pull ${dockerTag}"
    }
  }

  stage("Run") {
    sshagent(credentials: ['jenkins-ci']) {
      sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.11 "cd ./dnd5-char-viewer; killall -9 java; java -jar *.jar 2>> /dev/null >> /dev/null &"'

    }
  }

  stage("Smoke-Test") {
    timeout(time: 30, unit: 'SECONDS') {
      sh 'until $(curl --silent --head --fail http://192.168.42.11:8080 > /dev/null); do printf \'.\'; sleep 1; done; curl http://192.168.42.11:8080 | grep \'ng-app="characterViewer"\''
    }
  }
}
