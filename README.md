# spark-with-flume-and-docker
<br/>
This is a Spark toy which deployed with Docker containers in multi-hosts environment, combining with Apache Flume to collect the streaming data from meetup.com and implement Spark streaming with word count and store the result to hive(default) or mongodb container.

<br/><br/>

* __Modifying /lib/systemd/system/docker.service:__
    
    <pre>EnvironmentFile=-/etc/default/docker<br/>
    ExecStart=/usr/bin/docker daemon $DOCKER_OPTS -H fd://</pre>

* __Starting docker daemon with modifying /etc/default/docker in every nodes:__

    <pre>DOCKER_OPTS="-H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock --cluster-store consul://MASTER_IP:8500 --cluster-advertise eth1:2376"</pre>
    
* __Run docker service as ROOT:__

    <pre>service docker start</pre>

* __Run bootstrap_docker.sh to start consul and swarm container in your master node__

    <pre>./bootstrap_docker.sh -i MASTER_IP</pre>

* __Run bootstrap_slave.sh in every slave nodes__

    <pre>./bootstrap_slave.sh -i NODE_IP -m MASTER_IP</pre>


* __Create a overlay network in your master node, for example I created a overlay network named 'my-net':__

    <pre>docker network create --driver overlay my-net</pre>

* __Create a environment variable to directly control swarm in master node:__

    <pre>export DOCKER_HOST=tcp://0.0.0.0:4000</pre>

* __Run docker compose to set up multi-host containers in swarm, it will automatically deploy containers in nodes:__

    <pre>docker-compose scale namenode=1 datanode=AMOUNT_YOU_WANT mongodb=1 flume=1</pre>

* __Copy Source Code to container:__

    <pre>docker cp FOLDER_PATH CONTAINER_NAME:DESTINATION_PATH</pre>

* __Run post.py to start collecting data from meetup.com to flume:__

    <pre>python post.py http://FLUME_CONTAINER_HOST_IP:8001 </pre>

* __OPTION 1: Run flumecount.sh which will store the flume wordcount result into hive, but make sure you already create table 'src' in hive__

    <pre>./flumecounthive.sh</pre>

* __OPTION 2: Run flumecount.sh which will store the flume wordcount result into mongodb, but make sure you already create database in mongodb__

    <pre>./flumecount.sh</pre>
