// TEST Comment

stage 'Checkout'
node {
    git poll: true, url: 'https://github.com/Endron/dnd5-char-viewer.git'
}

stage 'Build'
node {
    def gradleHome = tool 'Gradle'
    sh "${gradleHome}/bin/gradle build"
}

stage 'Deploy'
node {
    sshagent(credentials: ['vagrant']) {
        sh 'ssh -o StrictHostKeyChecking=no -p 2222 -l vagrant localhost mkdir -p dnd5-char-viewer'
        sh 'scp -o StrictHostKeyChecking=no -P 2222 build/libs/dnd5-char-viewer.jar vagrant@localhost:dnd5-char-viewer/'
    }
}

stage 'Run'
node {
    sshagent(credentials: ['vagrant']) {
        sh 'ssh -o StrictHostKeyChecking=no -p 2222 -l vagrant localhost "cd ./dnd5-char-viewer; killall -9 java; java -jar dnd5-char-viewer.jar 2>> /dev/null >> /dev/null &"'
    }
}

stage 'Test'
node {
    timeout(time: 60, unit: 'SECONDS') {
        sh 'until $(curl --silent --head --fail http://localhost:28080 > /dev/null); do printf \'.\'; sleep 5; done; curl http://localhost:28080 | grep \'ng-app="characterViewer"\''
    }
}