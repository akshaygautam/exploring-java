package reflection; 	
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;

import annotations.MyAnno1;
public class TestReflection {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException, NoSuchMethodException {
		Method[] methods = MyClass.class.getMethods(); 
		Field[] fields = MyClass.class.getFields();
		Constructor[] constr = MyClass.class.getConstructors();
//		workingWithConstructors(constr);
//		workingWithMethods(methods);
//		workingWithFields(fields);
//		accessPrivateFields(constr);
//		accessPrivateMethod();
//		accessAnnotations();
		dynamicProxy();
	}

	private static void dynamicProxy() {
		InvocationHandler handler = new MyInvocationHandler();
		MyInterface obj = (MyInterface) Proxy.newProxyInstance(MyInterface.class.getClassLoader(), 
				new Class[] {MyInterface.class, MyInterfaceCustom.class}, handler);
		System.out.println(obj.getCustomName());
	}

	private static void accessAnnotations() {
		Annotation[]  ann = MyClass.class.getAnnotations();
		for(Annotation a: ann) {
			MyAnno1 m = (MyAnno1) a;
			System.out.println(m.name());
			System.out.println(m.name());
			System.out.println(m.somthing());
			System.out.println(Arrays.toString((m.manyThings())));
			
		}
	}

	private static void accessPrivateMethod() {
		Method[] methods = MyClass.class.getDeclaredMethods();
		for(Method m: methods)
			System.out.print(m.getName()+"(RETRUN TYPE: "+m.getReturnType()+") (is getter: "+isGetter(m)+") (is setter: "+isSetter(m)+" )\n");
	}

	private static void accessPrivateFields(Constructor[] constr) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MyClass obj1 = (MyClass) constr[0].newInstance("obj1","mail", "add");
		Field[] fields = MyClass.class.getDeclaredFields();
		for(Field f: fields) f.setAccessible(true);
		for(Field f: fields)
		System.out.println((String) f.get(obj1));
	}

	private static void workingWithFields(Field[] fields) {
		for(Field f: fields)
			System.out.print(f.getName()+"("+f.getType() +" TYPE),");
	}

	private static void workingWithMethods(Method[] methods) {
		for(Method m: methods)
			System.out.print(m.getName()+"(RETRUN TYPE: "+m.getReturnType()+") (is getter: "+isGetter(m)+") (is setter: "+isSetter(m)+" )\n");
	}

	private static void workingWithConstructors(Constructor[] constr)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, SecurityException, NoSuchMethodException {
		for(Constructor c: constr) {
			Parameter[] par = c.getParameters();
			System.out.print(c.getName());
			for(Parameter p: par) {
				System.out.print("( "+p.getType()+" TYPE)");
			}
			System.out.println();
		}
		createObjects(constr);
	}

	private static void createObjects(Constructor[] constr)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, SecurityException, NoSuchMethodException {
		MyClass obj1 = (MyClass) constr[0].newInstance("obj1","mail", "add");
		System.out.println(obj1);
		MyClass obj2 = (MyClass) constr[1].newInstance();
		System.out.println(obj2);
		Field f = MyClass.class.getField("address");
		f.set(obj2, "obj2Address");
		System.out.println(obj2);
		Method m1 = MyClass.class.getMethod("setName",String.class);
		Method m2 = MyClass.class.getMethod("setEmail",String.class);
		Method m3 = MyClass.class.getMethod("setAddress",String.class);
		
		m1.invoke(obj2, "ReflectionSetName");
		m2.invoke(obj2, "ReflectionSetEmail");
		m3.invoke(obj2, "ReflectionSetAddressAgain");
		
		System.out.println(obj2);
	} 
	
	public static boolean isGetter(Method method){
		  if(!method.getName().startsWith("get"))      return false;
		  if(method.getParameterTypes().length != 0)   return false;  
		  if(void.class.equals(method.getReturnType())) return false;
		  return true;
		}

		public static boolean isSetter(Method method){
		  if(!method.getName().startsWith("set")) return false;
		  if(method.getParameterTypes().length != 1) return false;
		  return true;
		}

}
