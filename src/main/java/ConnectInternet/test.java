package ConnectInternet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;


/**
 * Created by Roman on 30.08.2017.
 */

// додає розряди числа
public class test {

    public static void main(String[] args)  {
        int n = 355;
        String a = String.valueOf(n);
        int sum = 0;
        char[] z = a.toCharArray();
        for (int i = 0; i<z.length; i++) {
            a = String.valueOf(z[i]);
            int b = Integer.valueOf(a);
            System.out.println(b);
            sum += b;
        }
        System.out.println(sum);
    }
}

// шукає імя людини, яка прибігла найшвидше
class tsss{
    private int name ;
    public static void main(String[] args) {

        String[] names = { "Elena", "Thomas", "Hamilton", "Suzie", "Phil", "Matt", "Alex", "Emma", "John", "James", "Jane", "Emily", "Daniel", "Neda", "Aaron", "Kate" };
        int[] times = { 341, 273, 278, 329, 445, 402, 388, 275, 243, 334, 412, 393, 299, 343, 317, 265 };
        int aa = times[0] ;
        int bb = 0;
        for (int i =0 ; i<times.length; i++) {
            if( aa > times[i] ) {
                aa = times[i];
                bb=i;
            }
        }

        System.out.println(names[bb] + " result:"+ times[bb]);
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

}



// Шукає всі прості числа
class esss{
    public static void main(String[] args) {
//        int[] mas ={2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51};
//        System.out.println(mas[0]);
//        for(int i=0; i<mas.length; i++) {
//            for (int j=0; j<i; j++){
//                int d = mas[i]%mas[j];
//                if (d  == 0) {
////                    System.out.println(mas[i]);
//                    break;
//                } else {
//                    if (j == i-1)
//                    System.out.println(mas[i]);
//                }
//            }
//        }
//
//        tsss t = new tsss();
//        t.setName(5);
//        System.out.println(t.getName() + "dsdsdsff");
         }
}


// Видає найбільше та найменше число в масиві та їх індекс
class  OneLoop {
    public static void  main(String[] args) {
        Integer[] mas ={2,30,49,15,56,27,83,95,10,51};
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(mas));
        TreeSet<Integer> treeSet = new  TreeSet<Integer> (list);

        int higher = treeSet.pollLast();
        System.out.println("Найбільший елемент - " + higher + " його індекс - " + list.indexOf(higher));

        int lower = treeSet.pollFirst();
        System.out.println("Найменший елемент - " + lower + " його індекс - " + list.indexOf(lower));

        higher = treeSet.pollLast();
        System.out.println("Другий найбільший елемент - " + higher + " та його індекс - " + list.indexOf(higher));

        lower = treeSet.pollFirst();
        System.out.println("Другий найменший елемент - " + lower + " та його індекс - " + list.indexOf(lower));

        System.out.println(treeSet);
        Integer[] m = new Integer[treeSet.size()];
        m = treeSet.toArray(m);
        for (int i: m) {
            System.out.print(i+", ");
        }
    }
}

class TwoVumirnuyList {
    public static void main(String[] args) {
        List<ArrayList<Integer>> list = new ArrayList<>();
        ArrayList<Integer> array = new ArrayList<>();
        ArrayList<Integer> array1 = new ArrayList<>();
        list.add(0, array);
        list.add(1, array1);
        array.add(0,5);
        array.add(1,3);
        array1.add(0,4);
        list.get(1).add(8);
//        System.out.println(list);
//        System.out.println(array);
//        System.out.println((array1));

        int[] a = new int[0];
        for (int i=0; i < a.length; i++ ) {
            a[i] = i;
            System.out.println(i+"  " + a[i]);
        }
        array.add(2, 9);
array.remove(0);


//        if(array.indexOf(array.get(2)) == array.size()-1) System.out.println("size  " );
    }
}