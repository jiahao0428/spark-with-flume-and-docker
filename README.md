# spark-with-flume-and-docker

* _Starting docker daemon with modifying /etc/default/docker:_

  DOCKER_OPTS="-H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock --cluster-store consul://<master_ip>:8500 --cluster-advertise eth1:2376"

* _Run bootstrap_docker.sh to start consul and swarm container in your master node_

* _Run bootstrap_slave.sh in your slave nodes_

* _Create a overlay network in your master node, i created a overlay network named 'my-net':_

  docker network create --driver overlay <my-multi-host-network>

* _create a environment variable to directly control swarm in master node:_

  export DOCKER_HOST=tcp://>master_ip>:4000

* _Run docker compose to set up multi-host containers in swarm, it will automatically deploy containers in nodes:_

  docker-compose scale namenode=1 datanode=WHATEVER_YOU_WANT mongodb=1 flume=1

* _Run post.py to start collecting data from meetup.com to flume(Remember to change ip address inside the script):_

  python post.py

* _Run flumecount.sh which will store the flume wordcount result into mongodb, but make sure you already create database in mongodb_
