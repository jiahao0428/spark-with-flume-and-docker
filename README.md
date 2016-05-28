# spark-with-flume-and-docker

* ##Starting docker daemon with modifying /etc/default/docker:

  DOCKER_OPTS="-H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock --cluster-store consul://<master_ip>:8500 --cluster-advertise eth1:2376"

* ##Run bootstrap_docker.sh to start consul and swarm container in your master node

* ##Run bootstrap_slave.sh in your slave nodes

* ##Create a overlay network in your master node, i created a overlay network named 'my-net':

  docker network create --driver overlay <my-multi-host-network>

* ##create a environment variable to directly control swarm in master node:

  export DOCKER_HOST=tcp://>master_ip>:4000

* ##Run docker compose to set up multi-host containers in swarm, it will automatically deploy containers in nodes:

  docker-compose scale namenode=1 datanode=WHATEVER_YOU_WANT mongodb=1 flume=1

* ##Run post.py to start collecting data from meetup.com to flume(Remember to change ip address inside the script):

  python post.py

* ##Run flumecount.sh which will store the flume wordcount result into mongodb, but make sure you already create database in mongodb
