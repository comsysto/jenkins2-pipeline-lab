#!groovy
node {

  stage("Checkout") {
    git branch: 'feature/jenkins-poll-test', poll: true, url: 'https://github.com/Endron/dnd5-char-viewer.git'
  }

  stage("Build") {
    sh "./gradlew build"
    publishHTML(target: [
            allowMissing         : false,
            alwaysLinkToLastBuild: true,
            keepAll              : false,
            reportDir            : 'build/reports/tests',
            reportFiles          : 'test/index.html',
            reportName           : 'Unit tests report'])
  }

  stage("Deploy") {
    sshagent(credentials: ['vagrant']) {
      sh 'ssh -o StrictHostKeyChecking=no -p 2222 -l vagrant localhost mkdir -p dnd5-char-viewer'
      sh 'scp -o StrictHostKeyChecking=no -P 2222 build/libs/dnd5-char-viewer.jar vagrant@localhost:dnd5-char-viewer/'
    }
  }

  stage("Run") {
    sshagent(credentials: ['vagrant']) {
      sh 'ssh -o StrictHostKeyChecking=no -p 2222 -l vagrant localhost "cd ./dnd5-char-viewer; killall -9 java; java -jar dnd5-char-viewer.jar 2>> /dev/null >> /dev/null &"'
    }
  }

  stage("Smoke-Test") {
    timeout(time: 60, unit: 'SECONDS') {
      sh 'until $(curl --silent --head --fail http://localhost:28080 > /dev/null); do printf \'.\'; sleep 1; done; curl http://localhost:28080 | grep \'ng-app="characterViewer"\''
    }
  }
}