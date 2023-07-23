package com.thinking.machines.tool;
import java.util.*;
import java.lang.reflect.*;
class Pair<T1,T2>
{
public T1 first;
public T2 second;
Pair(T1 first, T2 second)
{
this.first=first;
this.second=second;
}
}

public class Utility 
{
private static HashSet<String> primitiveTypesSet=new HashSet<String>();
static 
{
primitiveTypesSet.add("int");
primitiveTypesSet.add("long");
primitiveTypesSet.add("short");
primitiveTypesSet.add("byte");
primitiveTypesSet.add("double");
primitiveTypesSet.add("float");
primitiveTypesSet.add("char");
primitiveTypesSet.add("boolean");
primitiveTypesSet.add("java.lang.Integer");
primitiveTypesSet.add("java.lang.Boolean");
primitiveTypesSet.add("java.lang.Long");
primitiveTypesSet.add("java.lang.Short");
primitiveTypesSet.add("java.lang.Charactor");
primitiveTypesSet.add("java.lang.Double");
primitiveTypesSet.add("java.lang.Float");
primitiveTypesSet.add("java.lang.Byte");
primitiveTypesSet.add("java.lang.String");
}


public static void copyObject(Object target, Object source)
{
try
{
Class sourceClass,targetClass;
targetClass=target.getClass();
sourceClass=source.getClass();
Method targetMethods[];
targetMethods=targetClass.getMethods();
Method sourceMethods[];
sourceMethods=sourceClass.getMethods();
LinkedList<Pair<Method,Method>> setterGetters=new LinkedList<>();
LinkedList<Method> sourceGetterMethods=new LinkedList<>();
for(Method method:sourceMethods)
{
if(isGetter(method))
{
sourceGetterMethods.add(method);
}
}
String setterName,getterName;
Method getterMethod;
for(Method method:targetMethods)
{
if(!isSetter(method)) continue;
getterMethod=getGetterOf(method,sourceGetterMethods);
if(getterMethod!=null) setterGetters.add(new Pair(method,getterMethod));
}
// Information extraction about setter / getter complete

Class propertyType;
Object object;
for(Pair<Method,Method> pair:setterGetters)
{
propertyType=pair.second.getReturnType();
if(isPrimitive(propertyType))
{
try
{
pair.first.invoke(target,pair.second.invoke(source));
}catch(IllegalAccessException iae) {}
catch(InvocationTargetException ite) {}
catch(Throwable t) {}
continue;
}
if(isOneDimensionalArray(propertyType))
{
/*
if(isArrayPrimitive(propertyType))
{

}
*/

}

//do whatever remains
}
}catch(Exception e)
{
System.out.println(e);
}
} // copyObject ends

public static boolean isPrimitive(Class type)
{
return primitiveTypesSet.contains(type.getName());
}

public static boolean isOneDimensionalArray(Class type)
{
String arrayName=type.getName();
if(arrayName.indexOf("L")==1)
{
return true;
}
return false;
}

public static Method getGetterOf(Method setterMethod,LinkedList<Method> getterMethods)
{
String setterPropertyName="";
String setterName=setterMethod.getName();
Class setterPropertyType;
Class getterPropertyType;
if(setterName.length()>3) setterPropertyName=setterName.substring(3);
String getterName;
setterPropertyType=setterMethod.getParameterTypes()[0];
String getterPropertyName;
for(Method method:getterMethods)
{
getterPropertyName="";
getterName=method.getName();
if(getterName.length()>3) getterPropertyName=getterName.substring(3);
getterPropertyType=method.getReturnType();
if(setterPropertyName.equals(getterPropertyName) && setterPropertyType.equals(getterPropertyType))
{
return method;
}
}
return null;
}
public static boolean isSetter(Method method)
{
return method.getName().startsWith("set") && method.getParameterCount()==1;
}
public static boolean isGetter(Method method)
{
if(method.getName().startsWith("get")==false) return false;
if(method.getReturnType().getSimpleName().toUpperCase().equals("VOID")) return false;
if(method.getParameterCount()>0) return false;
return true;
}

}//objectCopier ends 

