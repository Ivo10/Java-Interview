package test;

import java.util.Arrays;

class Solution {
    public int longestSubarray(int[] nums, int limit) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (SubArray(nums, i, j) <= limit) {
                    ans = Math.max(ans, j - i);
                }
            }
        }
        return ans;
    }

    public int SubArray(int[] nums, int i, int j) {
        int[] arrayCopy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(arrayCopy, i, j + 1);
        return arrayCopy[j] - arrayCopy[i];
    }

    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        Arrays.sort(arr, 0, 3);
        System.out.println(Arrays.toString(arr));
    }
}