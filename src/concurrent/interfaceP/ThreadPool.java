package concurrent.interfaceP;

public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);
    void shutdown();
    // 增加工作者线程
    void addWorkers(int num);
    // 减少工作者线程
    void removeWorker(int num);
    // 得到正在等待执行的任务数量
    int getJobSize();

}
