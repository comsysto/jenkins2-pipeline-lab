# Jenkins 2.0 Pipelines
## Subheading with even more buzzwords

2-3 sentence abstract
* Local CD environment vagrant, ansible
* Pre-configured Jenkins Pipeline
* Starting point to play with pipelines in production-like scenario

### Intro / Local environment
* Short explanation of local sandbox infrastructure
* Maybe a small/simple diagram showing VMs + Networking + App
* Links to repos

### Continuous Delivery scenario concept
* Short explanation of the CD scenario we build
* Maybe visualisation of stages + steps

### Jenkins Pipelines and Jenkinsfiles
<a name=jenkinsPipelines/> 
In the last blogpost about Jenkins 2.0[LINK], which focused on the configuration and administration of Jenkins 2.0, we gave a quick overview of the new Pipline plugin and its features. Now we want to have a closer look at the capabilities of the Pipeline plugin and work out the major innovations and differences that are introduced by the plugin. 

#### Freestyle Jobs vs. Pipelines
<a name=freestyleVsPipelines/> 

The conventional way of building applications with Jenkins is to define a series of sequential executable steps, each configured as a freestyle job, and then trigger one
after the other until the whole build process is completed. The chaining of freestyle jobs is usually done via the Jenkins UI, which makes changes and reconfigurations of 
the job chain rather inconvenient. But fear not, the Jenkins Pipline plugin is here for the rescue! With the new plugin it becomes possible to orchestrate all workflow steps from commit to deployment within a single job. This job is defined using the Pipeline DSL (domain specific language), which is based on Groovy. Lets have a quick look at the vocabulary of the Pipeline DSL: in the following we have a listing of the three most important terms when it comes to writing pipeline scripts.

* **Stage**: stages are used to break your pipeline into logically distinct sections, which can include one or more build steps. All steps defined within a stage are then visualized as a unique segment in the Pipeline UI.
* **Step**: build steps are the single executable tasks, which can be chained together in sequence to form stages.
* **Node**: nodes are wrappers for resource-intensive steps, which should be scheduled and executed in a separate workspace, in order to avoid negative impacts on the overall pipeline performance.

So using a single job definition for creating build chains already makes life more convenient, but there is still more to it than that. The Pipeline plugin also offers new features that could not be realized so easily prior to Jenkins 2.0:

* Interruptions of the pipeline process (e.g. by restart of the Jenkins master) can be handled gracefully now with a persistent record of execution.
* Manual quality gates can be introduced into the workflow, where the pipeline process halts and waits for manual input.
* More complex workflows can be designed which include forks, joins, loops and parallel executions.

The following picture is a screenshot of the Jenkins Pipeline UI of our projects, showing the pipelines currently waiting for manual input at the quality gates.

[HERE SCREENSHOT]
![](images/pipelineUI.png)



#### Pipeline as Code
<a name=pipelineAsCode/> 

Another big advantage of the Pipeline plugin is the Pipeline as code feature. Instead of defining a Pipeline job via the Jenkins UI, it is now possible to store the configuration in an external file (called Jenkinsfile), which can easily be put under version control. Jenkins can then be configured to automatically scan repositories for Jenkinsfiles and setup a new build pipeline accordingly. Having the project code as well as the build/deployment configuration under the same roof seems to be the new trend at the moment, but after some discussion on this topic, we still remain skeptical towards this approach (see [conclusion](#conclusion)). Despite our skepticism, the Pipeline as code feature also offers some clear advantages that should not be neglected here:

* **Versioning of job definitions**: Putting the configuration under version control creates traceability and persistency
* **Sharing and Reuse**: job definitions can be easily shared and reused for similar builds
* **Automation**: New build pipeline are automatically created for new branches.
* **Adaptation**: the build pipeline can easily be manipulated without touching the Jenkins UI

As an example for a full-blown Jenkinsfile, the following code snipped shows the JAR deployment Jenkinsfile  of our project:

```groovy
#!groovy

stage("[COMMIT] Build+UnitTest")
node {
  git branch: 'master', poll: true, url: 'https://github.com/Endron/dnd5-char-viewer.git'
  sh "./gradlew clean build"
  junit '**/build/test-results/*/*.xml'
}

stage("[DEVELOPMENT] Deployment")
node {
  sshagent(credentials: ['jenkins-ci']) {
    sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.11 mkdir -p dnd5-char-viewer'
    sh 'scp -o StrictHostKeyChecking=no build/libs/*.jar ubuntu@192.168.42.11:dnd5-char-viewer/'
    sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.11 "cd ./dnd5-char-viewer; killall -9 java; java -jar *.jar 2>> /dev/null >> /dev/null &"'
  }
}

stage("[DEVELOPMENT] SmokeTest")
node {
  timeout(time: 60, unit: 'SECONDS') {
    sh 'until $(curl --silent --head --fail http://192.168.42.11:8080 > /dev/null); do printf \'.\'; sleep 1; done; curl http://192.168.42.11:8080 | grep \'ng-app="characterViewer"\''
  }
}

stage("[DEVELOPMENT] Manual UI Test")
input "Continue with production deployment?"

stage("[PRODUCTION] Publish")
node {
  def server = Artifactory.newServer('http://localhost:8081/artifactory', 'admin', 'password')
  def uploadSpec = """{
    "files": [
      {
        "pattern": "build/libs/dnd5-char-viewer.jar",
        "target": "ext-release-local/dnd5-char-viewer/dnd5-char-viewer.jar"
      }
    ]
  }"""
  server.upload(uploadSpec)
}

stage("[PRODUCTION] Deployment")
node {
  sshagent(credentials: ['jenkins-ci']) {
    sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.12 mkdir -p dnd5-char-viewer'
    sh 'scp -o StrictHostKeyChecking=no build/libs/*.jar ubuntu@192.168.42.12:dnd5-char-viewer/'
    sh 'ssh -o StrictHostKeyChecking=no -l ubuntu 192.168.42.12 "cd ./dnd5-char-viewer; killall -9 java; java -jar *.jar 2>> /dev/null >> /dev/null &"'
  }
}

stage("[PRODUCTION] SmokeTest")
node {
  timeout(time: 60, unit: 'SECONDS') {
    sh 'until $(curl --silent --head --fail http://192.168.42.12:8080 > /dev/null); do printf \'.\'; sleep 1; done; curl http://192.168.42.12:8080 | grep \'ng-app="characterViewer"\''
  }
}
```





#### Deploying Docker containers
* Docker pipeline in detail

#### Deploying Jars
* Jar pipeline in detail

### Conclusion / Summary
* Bla bla what we learned

### Acknowlegements
* names of the guys we took the Ansible roles from. (Just to be fair and avoid trouble)


##### Some references:
* https://dzone.com/articles/jenkins-pipeline-plugin-tutorial
* https://jenkins.io/solutions/pipeline/
* https://dzone.com/refcardz/continuous-delivery-with-jenkins-workflow
* ...
