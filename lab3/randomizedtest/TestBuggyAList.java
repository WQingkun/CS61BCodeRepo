package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  @Test
    public void testThreeAddThreeRemove(){
      AListNoResizing<Integer> lst = new AListNoResizing<>();
      AListNoResizing<Integer> lbug = new AListNoResizing<>();
      for(int i = 0; i < 3; i++){
          lst.addLast(i);
          lbug.addLast(i);
      }
      assertEquals(lst.size(), lbug.size());
      for(int i = 0; i < 3; i++){
          assertEquals(lst.removeLast(), lbug.removeLast());
      }

  }
  @Test
    public void randomizedTest(){
      AListNoResizing<Integer> L = new AListNoResizing<>();
      BuggyAList<Integer> l = new BuggyAList<>();
      int N = 5000;
      for (int i = 0; i < N; i += 1) {
          int operationNumber = StdRandom.uniform(0, 4);
          if (operationNumber == 0) {
              // addLast
              int randVal = StdRandom.uniform(0, 100);
              L.addLast(randVal);
              l.addLast(randVal);
          } else if (operationNumber == 1) {
              // size
              int size = L.size();
              int lsize = l.size();
              assertEquals(size, lsize);
          } else if (operationNumber == 2 && L.size() != 0) {
              //getLast
              int last = L.getLast();
              int llast = l.getLast();
              assertEquals(last, llast);
          } else if (operationNumber == 3 && L.size() != 0) {
              //removeLast
              int Lrm = L.removeLast();
              int lrm = l.removeLast();
              assertEquals(Lrm, lrm);
          }
      }
  }
}
