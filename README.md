# Sudoku

這個 project 含有下面部分
+ 數獨解答 (Sudoku solver)

+ 數獨出題 (Sudoku generator)

## 資料夾 Folder

這個 project 使用下面資料夾來區分
+ 備份_Backup：備份檔案存放資料夾，`SudokuGenerator.jar` 是別人寫的數獨產生器。

+ 資料_Documents：存放一些相關的資料。

+ 數獨題目_Sudoku-problems：收集一些數獨題目，可以做為 test cases。

## Packages

這個 project 含有下面的 packages

+ 頂層 package (default package)

+ dlx

+ exercise

+ Sudoku_puzzle


### 頂層 package (default package)
+ 主程式 (main function)：`SudokuSolver.java`。

```
java -jar Sudoku.jar -i sudoku.txt
```
    
+ 工具程式 (tool provider)：`Tools.java`。
          
    可以讀取數獨題目的文字檔案 (input txt file)，或是使用鍵盤輸入數獨 (keyboard input)。
    同時也包含輸出解答的部分 (output txt file) 以及驗證 (verify)。
        
+ 解數獨的程式 (solver)：`Sudoku.java`。

    一般解法，記錄每個空格可以填入的數字，使用回溯法 (backtracking) 來尋找解答。

### dlx

使用`舞蹈鏈 (Dancing Links)`來解數獨。

`舞蹈鏈 (Dancing Links)`，也稱`DLX`，是由 Donald Knuth 提出的資料結構，目的是快速實現他提出的 `X演算法 (Algorithm X)`。
`X演算法`是一種遞迴演算法，時間複雜度不確定 (nondeterministic)，深度優先 (depth-first)，通過回溯 (backtracking) 尋找`精確覆蓋問題 (exact cover problem)`所有可能的解。

### exercise

練習用的 package，單純備份。

### Sudoku_puzzle：數獨產生器(Sudoku generator)，待補 (Under construction)。

有 2 種作法，挖格法、填空法。
+ 挖格法：使用一組解答，挖去幾格。
+ 填空法：由空白的數獨，填上幾格。
