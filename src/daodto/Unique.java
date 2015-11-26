package daodto;

import java.util.ArrayList;
import java.util.List;

public class Unique {


	/**
	 * リスト内の数字の重複を排除する
	 * @param arg0
	 * @return
	 */
	public static  ArrayList<Integer> unique(List<Integer> arg0) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int i = 0; i < arg0.size(); i++) {
			int x = arg0.get(i);
			if (!ret.contains(x)) {
				ret.add(x);
			}
		}
		return ret;
	}

}
