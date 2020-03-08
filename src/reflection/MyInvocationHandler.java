package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
		if(method.getName().equals("getName")) {
			return handleGetName();
		}else if(method.getName().equals("getCustomName")) {
			return handleCustomName(proxy, method, args);
		}
		return null;
	}

	private Object handleCustomName(Object proxy, Method method, Object[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		System.out.println(method.getDeclaringClass());
		Class c = Class.forName(method.getDeclaringClass().getName()+"Impl");
		Constructor co =  c.getConstructor(null);
		Class i = (Class) co.newInstance();
		System.out.println(co.getName());
		method.invoke(i,args);
		return null;
	}

	private Object handleGetName() {
		return Object.class.getName();
	}

}
