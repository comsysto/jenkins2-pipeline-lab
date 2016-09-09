# jenkins2-pipeline-lab

### Prerequisites

* vagrant
* ansible 2.0.2 (! only works with this version)


### Setup your local environment

#### 1. Create & provision vagrant boxes

     vagrant up

You need to choose your network for bridging. Mostly option 1)


#### Configure ssh for your vagrant boxes

    ./scripts/configure-ssh.sh


### Let's try it

* http://localhost:18080/ 


### Links & References

* https://jenkins.io/solutions/pipeline/
* https://dzone.com/refcardz/continuous-delivery-with-jenkins-workflow


### Jenkinsfile

```
    def gitCommit
    stage('Checkout')
    node {
        // Get some code from a GitHub repository
        git url: 'https://github.com/Endron/dnd5-char-viewer.git'
        sh 'git rev-parse HEAD > git.id'
        gitCommit = readFile('git.id')
    }

    stage('Build') 
    node {
        def dockerTag = "localhost:5000/dndviewer:${gitCommit}"
        
        sh "./gradlew clean build"
        sh "docker build . --tag ${dockerTag}"
        sh "docker push ${dockerTag}"
    }

    //checkpoint 'Completed CI'

    stage ('Deploy') 
    node {
        sshagent(['jenkins']) {
            sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.11 uname -a'
        }
    }
```
