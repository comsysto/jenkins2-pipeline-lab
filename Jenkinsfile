#!groovy
node {
  stage("[COMMIT] Build+UnitTest") {
    git branch: 'feature/jenkins-poll-test', poll: true, url: 'https://github.com/Endron/dnd5-char-viewer.git'
    sh "./gradlew clean build"
    junit '**/build/test-results/*/*.xml'
  }

  stage("[DEVELOPMENT] Deployment") {

    sshagent(credentials: ['jenkins-ci']) {
      sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.11 mkdir -p dnd5-char-viewer'
      sh 'scp -o StrictHostKeyChecking=no build/libs/*.jar ubuntu@192.168.42.11:dnd5-char-viewer/'
      sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.11 "cd ./dnd5-char-viewer; killall -9 java; java -jar *.jar 2>> /dev/null >> /dev/null &"'
    }
  }

  stage("[DEVELOPMENT] SmokeTest") {
    timeout(time: 60, unit: 'SECONDS') {
      sh 'until $(curl --silent --head --fail http://192.168.42.11:8080 > /dev/null); do printf \'.\'; sleep 1; done; curl http://192.168.42.11:8080 | grep \'ng-app="characterViewer"\''
    }
  }

  stage("[DEVELOPMENT] Manual UI Test") {
    input "Continue with production deployment?"
  }

  stage("[PRODUCTION] Deployment") {

    sshagent(credentials: ['jenkins-ci']) {
      sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.12 mkdir -p dnd5-char-viewer'
      sh 'scp -o StrictHostKeyChecking=no build/libs/*.jar ubuntu@192.168.42.12:dnd5-char-viewer/'
      sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.12 "cd ./dnd5-char-viewer; killall -9 java; java -jar *.jar 2>> /dev/null >> /dev/null &"'
    }
  }

  stage("[PRODUCTION] SmokeTest") {
    timeout(time: 60, unit: 'SECONDS') {
      sh 'until $(curl --silent --head --fail http://192.168.42.12:8080 > /dev/null); do printf \'.\'; sleep 1; done; curl http://192.168.42.12:8080 | grep \'ng-app="characterViewer"\''
    }
  }


}
