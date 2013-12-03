import java.util.*;
import java.io.InputStream;
/**
 * Demonstrates a solution to the Evernote problem #2.
 * Uses priority queue to list k most frequently occuring of the given n words.
 * @author: Kedar (kedar@jymob.com)
 */
public class TopK {
  public static void main(String[] args) { 
    Map<String, Integer> freq = new HashMap<>();
    int k = fill(System.in, freq);
    Queue<Map.Entry<String, Integer>> pq = toPriorityQueue(freq);
    for (int i = 0; i < k; i++)
      System.out.println(pq.poll().getKey()); //we could print the frequency as well
  }

  private static int fill(InputStream is, Map<String, Integer> f) {
    Scanner s = new Scanner(is);
    int len = 0;
    if (s.hasNextLine()) {
      len = s.nextInt();
    } else
      throw new RuntimeException("invalid input");
    for (int i = 0; i < len; i++) {
      String word = s.next(); 
      if (!f.containsKey(word))
        f.put(word, 0);
      f.put(word, f.get(word) + 1);
    }
    int k = s.nextInt();
    //implement constraints on k; here we assume that k << len
    return k;
  }

  private static PriorityQueue<Map.Entry<String, Integer>> toPriorityQueue(Map<String, Integer> f) {
    Comparator<Map.Entry<String, Integer>> c = new EntryComparator();
    PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(f.size(), c);  
    for (Map.Entry<String, Integer> entry : f.entrySet())
      pq.add(entry);
    return pq;
  }
}

class EntryComparator implements Comparator<Map.Entry<String, Integer>> {

  public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
    int freq = e2.getValue().compareTo(e1.getValue()); //compares integer frequencies
    if (freq == 0) // two words occur equally frequently
      return e1.getKey().compareTo(e2.getKey());
    return freq;
  }
}
