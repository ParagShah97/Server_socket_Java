package com.thinking.machines.nafclient;
import com.thinking.machines.nafclient.*;
import com.library.*;
import java.io.*;
import java.net.*;

public class Main
{
public static void main(String gg[])
{
try
{
TMNAFClient tmnafClient=new TMNAFClient("localhost",5000);
Student s=new Student();
s.setName("Pappu");
tmnafClient.process("/serviceA/usc",s);

System.out.println("Roll Number : "+s.getRollNumber()+"Name : "+s.getName());

//String s=(String)tmnafClient.process("/serviceA/whatever");
//tmnafClient.process("/serviceA/add",10,20);
//int x=(int)tmnafClient.process("/serviceA/getProduct",3,5);
//System.out.println(s);
//System.out.println(x);
}catch(Exception e)
{
System.out.println(e);

}
}
}
