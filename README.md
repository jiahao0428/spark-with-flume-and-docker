# spark-with-flume-and-docker

* __Starting docker daemon with modifying /etc/default/docker:__

    <pre>DOCKER_OPTS="-H tcp:////0.0.0.0:2375 -H unix:////var/run/docker.sock --cluster-store consul://*master_ip*:8500 --cluster-advertise eth1:2376"</pre>

* __Run bootstrap_docker.sh to start consul and swarm container in your master node__

* __Run bootstrap_slave.sh in all of your slave nodes__

* __Create a overlay network in your master node, for example I created a overlay network named 'my-net':__

    <pre>docker network create --driver overlay *my-multi-host-network*</pre>

* __Create a environment variable to directly control swarm in master node:__

    <pre>export DOCKER_HOST=tcp:////*master_ip*:4000</pre>

* __Run docker compose to set up multi-host containers in swarm, it will automatically deploy containers in nodes:__

    <pre>docker-compose scale namenode=1 datanode=WHATEVER_YOU_WANT mongodb=1 flume=1</pre>

* __Run post.py to start collecting data from meetup.com to flume:__

    <pre>python post.py *flume_url*:8001 </pre>

* __Run flumecount.sh which will store the flume wordcount result into mongodb, but make sure you already create database in mongodb__
