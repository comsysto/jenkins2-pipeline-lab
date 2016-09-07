#!/usr/bin/env bash

# generate ssh configs for vagrant VMs
vagrant ssh-config app > ssh-config/app.config
vagrant ssh-config jenkins > ssh-config/jenkins.config

# add jenkins public key to app authorized keys
ssh jenkins -F ssh-config/jenkins.config "sudo cat /var/lib/jenkins/.ssh/id_rsa.pub" | ssh app -F ssh-config/app.config "sudo sh -c 'cat >> /home/ubuntu/.ssh/authorized_keys'"
