package rmi;

import java.rmi.Naming;

import rmi.remote.MyRemote;
/**
 * 
 * @author akshay
 * To test run:
 * Run start rmiregistry from src folder
 *
 */
public class MyRemoteClient {

	public static void main(String[] args) {
		MyRemoteClient client = new MyRemoteClient();
		client.go();
	}
	
	public void go() {
		try {
			MyRemote service = (MyRemote) Naming.lookup("//localhost/RemoteHello");
			String s = service.sayHello();
			System.out.println(s);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
