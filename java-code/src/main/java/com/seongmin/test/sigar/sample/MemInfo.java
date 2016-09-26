package com.seongmin.test.sigar.sample;
import java.util.HashMap;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

/**
* 메모리 사용량 예제
* @author kay
*
*/
public class MemInfo {

     public static void main(String[] args) throws SigarException {
          Sigar sigar = new Sigar();
         
          Mem mem = sigar.getMem();
          Swap swap = sigar.getSwap();

          HashMap<Integer, String[]> hm = new HashMap<Integer, String[]>();
         
          hm.put(0, new String[]{"total", "used", "free"} );
          hm.put(1, new String[]{format(mem.getTotal()), format(mem.getUsed()), format(mem.getFree())});
          hm.put(2, new String[]{"", format(mem.getActualUsed()), format(mem.getActualFree())});
          hm.put(3, new String[]{format(swap.getTotal()), format(swap.getUsed()), format(swap.getFree())});
         
          for(int i=0; i < hm.size(); i++) {
               mem_output(hm.get(i));
          }
     }
    
     public static String format(Long value) {
          return Long.toString(new Long(value / 1024));
     }
    
     public static void mem_output(String[] value) {
          String str = "";
          for(int i=0; i < value.length; i++) {
               str += value[i] + "\t";
          }
          System.out.println(str);
     }
}
