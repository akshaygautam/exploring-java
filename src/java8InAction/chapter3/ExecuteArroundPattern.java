package java8InAction.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteArroundPattern {

	public static void main(String[] args) throws IOException {
		String result = processFileLimited();
		System.out.println(result);
		
		//Using Lambdas to improve readability
		System.out.println("------");
		String oneLine = processFile(br -> br.readLine());
		System.out.println(oneLine);
		
		String twoLine = processFile(br -> br.readLine()+br.readLine() );
		System.out.println(twoLine);
		
		
	}
	
	private static String processFileLimited() throws IOException {
		try (BufferedReader br =
                new BufferedReader(new FileReader("lambdasinaction/chap3/data.txt"))) {
			return br.readLine();
		}
   }

	public static String processFile(BufferedReaderProcessor processor) throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader("lambdasinaction/chap3/data.txt"))){
			return processor.process(br);
		}
	}
	
	@FunctionalInterface
	private interface BufferedReaderProcessor{
		String process(BufferedReader br) throws IOException;
	}
}
