package io.spring.common;

/**
 * @author xiaokexiang
 * @since 2021/8/25
 * For循环break or continue
 */
public class LoopRetry {

    public static void main(String[] args) {
        breakRetry();
    }

    private static void continueRetry() {
        retry:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("j: " + j);
                if (j == 3) {
                    continue retry;
                }
            }
        }
        // 最终出现3次 0,1,2,3
        System.out.println("ok");
    }

    private static void breakRetry() {
        retry:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("j: " + j);
                if (j == 3) {
                    break retry;
                }
            }
        }
        // 最终出现1次 0,1,2,3
        System.out.println("ok");
    }
}
