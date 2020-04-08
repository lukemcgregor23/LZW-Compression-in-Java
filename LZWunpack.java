// Name: Kiera Leahy
// ID:   1159999

// Name:
// ID:

class LZWunpack {
	// I'm going to ignore this exception because
	// I don't think it throws with stdin
	public static void main(String[] args) throws Exception {
		// the maximum possible phrase number which may be output
		int topPhraseNumber = 256;
		// the buffer fills with bytes until a phrase number can be output
		long buffer = 0L;
		int bufferLength = 0;
		
		while (true) {
			// the bits representing an incoming phrase number
			int phraseLength = calcBitLength(topPhraseNumber);
			// so long as there aren't enough bits in the buffer to output a phrase number
			while (bufferLength < phraseLength) {
				// read a byte and leave the loop if EOF reached
				int phraseByte = System.in.read();
				if (phraseByte < 0) break;
				// add the byte to the buffer
				buffer |= phraseByte << bufferLength;
				bufferLength += 8;
			}
			// if the buffer still doesn't contain a phrase EOF must have been reached
			if (bufferLength < phraseLength) break;
			// print a phrase from the buffer
			System.out.print((buffer & ((1 << phraseLength) - 1)) + "\n");
			// remove it from the buffer
			buffer = buffer >> phraseLength;
			bufferLength -= phraseLength;
			// next time the phrase number may be larger
			topPhraseNumber++;
		}
		
		// make sure the system finishes outputing all phrase numbers before the program ends
		System.out.flush();
	}
	
	// calculate the number of bits required to represent a number
	// shifts and counts bit to determine how many are needed
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