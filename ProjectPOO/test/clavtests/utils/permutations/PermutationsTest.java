package clavtests.utils.permutations;

import clavtests.utils.TestSets;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class PermutationsTest {

	private int facto(int n) {
		int res = 1;
		for (int i = 1; i <= n; i++) {
			res *= i;
		}
		return res;
	}

	private int binCoeff(int k, int n) {
		return facto(n) / (facto(k) * facto(n - k));
	}

	private void verifyPerm(ArrayList list, int k) {
		ArrayList<ArrayList> perm = Permutations.allKInN(list, k);
		if (k < 0 || k > list.size()) {
			Assert.assertEquals("Permutations should be empty for k out of 0 - n", 0, perm.size());
		}
		else if(k==0){
			Assert.assertEquals("Only one void permutation expected for k == 0 ",1,perm.size());
			Assert.assertEquals("Only one void permutation expected for k == 0",0,perm.get(0).size());
		}
		else {
			Assert.assertEquals("Invalid numbers of permutations for n = "+list.size() + " k = "+k, binCoeff(k, list.size()), perm.size());
			for (ArrayList p : perm) {
				Assert.assertEquals("Invalid size of permutations for k = " + k, k, p.size());
			}
		}
	}

	private void completePermTest(Object[] array) {
		ArrayList<Object> list = new ArrayList<>(Arrays.asList(array));
		for (int k = 0; k <= array.length; k++) {
			verifyPerm(list, k);
		}
	}

	@Test
	public void idPermTest(){
		completePermTest(TestSets.sampleIds);
	}

	@Test
	public void idShuffleTest(){
		ArrayList<ArrayList> s = Permutations.shuffle(new ArrayList(Arrays.asList(TestSets.sampleIds)));
		for(ArrayList l : s){
			for(Object o : l){
				System.out.println((String) o);
			}
		}
	}


}
