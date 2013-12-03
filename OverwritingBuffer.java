import java.util.Scanner;
import java.io.InputStream;
import java.util.regex.*;
/**
 * Demonstrates an overwriting circular buffer. It acts like a FIFO queue of strings.
 * Note: Suitable only for single threaded use.
 * The distinguishing factor is that the queue always inserts elements
 * at the tail and allows overwriting elements while doing so.
 * @author: Kedar (kedar@jymob.com)
 */
class OverwritingBuffer<T> {
  public static void main(String[] args) { 
    test(System.in);
  }
  private static void test(InputStream is) {
    Scanner s = new Scanner(is);
    int size;
    if (s.hasNextLine()) {
      size = Integer.valueOf(s.nextLine());
    } else
      throw new RuntimeException("invalid input, specify #items");
    //System.out.println("size: " + size);
    OverwritingBuffer<String> ocb = new OverwritingBuffer<>(size);
    while (s.hasNextLine()) {
      String line = s.nextLine();
      //System.out.println("line: " + line);
      Scanner ss = new Scanner(line);
      if (ss.findInLine("^([ARLQ]) ?(\\d+)?$") == null) { //not bingo! but works
        System.out.println("no match");
        break;
      }
      MatchResult r = ss.match();
      String command = r.group(1);
      //System.out.println("command: " + command);
      try {
        if ("Q".equals(command))
          break;
        if ("L".equals(command)) {
          ocb.list();
        }
        else if ("R".equals(command)) {
          int n = Integer.valueOf(r.group(2));
       //   System.out.println("n: " + n);
          for (int i = 0; i < n; i++)
            ocb.remove();
        } else if ("A".equals(command)) {
          int n = Integer.valueOf(r.group(2));
        //  System.out.println("n: " + n);
          for (int i = 0; i < n; i++)
            ocb.add(s.nextLine());
        } else {
          //ignore or throw exception?
        }
      } finally {
        ss.close();
      }
    }
  }
  // class implementation
  private final int size;
  //constraints of the problem should work ok with integers, but just in case
  private long head, tail;
  private final T[] buffer;
  //allow only T items in the buffer, but runtime type is Object
  @SuppressWarnings("unchecked") 
  public OverwritingBuffer(int size) {
    if (size < 0 || size > 10_000)
      throw new IllegalArgumentException("0<=N<=10000, invalid N: " + size);
    this.size = size;
    this.buffer =  (T[]) new Object[size];
    this.head = 0L;
    this.tail = 0L;
  }

  public void add(T item) {
    buffer[(int) tail % size] = item; //buffer is never 'full', we overwrite
    tail += 1;
    if (tail - head > size)
      head += 1L; //possible overflow, but avoid checking every time
    //invariant: tail-head <= size
  }
  public T remove() {
    if (head == tail)
      throw new IllegalStateException("buffer is empty");
    T x = buffer[(int) head % size];
    head += 1L; //possible overflow, but avoid checking every time
    return x;
  }
  public void list() {
    for (int i = (int) head; i < (int) tail; i++)
      System.out.println(buffer[i%size]);
  }
}

