#!/bin/sh

#Master configuration

while getopts "i:" opt; do
	case $opt in
		i)
			ip_addr=$OPTARG
			;;
	esac
done


docker -H :2375 run -d -p 8500:8500 --name=consul progrium/consul -server -bootstrap

docker -H :2375 run -d -p 4000:4000 swarm manage -H :4000 --replication --advertise $ip_addr:4000 consul://$ip_addr:8500
