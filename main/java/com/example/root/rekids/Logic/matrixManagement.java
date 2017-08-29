import java.util.Random;

public class matrixManagement {
	String[][] matrix;
	String[][] matrixShown;
	regexManagement regex;
	String matrixEntry;

	matrixManagement() {
		matrix = new String[5][5];
		regex = new regexManagement();
		matrixEntry = "";
	}

	public String[][] generateMatrix(String[] Alphabet, String exp) {
		for (int i = 0; i < 5; i++) {
			matrixEntry = regex.generateLanguage(Alphabet, exp);
		
			for (int j = 0; j < 5; j++) {
				matrix[i][j] = String.valueOf(matrixEntry.charAt(j));
		
			
		}
			}

		return matrix;
	}
	
	public String[][] generateFMatrix() {
		matrixShown=matrix;
		Random random = new Random();
		int num;
		for(int i=0;i<5;i++) {
			num= 1 + random.nextInt(4);
			matrixShown[i][num]="F";
		}
		return matrixShown;
	}
	
	public void printMatrix(){
		for(int i=0; i <5; i++) {
			System.out.println("");
			for(int j=0; j <5; j++) {
				System.out.print(matrix[i][j]);
			}
		}
		
	}

}
