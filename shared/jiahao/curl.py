import requests
import SocketServer
import thread
import time
import json

def SubscribeThread(str1, str2):
	global streamingData
	streamingStr = ''
	r = requests.get('http://stream.meetup.com/2/rsvps', stream=True)

	for line in r.iter_lines():
		line1 = line
		line = json.loads(line)

		streamingStr += line["group"]["group_city"]
		streamingStr += line["group"]["group_country"]

		for topic in line["group"]["group_topics"]:
			streamingStr += topic["topic_name"]

		streamingStr += line["event"]["event_name"]	
		streamingData.append(streamingStr)
		streamingStr = ''

class MyTCPHandler(SocketServer.BaseRequestHandler):
    def handle(self):
	global streamingData
	while 1:
            self.request.send(streamingData.pop().encode('utf-8'))

streamingData = []
thread.start_new_thread(SubscribeThread, ('', ''))
time.sleep(1)

HOST, PORT = "namenode", 8001
server = SocketServer.TCPServer((HOST, PORT), MyTCPHandler)
server.serve_forever()
