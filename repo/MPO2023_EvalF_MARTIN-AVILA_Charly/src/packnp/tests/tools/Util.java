package packnp.tests.tools;
 
import java.util.List;
 
 
public class Util {
 
	public static boolean estVide(List<List<Integer>>g) {
		boolean res=g!=null;
		int c;
		c=0;
		while (res && c<g.size()) {
			res=res&&g.get(c)!=null && g.get(c).size()==0;
			c++;
		}
		return res;
	}
	
	public static boolean egalesNonNulles(int[][] m1, int[][] m2) {
		if (m1==null || m2==null || m1.length!=m2.length || m1[0].length!=m2[0].length) {
			return false;
		} else {
			boolean res=true;
			int c,l;
			c=0;
			while (c<m1.length && res) {
				l=0;
				while (l<m1[c].length && res) {
					if (m1[c][l]!=m2[c][l]) {
						res=false;
					}
					l++;
				}
				c++;
			}
			return res;
		}
	}
	
	
	public static boolean equals(List<List<Integer>> l1, List<List<Integer>>l2) {
		if (l1==null || l2==null || l1.size()!=l2.size()) {
			return false;
		} else {
			for (int col=0; col<l1.size(); col++) {
				if (!l1.get(col).equals(l2.get(col))) {
					return false;
				}
			}
			return true;
		}
	}
 
}
