package clavtests.utils.permutations;

import java.util.ArrayList;

public class PermutItem {
	int nextIndex;
	ArrayList current;

	public PermutItem() {
		this.current = new ArrayList();
		this.nextIndex = 0;
	}

	public PermutItem(int nextIndex, ArrayList current) {
		this.nextIndex = nextIndex;
		this.current = current;
	}
}
