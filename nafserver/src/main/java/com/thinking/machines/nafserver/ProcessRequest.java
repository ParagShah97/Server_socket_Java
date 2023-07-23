package com.thinking.machines.nafserver;
import com.thinking.machines.nafserver.model.*;
import com.thinking.machines.nafserver.tool.*;
import com.thinking.machines.nafcommon.*;
import java.net.*;
import java.io.*;
import java.lang.reflect.*;
class ProcessRequest extends Thread
{

Socket socket;
Application application;
ProcessRequest(Socket socket,Application application)
{
System.out.println("point s1");	// REMOVE
System.out.println("point s2");	// REMOVE
this.socket=socket;
this.application=application;
start();
}
public void run()
{
System.out.println("point s3");	// REMOVE
Request request;
Response response=new Response();
try
{
byte ack[]=new byte[1];
InputStream inputStream;
OutputStream outputStream;
byte requestLengthInBytes[]=new byte[4];
int requestLength;
int byteCount;
int bytesToRead;
int bytesToWrite;
ByteArrayOutputStream baos;
byte requestBytes[];
byte chunk[]=new byte[1024];
ByteArrayInputStream bais;
ObjectInputStream ois;
ObjectOutputStream oos;
byte responseBytes[];
byte responseLengthInBytes[];
int responseLength;
int chunkSize;


System.out.println("point s4");	// REMOVE
inputStream=socket.getInputStream();
byteCount=inputStream.read(requestLengthInBytes);
requestLength=(requestLengthInBytes[0] & 0xFF) <<24 | (requestLengthInBytes[1] & 0xFF) <<16 | (requestLengthInBytes[2] & 0xFF) <<8 | (requestLengthInBytes[3] & 0xFF);
ack[0]=79;
outputStream=socket.getOutputStream();
outputStream.write(ack,0,1);
outputStream.flush();
baos=new ByteArrayOutputStream();
bytesToRead=requestLength;
while(bytesToRead>0)
{
byteCount=inputStream.read(chunk);
if(byteCount>0)
{
baos.write(chunk,0,byteCount);
}
bytesToRead-=byteCount;
}
ack[0]=79;
outputStream.write(ack,0,1);
outputStream.flush();
requestBytes=baos.toByteArray();
bais=new ByteArrayInputStream(requestBytes);
ois=new ObjectInputStream(bais);
request=(Request) ois.readObject();

System.out.println("point s5");	// REMOVE
//System.out.println(request.getPath());


Service service;
service=application.getService(request.getPath());
Method method=service.getMethod();
Class c=service.getModule().getServiceClass();


System.out.println("point s6");	// REMOVE
Object result=method.invoke(c.newInstance(),request.getArguments());



response.setArguments(request.getArguments());
response.setResult(result);
System.out.println("point s7");	// REMOVE
response.setIsSuccessful(true);
//ddddddddddddd

System.out.println("point s8");	// REMOVE
baos=new ByteArrayOutputStream();
oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();
responseBytes=baos.toByteArray();
responseLength=responseBytes.length;
responseLengthInBytes=new byte[4];
responseLengthInBytes[0]=(byte)(responseLength>>24);
responseLengthInBytes[1]=(byte)(responseLength>>16);
responseLengthInBytes[2]=(byte)(responseLength>>8);
responseLengthInBytes[3]=(byte)(responseLength);
outputStream.write(responseLengthInBytes,0,4);
outputStream.flush();
byteCount=inputStream.read(ack);


if(ack[0]!=79) throw new ApplicationException("Unable to receive acknowledgement");



bytesToWrite=responseLength;
chunkSize=1024;
int i=0;
while(bytesToWrite>0)
{
if(bytesToWrite<chunkSize) chunkSize=bytesToWrite;
outputStream.write(responseBytes,i,chunkSize);
outputStream.flush();
i+=chunkSize;
bytesToWrite-=chunkSize;
}
byteCount=inputStream.read(ack);
if(ack[0]!=79) throw new ApplicationException("Unable to receive acknowledgement");

System.out.println("point s9");	// REMOVE
socket.close();






//dddddddddddddddd
}catch(Exception e)
{
response.setIsException(true);
response.setException(e.getMessage());
response.setIsSuccessful(false);
}catch(Throwable t)
{
response.setIsError(true);
response.setError(t.getMessage());
response.setIsSuccessful(false);
}

}//func. ends


}//class ends
