package com.thinking.machines.nafserver;
import com.thinking.machines.nafserver.model.*;
import com.thinking.machines.nafserver.tool.*;
import java.net.*;
import java.io.*;

public class TMNAFServer
{
private static Application application;

public TMNAFServer()
{
System.out.println("point st1");	// REMOVE
initialize();
}
private void initialize()
{
System.out.println("point st2");	// REMOVE
application=ApplicationUtility.getApplication();
System.out.println("point st3");	// REMOVE
}
public static void startServer()
{
System.out.println("point st4");	// REMOVE
startServer(5000);
}
public static void startServer(int portNumber)
{
try
{
System.out.println("point st5");	// REMOVE
ServerSocket serverSocket=new ServerSocket(portNumber);
Socket socket;
while(true)
{
System.out.println("Server is listening...");
socket=serverSocket.accept();
System.out.println("point st6");	// REMOVE
System.out.println("Request arrived...");
System.out.println("point st7");	// REMOVE
ProcessRequest processRequest=new ProcessRequest(socket,application);
System.out.println("point st8");	// REMOVE
System.out.println("Done");
}
}catch(IOException ioe)
{
System.out.println(ioe);
}
}
}
