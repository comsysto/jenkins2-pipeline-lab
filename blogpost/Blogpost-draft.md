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

### Jenkins pipeline + Jenkins file explained
* Some words on Jenkins Pipeline / Pipeline as Code

- Why Pipeline is useful: Jenkins Jobs vs. Pipelines
    -Freestyle Jobs can only define sequential executable steps but not the whole application lifecycle of an application.
    -Pipelines offers the possibility to orchestrate all workflow steps from commit to delpoyment in a single job, which
     offers plenty of new possibilities:
        1) interruptions of the pipeline (by restart of the jenkins master for example) can be handled gracefully.
        2) Manual quality gates can be introduced to the workflow, where the pipelines halts and waits for manual input.
        3) complex workflows which include forks, joins, loops and parallel executions

* Gist of Jenkinsfile

    Another big advantage of the pipeline plugin is that in enables the "Pipeline as Code" feature. Instead of defining the
    single workflow steps through the Jenkins UI, the whole configuration process can be done in single file (called Jenkinsfile)
    using a Pipeline DSL (domain specific language), which is based on Groovy. The Jenkinsfile can then simply be checked
    into the repository. Two main advantages of the this "Pipeline as Code" approach are the

* Transition text to Pipeline using Docker + Pipeline using jar/spring-boot





#### Deploying Docker containers
* Docker pipeline in detail

#### Deploying Jars
* Jar pipeline in detail 

### Conclusion / Summary
* Bla bla what we learned 



##### Some references:
* https://dzone.com/articles/jenkins-pipeline-plugin-tutorial
* https://jenkins.io/solutions/pipeline/
* https://dzone.com/refcardz/continuous-delivery-with-jenkins-workflow
* ...