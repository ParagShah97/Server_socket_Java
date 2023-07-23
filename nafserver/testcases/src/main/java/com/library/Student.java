package com.library;
import java.io.*;
public class Student implements Serializable
{
private int rollNumber;
private String name;
public void setRollNumber(int i)
{
this.rollNumber=i;
}
public int getRollNumber()
{
return this.rollNumber;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
}
