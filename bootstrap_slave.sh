#!/bin/sh

#Slave configuration

while getopts "i:m:" opt; do
	case $opt in
		i)
			ip_addr=$OPTARG
			;;
		m)
			master_ip=$OPTARG
			;;
	esac
done

exec docker run -d swarm join --advertise=$ip_addr:2375 consul://$master_ip:8500
