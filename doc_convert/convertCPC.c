#include <stdio.h>
#include <stdlib.h>
#include <string.h>



void main(){

	char input[100];
	char* curstr;	

	printf("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
	while(gets(input)!=0)
	{
		printf("\t<entry>\n");
		//station name
		curstr = strtok(input,",");
		printf("\t\t<title name=\"%s\"></title>\n",curstr);
		//station address
		curstr = strtok(NULL,",");
		printf("\t\t<address  area=\"\" name=\"%s\"></address>\n",curstr);
		//station tele
		curstr = strtok(NULL,",");
		//printf("%s",curstr);
		//station time
		curstr = strtok(NULL,",");
		//printf("%s\n",curstr);
		printf("\t\t<position latitude=\"\" longtitude=\"\"></position>\n");
		printf("\t</entry>\n");
	}
	printf("</resources>\n");

}
