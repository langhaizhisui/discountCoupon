package cn.lhzs.common.support.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Description: 系统推送消息线程池--主要用于短信和APP消息推送 --spring容器启动初始化线程池，关闭时销毁线程池
 * @author sonic.liu
 */
@Component("pushMessagePool")
public class PushMessagePool implements InitializingBean, DisposableBean {

	private static Log log = LogFactory.getLog(PushMessagePool.class);

	// 线程池默认值
	private int threadSize = 20;

	// 线程池
	ExecutorService threadPool;

	public void addTask(Runnable run) {
		threadPool.execute(run);
	}

	public <T> Future<T> addTask(Callable<T> run) {
		return threadPool.submit(run);
	}

	@Override
	public void destroy() throws Exception {
		log.info("------PushMessagePool destroy........");
		// 销毁
		if (threadPool != null) {
			threadPool.shutdown();
			try {
				// Wait a while for existing tasks to terminate
				if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
					threadPool.shutdownNow(); // Cancel currently executing
					// Wait a while for tasks to respond to being cancelled
					if (!threadPool.awaitTermination(60, TimeUnit.SECONDS))
						log.error("处理消息线程池没有正常停止............");
				}
			} catch (InterruptedException ie) {
				// (Re-)Cancel if current thread also interrupted
				threadPool.shutdownNow();
				// Preserve interrupt status
				Thread.currentThread().interrupt();
			}
			log.info("处理消息线程池正常停止....");
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("------PushMessagePool init........");
		threadPool = Executors.newFixedThreadPool(threadSize);
	}

	public int getThreadSize() {
		return threadSize;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;

	}

}
