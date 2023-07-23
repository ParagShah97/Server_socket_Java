package com.library.gattu;
import com.thinking.machines.nafserver.annotation.*;
@Path("/serviceB")
public class ServiceB
{
@Path("/whatever")
public String getWhatever()
{
return "Whatever";
}
@Path("/add")
public void add(int a,int b)
{
System.out.println(a+b);
}
@Path("/getProduct")
public int getProduct(int a,int b)
{
return a*b;
}
public int getDiff(int e,int f)
{
return e-f;
}
}
