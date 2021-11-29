package com.thinker.queue;

/**
 * @title QueueAbstract
 * @description 便捷调用方法
 * @time 2020/7/6 2:54 下午
 * @return
 **/
public abstract class QueueJobAbstract {
    public abstract void run(QueueJob queueJob);
    public abstract void error(QueueJob queueJob);
}
