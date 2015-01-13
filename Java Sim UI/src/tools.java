import java.util.Arrays;


public class tools {
	public static <T> float[] concatfloat(float[] first, float[] second) {
		float[] result = Arrays.copyOf(first, first.length + second.length);
		  System.arraycopy(second, 0, result, first.length, second.length);
		  return result;
		}
	public static <T> int addintarry(int [] intarry) {
		int z = 0;
		for (int i=0; i < intarry.length; i++) {
		    z = z + intarry[i];
		}
		return z;
	}
	public static <T> int [] setintarrayoffset(int [] intarry) {
		int [] z = new int [intarry.length] ;
		int count =0;
		z[0]=0;
		for (int i=1; i < intarry.length; i++) {
		    z[i]= z[i-1]+intarry[i-1];
		}
		return z;
	}
}
