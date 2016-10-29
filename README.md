# jenkins2-pipeline-lab

This repository provides you a local playground environment using Vagrant and Ansible to get started with Jenkins 2.
In addition to Jenkins 2 to will get app servers VMs and an artifactory to build "real-world" examples.
The pipelines described in our [Blogpost] are pre-configured and ready to run.

### Prerequisites

* vagrant
* ansible 2.0.2 (!) only works with this version


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