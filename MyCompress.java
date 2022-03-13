public class MyCompress {
	public static void main(String[] args) {
		String mode = args[0];
		String fileName = args[1];
		
		Compress compress = new Compress();
		UnCompress unCompress = new UnCompress();

		if(mode.equals("-c") || mode.equals("-C"))
			compress.compress(fileName);
	
		if(mode.equals("-x") || mode.equals("-X"))
			unCompress.unCompress(fileName);
	}
}