    package com.watermelon.data_structure;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;

    public class A17RecursionPermutation {
        public static void main(String[] args) {
            List<Integer> directions = new ArrayList<>();
            directions.add(1);
            directions.add(2);
            directions.add(3);
            directions.add(4);

            List<List<Integer>> result = permute(directions);
            for (List<Integer> permutation : result) {
                System.out.println(permutation);
            }
        }

        public static List<List<Integer>> permute(List<Integer> directions) {
            List<List<Integer>> result = new ArrayList<>();
            permuteHelper(result, new ArrayList<>(), directions);
            return result;
        }

        private static void permuteHelper(List<List<Integer>> result, List<Integer> temp, List<Integer> directions) {
            if (temp.size() == directions.size()) {
                result.add(new ArrayList<>(temp));
                return;
            }
            for (int direction : directions) {
                //每一个节点都用1，,2，,3，,4尝试一下
                if (!temp.contains(direction)) {
                    temp.add(direction);
                    permuteHelper(result, temp, directions);
                    temp.remove(temp.size() - 1);
                }
            }
        }
    }
