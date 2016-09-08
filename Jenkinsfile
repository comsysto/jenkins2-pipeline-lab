stage 'Checkout' 
node {
   // Get some code from a GitHub repository
   git url: 'https://github.com/Endron/dnd5-char-viewer.git'
}

stage 'Build'
node {
   def gradleHome = tool 'Gradle'

   sh "./gradlew clean build"
}
stage 'Deploy'
node {
    sh "scp -v -o StrictHostKeyChecking=no -i ../../users/admin/jenkins-lab.pem build/libs/*.jar ubuntu@52.57.31.123:./"
    sh "ssh -v -o StrictHostKeyChecking=no -i ../../users/admin/jenkins-lab.pem ubuntu@52.57.31.123 'killall -9 java; echo \"java -jar *.jar \" | at now'"
}
