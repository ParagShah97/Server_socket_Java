package com.library;
import com.thinking.machines.nafserver.annotation.*;

@Path("/serviceA")
public class ServiceA
{
@Path("/whatever")
public String getWhatever()
{
System.out.println("getWhatever invoked");
return "Whatever";
}

@Path("/usc")
public void usc(Student student)
{
student.setRollNumber(1);
System.out.println("Roll Number : "+student.getRollNumber()+", Name : "+student.getName());
}

@Path("/add")
public void add(int a,int b)
{
System.out.println("add invoked");
System.out.println(a+b);
}
@Path("/getProduct")
public int getProduct(int a,int b)
{
System.out.println("getProduct invoked");
return a*b;
}
public int getDiff(int e,int f)
{
return e-f;
}
}
