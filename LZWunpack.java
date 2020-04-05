class LZWunpack {
	public static void main(String[] args) throws Exception {
		int topPhraseNumber = 15;
		long buffer = 0L;
		int bufferLength = 0;
		
		while (true) {
			int phraseLength = calcBitLength(topPhraseNumber);
			while (bufferLength < phraseLength) {
				
				int phraseByte = System.in.read();
				if (phraseByte < 0) break;
				
				buffer |= phraseByte << bufferLength;
				bufferLength += 8;
			}
			if (bufferLength < phraseLength) break;
			
			System.out.print((buffer & ((1 << phraseLength) - 1)) + "\n");
			
			buffer = buffer >> phraseLength;
			bufferLength -= phraseLength;
			
			topPhraseNumber++;
		}
		
		System.out.flush();
	}
	
	private static int calcBitLength(int n)
	{
		int l = 1;
		while (n > 1)
		{
			n = n >>> 1;
			l++;
		}
		return l;
	}
}