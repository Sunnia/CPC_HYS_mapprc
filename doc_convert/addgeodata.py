import geocoder
import xml.etree.ElementTree as ET
import time

root = ET.fromstring(open('output.xml').read())
count=0
for entry in root:
	addr=entry.find('address')
	if addr!=None:
		good=addr.get('name')
	else:
		print('cannot get address')
	position=entry.find('position')
	if position!=None:
		g = geocoder.google(good)
		if g.latlng == None:
			print(g)
		else:
			position.set('latitude',str(g.latlng.get('lat')))
			position.set('longtitude',str(g.latlng.get('lng')))
	else:
		print('no positiondata')
	count=count+1	
	print(good,g.latlng,' curcount : ',count)
	time.sleep(1)#try not to ask google too frequency

tree.write('gasstaion.xml',encoding="utf-8",xml_declaration=True)
