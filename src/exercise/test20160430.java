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
		//��������
        if (s.length() == 0) {  
            System.out.println(n + "  ---  " + ++c);  
        } else {  
            for (int i = 0; i < s.length(); ++i) { 
            	/*�C���եΪ��ѼƳ��|�O�Ѽ�s�����״��1�A��n�W�[1�A
            	 * ���쵲������s.length() == 0���u�A���j����*/
                printAllArray(s.substring(1), n + s.charAt(0)); 
                //�r�ꥭ���@��,�O�Ҳ��ͤ����Ƶ��G
                s = s.substring(1) + s.charAt(0);  
            }  
        }
	}
	/*���j�|�i��s.length()���A�ӥB��ƽեΦۨ����a����`��
	 * for (int i = 0; i < s.length(); ++i)���A�ҥH�|���L�Xs.length()!�����G�C*/

}
