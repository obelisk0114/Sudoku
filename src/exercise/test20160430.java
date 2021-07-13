package exercise;

public class test20160430 {

	/**
	 * @param args
	 */
	static int c = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SudokuEx_recursion sd = new SudokuEx_recursion();
		sd.test2016();
		
		//String s = "1234";  
        //printAllArray(s);

	}
	
	static void printAllArray(String s) {  
        printAllArray(s, "");  
    }
	private static void printAllArray(String s, String n) { 
		//結束條件
        if (s.length() == 0) {  
            System.out.println(n + "  ---  " + ++c);  
        } else {  
            for (int i = 0; i < s.length(); ++i) { 
            	/*每次調用的參數都會是參數s的長度減少1，而n增加1，
            	 * 直到結束條件s.length() == 0為真，遞迴結束*/
                printAllArray(s.substring(1), n + s.charAt(0)); 
                //字串平移一位,保證產生不重複結果
                s = s.substring(1) + s.charAt(0);  
            }  
        }
	}
	/*遞迴會進行s.length()次，而且函數調用自身的地方位於循環
	 * for (int i = 0; i < s.length(); ++i)中，所以會打印出s.length()!次結果。*/

}
