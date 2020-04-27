package java8InAction.chapter7;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SplitIteratorDemo {

	public static void main(String[] args) {
		final String SENTENCE =" Hey bro   my name is anthony   gonsalvi";
		System.out.println("Iterative word count: "+wordCount(SENTENCE));
		
		Stream<Character> stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
		System.out.println("Sequential stream word count: "+countwordsStream(stream));
		
		stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
		System.out.println("Parallel stream word count: "+countwordsStream(stream.parallel()));
		
		
		Spliterator<Character> spliterator = new WordcounterSplitIterator(SENTENCE);
		Stream<Character> streamSI = StreamSupport.stream(spliterator, false);
		System.out.println("Sequential stream word count with custom SplitIteartor: "+countwordsStream(streamSI));

		spliterator = new WordcounterSplitIterator(SENTENCE);
		streamSI = StreamSupport.stream(spliterator, true);
		System.out.println("Parallel stream word count with custom SplitIteartor: "+countwordsStream(streamSI.parallel()));
		
	}
	
	private static int countwordsStream(Stream<Character> stream) {
		return stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine).getCounter();
	}
	
	private static int wordCount(String s) {
		int count = 0;
		boolean lastspace = true;
		for(Character c: s.toCharArray()) {
			if(Character.isWhitespace(c)) {
				lastspace = true;
			}else {
				if(lastspace) count++;
				lastspace = false;
			}
		}
		return count;
	}
	
	static class WordCounter{
		private final int counter;
		private final boolean lastSpace;
		
		public WordCounter(int counter, boolean lastSpace) {
			this.counter = counter;
			this.lastSpace = lastSpace;
		}
		
		public WordCounter accumulate(Character c) {
			if(Character.isWhitespace(c)) {
				return lastSpace? this: new WordCounter(counter, true);
			}else {
				return lastSpace ? new WordCounter(counter+1, false): this;
			}
		}
		
		private WordCounter combine(WordCounter wordCounter) {
			return new WordCounter(counter+wordCounter.counter, wordCounter.lastSpace);
		}

		public int getCounter() {
			return counter;
		}
		
	}
	
	static class WordcounterSplitIterator implements Spliterator<Character>{

		private final String string;
		private int currentChar = 0;
		
		public WordcounterSplitIterator(String string) {
			this.string = string;
		}
		@Override
		public boolean tryAdvance(Consumer<? super Character> action) {
			currentChar = currentChar++;
			if(currentChar >= string.length()) return false;
			action.accept(string.charAt(currentChar++));
			return currentChar < string.length();
		}

		@Override
		public Spliterator<Character> trySplit() {
			int currentSize = string.length() - currentChar;
			if(currentSize < 10) {
				return null;
			}
			for(int splitpos = currentSize/2+currentChar; splitpos<string.length(); splitpos++) {
				if(Character.isWhitespace(string.charAt(splitpos))) {
					Spliterator<Character> spliterator = new WordcounterSplitIterator(string.substring(currentChar,splitpos));
					currentChar = splitpos;
					return spliterator;
				}
			}
			return null;
		}

		@Override
		public long estimateSize() {
			return string.length() - currentChar;
		}

		@Override
		public int characteristics() {
			return ORDERED+SIZED+SUBSIZED+NONNULL+IMMUTABLE+CONCURRENT;
		}
		
	}

}
