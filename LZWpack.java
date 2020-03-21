import java.io.Console;

class LZWpack {
	
	public static void main(String[] args) {
		
		int topPhraseNumber = 15;
		long buffer = 0L;
		int bufferLength = 0;
		
		while (System.console().hasNextLine()) {
			int phraseNumber = Integer.parseInt(System.console().readLine());
			//Scanner scan = new Scanner(System.in);
			//int phraseNumber = scan.nextInt();
			int phraseLength = calcBitLength(topPhraseNumber);
			
			buffer |= phraseNumber << bufferLength;
			bufferLength += phraseLength;
			
			while (bufferLength >= 8)
			{
				System.console().write(buffer & ((1 << 8) - 1));
				buffer = buffer >>> 8;
				bufferLength -= 8;
			}
		}
		
		if (bufferLength > 0)
			System.console().write(buffer);
	}
	
	private static int calcBitLength(int n)
	{
		int l = 1;
		while (n > 1)
		{
			n = n >>> 1;
			l++;
		}
	}
}