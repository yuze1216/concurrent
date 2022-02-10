package concurrent;

/**
 * @author:yuze
 * @description:
 * @data:2022/2/9
 */
public class UnsafeLazyInitialization {
    private static UnsafeLazyInitialization instance;
    private int i ;
    public UnsafeLazyInitialization() {
        System.out.println("init" + i++);
    }

    public static UnsafeLazyInitialization getInstance(){
        if(instance ==null) {
            instance = new UnsafeLazyInitialization();
        }
        return instance;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[1000];
        for (int i = 0; i <1000 ; i++) {
            threads[i] = new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    UnsafeLazyInitialization.getInstance();
                }
            });
            for (Thread t:threads) {
                t.start();
            }
        }
    }
}
