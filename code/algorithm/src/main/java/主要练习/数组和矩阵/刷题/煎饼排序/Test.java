package 主要练习.数组和矩阵.刷题.煎饼排序;
import java.util.ArrayList;
import java.util.List;
public class Test {
    public List<Integer> pancakeSort(int[] arr) {
        List<Integer> ret = new ArrayList<Integer>();
        for (int n = arr.length; n > 1; n--) {
            int index = 0;
            for (int i = 1; i < n; i++) {
                if (arr[i] >= arr[index]) {
                    index = i;
                }
            }
            if (index == n - 1) {
                continue;
            }
            reverse(arr, index);
            reverse(arr, n - 1);
            ret.add(index + 1);
            ret.add(n);
        }
        return ret;
    }

    public void reverse(int[] arr, int end) {
        for (int i = 0, j = end; i < j; i++, j--) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        int[] arr = {3,2,4,1};
        List<Integer> res = test.pancakeSort(arr);
        for (Integer re : res) {
            System.out.println(re);
        }
    }
}
