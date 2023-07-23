package com.thinking.machines.nafclient;
import java.util.*;
import java.io.*;
import java.net.*;
import com.thinking.machines.nafcommon.*;
import com.thinking.machines.tool.*;

public class TMNAFClient
{
static String host;
static int portNumber;
public TMNAFClient(String host,int portNumber)
{
this.host=host;
this.portNumber=portNumber;
}
public static Object process(String path,Object ...args)
{
Response response=null;
Request request=null;
try
{
request=new Request();
Object[] arguments=new Object[args.length];
int j=0;
for(Object o:args)
{
arguments[j]=o;
j++;
}
request.setPath(path);
request.setArguments(arguments);
//love love

ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(request);
oos.flush();
byte requestBytes[]=baos.toByteArray();
int requestSize=requestBytes.length;
byte requestSizeInBytes[]=new byte[4];
requestSizeInBytes[0]=(byte)(requestSize >> 24);
requestSizeInBytes[1]=(byte)(requestSize >> 16);
requestSizeInBytes[2]=(byte)(requestSize >> 8);
requestSizeInBytes[3]=(byte)(requestSize);

Socket socket=new Socket(host,portNumber);

OutputStream os=socket.getOutputStream();
os.write(requestSizeInBytes,0,4);
os.flush();
InputStream is=socket.getInputStream();
byte ack[]=new byte[1];
int byteCount=is.read(ack);
if(ack[0]!=79) throw new RuntimeException("Unable to receive acknowledgement");
int bytesToSend=requestSize;
int chunkSize=1024;
int i=0;
while(bytesToSend>0)
{
if(bytesToSend<chunkSize) chunkSize=bytesToSend;
os.write(requestBytes,i,chunkSize);
os.flush();
i=i+chunkSize;
bytesToSend-=chunkSize;
}
byteCount=is.read(ack);
if(ack[0]!=79) throw new RuntimeException("Unable to receive acknowledgement");
//response part-> 
byte [] responseLengthInBytes=new byte[4];
byteCount=is.read(responseLengthInBytes);
int responseLength;
responseLength=(responseLengthInBytes[0] & 0xff) << 24 | (responseLengthInBytes[1] & 0xff) << 16 | (responseLengthInBytes[2] & 0xff) << 8 | (responseLengthInBytes[3] & 0xff);
ack[0]=79;
os.write(ack,0,1);
os.flush();
baos=new ByteArrayOutputStream();
byte chunk[]=new byte[1024];
int bytesToRead=responseLength;
while(bytesToRead>0)
{
byteCount=is.read(chunk);
if(byteCount>0)
{
baos.write(chunk,0,byteCount);
baos.flush();
}
bytesToRead-=byteCount;
}
os.write(ack,0,1);
os.flush();
byte responseBytes[]=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(responseBytes);
ObjectInputStream ois=new ObjectInputStream(bais);

response=(Response)ois.readObject();



arguments=response.getArguments();
j=0;

for(Object o:arguments)
{
//args[j]=o;
Utility.copyObject(args[j],o);
j++;
}




socket.close();





//end-------------------
}catch(IOException e)
{
System.out.println(e);
}catch(ClassNotFoundException cnfe)
{
System.out.println(cnfe.getMessage());
}
return response.getResult();
}
}
