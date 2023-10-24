package test;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<Integer> list = new ArrayList<>();

    public List<Integer> countSmaller(int[] nums) {
        if (nums.length == 1) {
            list.add(0);
            return list;
        }
        for (int i = 0; i < nums.length; i++) {
            list.add(0);
        }
        process(nums, 0, nums.length - 1);
        return list;
    }

    public void process(int[] nums, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        process(nums, l, mid);
        process(nums, mid + 1, r);
        merge(nums, l, mid, r);
    }

    public void merge(int[] nums, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int p1 = l;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= r) {
            if (nums[p1] > nums[p2]) {
                int index = p1;
                while (index <= mid) {
                    list.set(index, list.get(index) + 1);
                    index++;
                }
            }
            help[i++] = nums[p1] <= nums[p2] ? nums[p1++] : nums[p2++];
        }
        while (p1 <= mid) {
            help[i++] = nums[p1++];
        }
        while (p2 <= r) {
            help[i++] = nums[p2++];
        }
        for (int j = 0; j < help.length; j++) {
            nums[l + j] = help[j];
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.countSmaller(new int[]{5, 2, 6, 1}));

    }
}
