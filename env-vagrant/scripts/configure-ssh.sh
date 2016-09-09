#!/usr/bin/env bash

# generate ssh configs for vagrant VMs
mkdir ssh-config
vagrant ssh-config app-dev > ssh-config/app-dev.config
vagrant ssh-config app-prod > ssh-config/app-prod.config
vagrant ssh-config jenkins > ssh-config/jenkins.config

# add jenkins public key to app authorized keys
ssh jenkins -F ssh-config/jenkins.config "sudo cat /var/lib/jenkins/.ssh/id_rsa.pub" | ssh app-dev -F ssh-config/app-dev.config "sudo sh -c 'cat >> /home/ubuntu/.ssh/authorized_keys'"
ssh jenkins -F ssh-config/jenkins.config "sudo cat /var/lib/jenkins/.ssh/id_rsa.pub" | ssh app-prod -F ssh-config/app-prod.config "sudo sh -c 'cat >> /home/ubuntu/.ssh/authorized_keys'"
