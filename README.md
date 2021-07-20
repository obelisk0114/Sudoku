# Sudoku

�o�� project �t���U������
+ �ƿW�ѵ� (Sudoku solver)

+ �ƿW�X�D (Sudoku generator)

�ƿW�� `1` �}�l�A�ϥ� `-1` �N��Ů� (empty cell)�C

## ��Ƨ� Folder

�o�� project �ϥΤU����Ƨ��ӰϤ�
+ �ƥ�_Backup�G�ƥ��ɮצs���Ƨ��A`SudokuGenerator.jar` �O�O�H�g���ƿW���;��C

+ ���_Documents�G�s��@�Ǭ�������ơC

+ �ƿW�D��_Sudoku-problems�G�����@�ǼƿW�D�ءA�i�H���� test cases�C

## Packages

�o�� project �t���U���� packages

+ ���h package (default package)

+ dlx

+ exercise

+ Sudoku_puzzle


### ���h package (default package)

�ѼƿW���{���H�ά����u��{���C�ѼƥH�Υ\��p�U�ҥܡG

| �Ѽ� (arguments) | �\�� (functionality) | �B�~���� (additional info) |
| :--- |  :---                  |        :---                |
| `-i` | Ū���ƿW�D�ت���r�ɮ� <br /> (Read sudoku puzzle from `.txt` file) | �Y�[�J�o�ӰѼơA�|�ͦ��ѵ���� <br /> (will generate solution `.txt` file) |
| `-o` | ���ͼƿW�D�ت��ѵ��ɮ� <br /> (Calculate sudoku puzzle solution and generate the `.txt` file) |  |
| `-d` | ���θѪk <br /> (Disable the method) | naive: `naive` <br /> DLX: `dlx`�B`dancinglinks`�B`dancinglinksx` |

+ �D�{�� (main function)�G`SudokuSolver.java`�C

```
java -jar Sudoku.jar

// sudoku.txt as input sudoku puzzle
java -jar Sudoku.jar -i sudoku.txt

// sudokuSolution.txt is the generated sudoku puzzle solutions
java -jar Sudoku.jar -o sudokuSolution.txt

// disable DLX solver
java -jar Sudoku.jar -d dlx
java -jar Sudoku.jar -d dancinglinksx
java -jar Sudoku.jar -d dancinglinks

// disable naive solver
java -jar Sudoku.jar -d naive

// sudoku.txt is the input sudoku puzzle. sudokuSolution.txt is the output sudoku puzzle solutions.
java -jar Sudoku.jar sudoku.txt sudokuSolution.txt

java -jar Sudoku.jar -i sudoku.txt -o sudokuSolution.txt
java -jar Sudoku.jar -i sudoku.txt -d naive
java -jar Sudoku.jar -o sudokuSolution.txt -d naive

java -jar Sudoku.jar -i sudoku.txt -o sudokuSolution.txt -d naive
```
    
+ �u��{�� (tool provider)�G`Tools.java`�C
          
    �i�HŪ���ƿW�D�ت���r�ɮ� (input txt file)�A�άO�ϥ���L��J�ƿW (keyboard input)�C
    �P�ɤ]�]�t��X�ѵ������� (output txt file) �H������ (verify)�C
        
+ �ѼƿW���{�� (solver)�G`Sudoku.java`�C

    �@��Ѫk�A�O���C�ӪŮ�i�H��J���Ʀr�A�ϥΦ^���k (backtracking) �ӴM��ѵ��C

### dlx

�ϥ�`�R���� (Dancing Links)`�ӸѼƿW�C

`�R���� (Dancing Links)`�A�]��`DLX`�A�O�� Donald Knuth ���X����Ƶ��c�A�ت��O�ֳt��{�L���X�� `X�t��k (Algorithm X)`�C
`X�t��k`�O�@�ػ��j�t��k�A�ɶ������פ��T�w (nondeterministic)�A�`���u�� (depth-first)�A�q�L�^�� (backtracking) �M��`��T�л\���D (exact cover problem)`�Ҧ��i�઺�ѡC

### exercise

�m�ߥΪ� package�A��³ƥ��C

### Sudoku_puzzle�G�ƿW���;�(Sudoku generator)�A�ݸ� (Under construction)�C

�� 2 �ا@�k�A����k�B��Ūk�C
+ ����k�G�ϥΤ@�ոѵ��A���h�X��C
+ ��Ūk�G�Ѫťժ��ƿW�A��W�X��C
