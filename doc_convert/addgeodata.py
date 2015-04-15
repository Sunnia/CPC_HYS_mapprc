import geocoder
import xml.etree.ElementTree as ET
import time

tree = ET.parse('output.xml')
root = tree.getroot()
count=0
for entry in root:
	addr=entry.find('address')
	if addr!=None:
		good=addr.get('name')
	else:
		print 'cannot get address'
	position=entry.find('position')
	if position!=None:
		g = geocoder.google(good)
		position.set('latitude',str(g.latlng.get('lat')))
		position.set('longtitude',str(g.latlng.get('lng')))
	else:
		print 'no positiondata'
	count=count+1	
	print good,g.latlng,' curcount : ',count
	time.sleep(1)#try not to ack google too frequency
	###the old way which is unefficient
	#for element in entry:
	#	if element.tag=='title': 
	#		print element.get('name')
	#	elif element.tag=='address':
	#		good=element.get('name')
	#	elif element.tag=='position':
	#		print good, element.attrib
			#g = geocoder.google(good)
			#entry.set('latitude',str(g.latlng.get('lat')))
			#entry.set('longtitude',str(g.latlng.get('lng')))
			#time.sleep(1)
	#
tree.write('gasstaion.xml',encoding="utf-8",xml_declaration=True)
