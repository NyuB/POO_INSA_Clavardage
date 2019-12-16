package clavtests.utils.permutations;

import java.util.ArrayList;

public class Permutations {

	public static ArrayList<ArrayList> allKInN(ArrayList list, int k) {
		ArrayList<ArrayList> res = new ArrayList<>();
		int n = list.size();
		if (k > n || k < 0) return res;
		ArrayList<PermutItem> queue = new ArrayList<>();
		queue.add(new PermutItem());
		while (queue.size() > 0) {
			PermutItem item = queue.get(0);
			queue.remove(0);
			int l = item.current.size();
			int i = item.nextIndex;
			int nb = k - l;
			if (nb == 0) {
				res.add(item.current);
			} else {
				for (int index = i; index <= n - nb; index++) {
					ArrayList aux = new ArrayList();
					aux.addAll(item.current);
					aux.add(list.get(index));
					queue.add(new PermutItem(index+1,aux));

				}
			}
		}
		return res;

	}
	public static ArrayList<ArrayList> shuffle(ArrayList list){
		ArrayList copy = new ArrayList(list);
		ArrayList<ArrayList> res = new ArrayList<>();
		ArrayList<ShuffleItem> queue = new ArrayList<>();
		queue.add(new ShuffleItem(new ArrayList(),copy));
		while(queue.size()>0){
			ShuffleItem item = queue.get(0);
			queue.remove(0);
			if(item.left.size()==0){
				res.add(item.current);
			}
			else{
				for(Object o : item.left){
					ArrayList aux = new ArrayList(item.current);
					ArrayList auxLeft = new ArrayList(item.left);
					aux.add(o);
					auxLeft.remove(o);
					queue.add(new ShuffleItem(aux,auxLeft));
				}
			}
		}
		return res;

	}
}
