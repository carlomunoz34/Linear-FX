package calculations;

import java.util.LinkedList;
import java.util.List;

public class LinearTransformationControl {
	private LinearTransformation currentLT;
	private List<LinearTransformation> history;
	private List<LinearTransformation> favorites;
	
	public LinearTransformationControl() {
		history = new LinkedList<>();
		favorites = new LinkedList<>();
		loadFavorites();
	}
	
	public String update(String line, String name) {
		if(line.equals(""))
			return null;
		
		LinearTransformation.Matrix ltMatrix = stringToMatrix(line);
		currentLT = new LinearTransformation(ltMatrix, name);
		history.add(currentLT);
		return currentLT.getEquation();
	}
	
	private void loadFavorites() {
		//TODO: Implement this function
	}

	public LinearTransformation getCurrentLT() {
		return currentLT;
	}

	public void setCurrentLT(String line) {
		LinearTransformation.Matrix ltMatrix = stringToMatrix(line);
		currentLT.setMatrix(ltMatrix);
	}

	public List<LinearTransformation> getHistory() {
		return history;
	}

	public List<LinearTransformation> getFavorites() {
		return favorites;
	}
	
	public void addHistory(LinearTransformation lt) {
		history.add(lt);
	}
	
	public void addFavorites(LinearTransformation lt) {
		if(lt.getName().equals(""))
			throw new IllegalArgumentException();
		
		favorites.add(lt);
		//TODO: Save the new lt in the archive
	}
	
	public String applyTL(String vector) throws IllegalDimensionException, IllegalArgumentException{
		if(currentLT == null)
			throw new IllegalArgumentException();
		
		if(vector == "")
			return "";
		
		LinearTransformation.Matrix vectorMat = stringToMatrix(vector);
//		vectorMat.printMat();
		LinearTransformation.Matrix result = currentLT.apply(vectorMat);
		
		
		StringBuilder sc = new StringBuilder();
		for(int i = 0; i < result.getMatrix().length; i++) {
			for(int j = 0; j < result.getMatrix()[i].length; j++) {
				sc.append(result.get(i, j));
				sc.append(" ");
			}
			sc.append("\n");
		}
		
		return sc.toString();
	}
	
	private LinearTransformation.Matrix stringToMatrix(String line) {
		String[] lines = line.trim().split("\n");
		String[][] matrixStr = new String[lines.length][];
		for(int i = 0; i < lines.length; i++) {
			matrixStr[i] = lines[i].trim().split(" ");
		}
		
		double[][] matrix = new double[matrixStr.length][matrixStr[0].length];
			
		for(int i = 0; i < matrixStr.length; i++) {
			for(int j = 0; j < matrixStr[i].length; j++) {
				matrix[i][j] = Double.parseDouble(matrixStr[i][j]);
			}
		}
		
		return new LinearTransformation.Matrix(matrix);
	}
	
	
}
