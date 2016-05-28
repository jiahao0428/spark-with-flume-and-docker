#!/usr/bin/env python
# -*- coding: utf-8 -*-

import requests
import time
import json

r = requests.get('http://stream.meetup.com/2/rsvps', stream=True)

for line in r.iter_lines():
	line = json.loads(line)

	streamingStr = ''
	streamingStr += line["group"]["group_city"]
	streamingStr += ' ' + line["group"]["group_country"]
	streamingStr += ' ' + line["event"]["event_name"]

	for topic in line["group"]["group_topics"]:
		streamingStr += ' ' + topic["topic_name"]
	
	payload = json.dumps([{'body': streamingStr}])
	header = {'content-type': 'application/json'}
	r = requests.post("http://192.168.1.130:8001", headers=header, data=payload)
	
	print payload
