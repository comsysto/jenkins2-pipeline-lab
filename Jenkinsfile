stage 'Checkout'
node {
   // Get some code from a GitHub repository
   git url: 'https://github.com/Endron/dnd5-char-viewer.git'
   //checkout scm: [$class: 'GitSCM', branches: [[name: '*/ec2-env']], userRemoteConfigs: [[url: 'https://github.com/comsysto/jenkins2-pipeline-lab.git']]]
}

stage 'Build'
node {
   def gradleHome = tool 'Gradle'

   sh "./gradlew clean assemble"
}

stage 'Test'
node{
   sh "./gradlew check"
}
stage 'Manual Quality Gates'

      step'Security Gate'
          input 'Everything fine?'

      step'UI Gate'
          input 'Really?'
          
)

stage 'Deploy'
node {
    sh "scp -v -o StrictHostKeyChecking=no -i ../../users/admin/jenkins-lab.pem build/libs/*.jar ubuntu@52.57.31.123:./"
    sh "ssh -v -o StrictHostKeyChecking=no -i ../../users/admin/jenkins-lab.pem ubuntu@52.57.31.123 'killall -9 java; echo \"java -jar *.jar \" | at now'"
}
