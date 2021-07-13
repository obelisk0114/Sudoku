package exercise;

// 2016/04/30 recursion exercise
public class SudokuEx_recursion {
	
	// 利用遞迴窮舉
	public void recursive(int[] intArray, int idx){
		if (idx >= intArray.length){
			return;
		}
		int save = intArray[idx];
		for(int i = 0; i < intArray.length; i++){
			intArray[idx] = i;
			printans(intArray);
//			if (authentic(intArray)) {
//				printans(intArray);
//			}
			recursive(intArray, idx+1);
		}
		intArray[idx] = save;
	}

	public void test2016(){
		int[] intArray = {-1,-1,-1,-1};
		recursive(intArray, 0);
	}
	
	// 列印
	public void printans(int[] intArray){
		for(int i = 0; i < intArray.length; i++){
			if(intArray[i] == -1){
				return;
			}
		}
		for(int i = 0; i < intArray.length; i++){
			System.out.print(intArray[i] + " ");
		}
		System.out.println();
	}
	
	// 檢查是否有重複項目
	public boolean authentic(int[] intArray){
		for (int i = 0; i < intArray.length-1; i++) {
			for (int j = i+1; j < intArray.length; j++) {
				if (intArray[i] == intArray[j]) {
					return false;
				}
			}
		}
		return true;
	}

}
