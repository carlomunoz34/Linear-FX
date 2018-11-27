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
	
	private void loadFavorites() {
		//TODO: Implement this function
	}

	public LinearTransformation getCurrentLT() {
		return currentLT;
	}

	public void setCurrentLT(LinearTransformation currentLT) {
		this.currentLT = currentLT;
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
}
