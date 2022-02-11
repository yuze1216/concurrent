package concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author:yuze
 * @description:一个缓存示例说明读写锁的使用方式
 * 之前提到锁（如Mutex和ReentrantLock）基本都是排他锁，这些锁在同一时刻只允许一个线
 * 程进行访问，而读写锁在同一时刻可以允许多个读线程访问，但是在写线程访问时，所有的读
 * 线程和其他写线程均被阻塞。读写锁维护了一对锁，一个读锁和一个写锁，通过分离读锁和写
 * 锁，使得并发性相比一般的排他锁有了很大提升。
 * 一般情况下，读写锁的性能都会比排它锁好，因为大多数场景读是多于写的。在读多于写
 * 的情况下，读写锁能够提供比排它锁更好的并发性和吞吐量
 * @data:2022/2/11
 */
public class Cache {
    static Map<String,Object> map = new HashMap<String, Object>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();
    // 获取一个key对应的value
    public static  final Object get(String key){
        r.lock();
        try{
            return map.get(key);
        }finally {
            r.unlock();
        }
    }
    // 设置key对应的value，并返回旧的value
    public static final Object put(String key,Object value){
        w.lock();
        try {
            return map.put(key,value);
        }finally {
            w.unlock();
        }
    }
    //清空所有的内容
    public static final void clear(){
        w.lock();
        try {
            map.clear();
        }finally {
            w.unlock();
        }
    }
    /**
     *  上述示例中，Cache组合一个非线程安全的HashMap作为缓存的实现，同时使用读写锁的
     * 读锁和写锁来保证Cache是线程安全的。在读操作get(String key)方法中，需要获取读锁，这使
     * 得并发访问该方法时不会被阻塞。写操作put(String key,Object value)方法和clear()方法，在更新
     * HashMap时必须提前获取写锁，当获取写锁后，其他线程对于读锁和写锁的获取均被阻塞，而
     * 只有写锁被释放之后，其他读写操作才能继续。Cache使用读写锁提升读操作的并发性，也保
     * 证每次写操作对所有的读写操作的可见性，同时简化了编程方式
     *
     * @author yuze
     * @date 2022/2/11 16:06
     * @param
     * @return
     */
}
