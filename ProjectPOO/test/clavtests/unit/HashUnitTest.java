package clavtests.unit;
import clavtests.utils.permutations.Permutations;
import org.clav.utils.HashUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static clavtests.utils.TestSets.sampleIds;

public class HashUnitTest {
	@Test
	public void orderAndCollisionTest(){
		ArrayList l = new ArrayList(Arrays.asList(sampleIds));
		HashSet<String> codes = new HashSet<>();
		for(int k = 1;k<=sampleIds.length;k++){
			ArrayList<ArrayList> perm = Permutations.allKInN(l,k);
			int index = 1;
			for(ArrayList arrayList : perm){

				ArrayList<ArrayList> shuffle = Permutations.shuffle(arrayList);
				String code = HashUtils.hashStringList(shuffle.get(0));
				Assert.assertFalse("No collision for different ids",codes.contains(code));
				codes.add(code);
				//System.out.println("Permutation K = "+k+" #"+index+" "+code);
				index++;
				int sindex = 1;
				for(ArrayList s : shuffle){
					//System.out.println("Rotation #"+sindex);
					sindex++;
					Assert.assertEquals("Code of permutations should be equals",code,HashUtils.hashStringList(s));
				}
			}
		}
	}

	@Test
	public void hashJoe(){
		ArrayList<String> joes = new ArrayList<>();
		joes.add("joe");
		joes.add("joe");
		System.out.println("Hashing joe "+HashUtils.hashStringList(joes));
	}
}
