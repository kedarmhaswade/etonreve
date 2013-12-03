import java.util.*;
import java.io.InputStream;
/**
 * Demonstrates a solution to the Evernote problem #3.
 * Input: array a, of integers
 * Output: array b, such that b[i] = Πa[j] ∀ j ≠ i
 * @author: Kedar (kedar@jymob.com)
 */
public final class ExMultiplier {
  public static void main(String[] args) { 
    long[] output = exProduct(input(System.in));
    for (long l : output)
      System.out.println(l);
  }

  private static int[] input(InputStream is) {
    Scanner s = new Scanner(is);
    int len = 0;
    if (s.hasNextLine()) {
      len = s.nextInt();
    } else
      throw new RuntimeException("invalid input");
    int[] input = new int[len];
    for (int i = 0; i < len; i++)
      input[i] = s.nextInt();
    return input;
  }
  private static long[] exProduct(int[] input) {
    long mega = 1L;
    int zIndex = -1; //first zero index
    int nz = 0; //no of zeroes
    long[] output = new long[input.length];
    for (int i = 0; i < input.length; i++) {
      if (input[i] == 0) {
        zIndex = i;
        nz += 1;
        if (nz > 1) {
          mega = 0;
          break; //no need to continue if nz > 1
        }
        if (nz == 1)
          mega *= 1;
      } else
        mega *= input[i];
    }
    if (nz > 1) {
      for (int i = 0; i < output.length; i++)
        output[i] = 0L; //perhaps not needed -- Java initializes array elements to 0.
    } else if (nz == 1) {
      for (int i = 0; i < output.length; i++)
        if (i == zIndex)
          output[i] = mega;
        else
          output[i] = 0L;
    } else {
      assert nz == 0 : "expected: no zeroes, got: " +  nz + " zeroes";
      for (int i = 0; i < output.length; i++)
        output[i] = mega/input[i];
    }
    return output;
  }
}

