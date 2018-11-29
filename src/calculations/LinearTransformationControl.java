package calculations;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

public class LinearTransformationControl {
	private static final int ALLOCATE = 2048;
	private LinearTransformation currentLT;
	private List<LinearTransformation> favorites;

	public LinearTransformationControl() throws IOException {
		favorites = new LinkedList<>();
	}

	public String update(String line) {
		if (line.equals(""))
			return null;

		LinearTransformation.Matrix ltMatrix = stringToMatrix(line);
		currentLT = new LinearTransformation(ltMatrix);
		return currentLT.getEquation();
	}

	public boolean loadFavorites() throws IOException {
		File ltFavorites = new File("src/files/LtFavorites");
		if (ltFavorites.listFiles().length == 0)
			return false;
		for (File file : ltFavorites.listFiles()) {
			if (file.isDirectory())
				continue;

			Path path = Paths.get("src/files/LtFavorites/", file.getName());
			FileChannel outFileChannel = FileChannel.open(path, StandardOpenOption.READ);
			ByteBuffer byteBuffer = ByteBuffer.allocate(ALLOCATE);

			outFileChannel.read(byteBuffer);
			outFileChannel.close();
			byteBuffer.flip();

			IntBuffer intBuffer = byteBuffer.asIntBuffer();
			CharBuffer charBuffer = byteBuffer.asCharBuffer();
			DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();

			int count = 0;
			int nameLength = intBuffer.get();
			count += Integer.BYTES;

			StringBuilder sc = new StringBuilder();

			charBuffer.position(2);
			for (int i = 0; i < nameLength; i++) {
				sc.append(charBuffer.get());
				count += Character.BYTES;
			}

			intBuffer.position((int) Math.ceil((double) count / Integer.BYTES));
			int columns = intBuffer.get();
			int rows = intBuffer.get();
			count += Integer.BYTES * 2;

			double[][] matrix = new double[rows][columns];
			doubleBuffer.position((int) Math.ceil((double) count / Double.BYTES));

			for (int i = 0; i < rows; i++)
				for (int j = 0; j < columns; j++)
					matrix[i][j] = doubleBuffer.get();

			LinearTransformation.Matrix ltMatrix = new LinearTransformation.Matrix(matrix);
			LinearTransformation lt = new LinearTransformation(ltMatrix);
			lt.setName(sc.toString());

			favorites.add(lt);
		}
		
		return true;
	}

	public LinearTransformation getCurrentLT() {
		return currentLT;
	}

	public String setCurrentLT(String line) {
		LinearTransformation.Matrix ltMatrix = stringToMatrix(line);
		currentLT.setMatrix(ltMatrix);
		return currentLT.getEquation();
	}

	public void setCurrentLT(LinearTransformation lt) {
		currentLT = lt;
	}

	public List<LinearTransformation> getFavorites() {
		return favorites;
	}

	public void addFavorites() throws IllegalArgumentException, IOException {
		if (currentLT.getName().equals(""))
			throw new IllegalArgumentException();

		favorites.add(currentLT);
		ByteBuffer byteBuffer = ByteBuffer.allocate(ALLOCATE);

		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();

		int count = 0;

		// Guardamos la longitud del nombre
		intBuffer.put(currentLT.getName().length());
		count += Integer.BYTES;

		charBuffer.position(2);
		for (char c : currentLT.getName().toCharArray()) {
			charBuffer.put(c);
			count += Character.BYTES;
		}

		intBuffer.position((int) Math.ceil((double) count / Integer.BYTES));
		intBuffer.put(currentLT.getMatrix().getColumns());
		intBuffer.put(currentLT.getMatrix().getRows());
		count += 2 * Integer.BYTES;

		doubleBuffer.position((int) Math.ceil((double) count / Double.BYTES));
		for (int i = 0; i < currentLT.getMatrix().getRows(); i++)
			for (int j = 0; j < currentLT.getMatrix().getColumns(); j++) {
				doubleBuffer.put(currentLT.getMatrix().get(i, j));
				count += Double.BYTES;
			}

		Path path = Paths.get("src/files/LtFavorites/", currentLT.getName() + ".txt");

		FileChannel outFileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		outFileChannel.write(byteBuffer);
		outFileChannel.close();

	}

	public String applyTL(String vector) throws IllegalDimensionException, IllegalArgumentException {
		if (currentLT == null)
			throw new IllegalArgumentException();

		if (vector == "")
			return "";

		LinearTransformation.Matrix vectorMat = stringToMatrix(vector);
		if(vectorMat.getColumns() > 1)
			throw new IllegalDimensionException();
			
		LinearTransformation.Matrix result = currentLT.apply(vectorMat);
		StringBuilder sc = new StringBuilder();
		for (int i = 0; i < result.getMatrix().length; i++) {
			for (int j = 0; j < result.getMatrix()[i].length; j++) {
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
		for (int i = 0; i < lines.length; i++) {
			matrixStr[i] = lines[i].trim().split(" ");
		}

		double[][] matrix = new double[matrixStr.length][matrixStr[0].length];

		for (int i = 0; i < matrixStr.length; i++) {
			for (int j = 0; j < matrixStr[i].length; j++) {
				matrix[i][j] = Double.parseDouble(matrixStr[i][j]);
			}
		}

		return new LinearTransformation.Matrix(matrix);
	}

	public void reset() {
		currentLT = null;
	}
	
	public void deleteFavorites() {
		File ltFavorites = new File("src/files/LtFavorites");
		if (ltFavorites.listFiles().length == 0)
			return;
		for (File file : ltFavorites.listFiles()) {
			file.delete();
		}
		
		favorites.clear();
	}

}
