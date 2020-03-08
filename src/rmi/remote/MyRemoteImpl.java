package rmi.remote;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
	
	private static final long serialVersionUID = 1L;

	protected MyRemoteImpl() throws RemoteException {}

	
	@Override
	public String sayHello() throws RemoteException {
		return "Hello from remote";
	}

	public static void main(String[] args) {
		try {
//			MyRemote service = new MyRemoteImpl();
			 Naming.rebind("//localhost/RemoteHello", new MyRemoteImpl());            
	            System.err.println("Server ready");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
