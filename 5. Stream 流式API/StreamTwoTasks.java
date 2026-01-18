/**
 * @author @厌。(QQ:669518272)
 * @version 1.0
 */

import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.Arrays;

public class StreamTwoTasks {

    // ===================== 题 1：自然数流 =====================

    // 自然数流：1,2,3,4,...
    public static LongStream createNaturalStream() {
        return LongStream.iterate(1, n -> n + 1);
    }

    /**
     * 题 1（循环版）
     */
    public static long solve1ByLoop(int limit) {
        // TODO: 不得使用 Stream
        return 0L;
    }

    /**
     * 题 1（Stream版）
     */
    public static long solve1ByStream(int limit) {
        // TODO: 用 Stream 完成
        return 0L;
    }

    // ===================== 题 2：字符串数组处理 =====================

    /**
     * 题 2（循环版）
     */
    public static String solve2ByLoop(String[] words, int limit) {
        // TODO: 不得使用 Stream
        return "";
    }

    /**
     * 题 2（Stream版）
     */
    public static String solve2ByStream(String[] words, int limit) {
        // TODO: 用 Stream 完成
        return "";
    }

    // ===================== 简单测试（你可保留或删除） =====================
    public static void main(String[] args) {
        System.out.println("=== Q1 Loop ===");
        long s1 = solve1ByLoop(5);
        System.out.println("sum=" + s1);

        System.out.println("=== Q1 Stream ===");
        long s2 = solve1ByStream(5);
        System.out.println("sum=" + s2);

        System.out.println("=== Q2 Loop ===");
        String[] words = {"  abc ", null, "a", "  Java", "  hi  ", "stream ", "  ", "ok"};
        String r1 = solve2ByLoop(words, 5);
        System.out.println("joined=" + r1);

        System.out.println("=== Q2 Stream ===");
        String r2 = solve2ByStream(words, 5);
        System.out.println("joined=" + r2);
    }
}
