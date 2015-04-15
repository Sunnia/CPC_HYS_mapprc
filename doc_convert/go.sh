cd '$shell pwd'
gcc convertCPC.c -o csvtoxml.o
./csvtoxml.o < origin.csv > output.xml
python addgeodata.py
