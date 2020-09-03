package dp;

import container.impl.Array;

import java.util.*;
import java.util.function.BiFunction;

public class CoinChange {

    public static int changeIteration(int[] coins, int amount) {
        // when target amount is i, dp[i] is the minimal amount of coins
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for(int coin : coins) {
            dp[coin] = 1;
        }
        for(int i = 0; i < dp.length; i++) {
            for(int coin : coins) {
                if(i < coin) {
                    // cannot change i with coin
                    continue;
                }

                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }

        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    public static int changeWithSolution(int[] coins, int amount) {
        int[][] dp = new int[coins.length][amount + 1];
        /*
        amount:    0, 1, 2, 3, 4, 5
                   ----------------
        coin 1:    0  1  2  3  4  5
        coin 2:    0  +  1  +  2  +
        coin 5:    0  +  +  +  +  1

        dp[i from 0 to coins.length][j] represent minimal amount of coin at value coins[i] needed to change j
         */

        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], 1, dp[i].length, Integer.MAX_VALUE);
            dp[i][0] = 0;
        };

        for(int i = 0; i < dp.length; i++) {
            for(int j = 1; j < dp[i].length; j++) {

            }
        }
        return 0;
    }

    public static ArrayList<Integer> change(int[] coins, int amount) {
        Map<Integer, ArrayList<Integer>> memo = new HashMap<>();
        memo.put(0, new ArrayList<>());

        for(int coin : coins) {
            ArrayList<Integer> array = new ArrayList<>();
            array.add(coin);
            memo.put(coin, array);
        }

        return change(memo, coins, amount);
    }

    private static ArrayList<Integer> change(Map<Integer, ArrayList<Integer>> memo,
                                             int[] coins,
                                             int amount) {

        if(amount < 0) {
            // cannot change
            return null;
        }

        if(memo.containsKey(amount)) {
            return new ArrayList<>(memo.get(amount));
        }

        ArrayList<Integer> solution = null;
        ArrayList<Integer> optimizeSolution = null;
        int changeCoin = 0;

        for(int coin : coins) {
            solution = change(memo, coins, amount - coin);

            if(solution == null) {
                // no solution found
                continue;
            }

            if(optimizeSolution == null) {
                optimizeSolution = solution;
                changeCoin = coin;
            } else if(optimizeSolution.size() > solution.size()) {
                optimizeSolution = solution;
                changeCoin = coin;
            }

        }

        if(optimizeSolution == null) {
            return optimizeSolution;
        }

        optimizeSolution = new ArrayList<>(optimizeSolution);
        optimizeSolution.add(changeCoin);

        return optimizeSolution;
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println("Change: " + change(coins, amount));
        System.out.println("Change: " + changeIteration(coins, amount));
    }
}
