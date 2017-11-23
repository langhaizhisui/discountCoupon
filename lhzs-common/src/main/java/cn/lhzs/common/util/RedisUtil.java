//package cn.lhzs.common.util;
//
//import cn.lhzs.common.config.SpringContentRegister;
//import org.springframework.context.ApplicationContext;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.support.atomic.RedisAtomicLong;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
///**
// * Redis缓存辅助类
// * @author sonic.liu
// */
//public final class RedisUtil {
//
//	private RedisUtil() {
//	}
//
//	private static RedisTemplate<Serializable, Serializable> redisTemplate = null;
//
//	/**  默认设置过期时间 30分钟*/
//	private static Integer EXPIRE = 30*60;
//
//	// 获取连接
//	@SuppressWarnings("unchecked")
//	private static RedisTemplate<Serializable, Serializable> getRedis() {
//		if (redisTemplate == null) {
//			synchronized (RedisUtil.class) {
//				if (redisTemplate == null) {
//					ApplicationContext wac = SpringContentRegister.getApplicationContext();
//					redisTemplate = (RedisTemplate<Serializable, Serializable>) wac.getBean("redisTemplate");
//				}
//			}
//		}
//		return redisTemplate;
//	}
//
//	public static final Serializable get(final String key) {
//		return getRedis().opsForValue().get(key);
//	}
//
//	public static final void set(final String key, final Serializable value) {
//		getRedis().boundValueOps(key).set(value);
//		expire(key, EXPIRE);
//	}
//
//	public static final void set(final String key, final Serializable value, final Integer expireTime) {
//		getRedis().boundValueOps(key).set(value);
//		if(expireTime > 0){
//			expire(key, expireTime);
//		}
//	}
//
//	public static final Boolean exists(final String key) {
//		return getRedis().hasKey(key);
//	}
//
//	public static final void del(final String key) {
//		getRedis().delete(key);
//	}
//
//	public static final void delAll(final String pattern) {
//		getRedis().delete(getRedis().keys(pattern));
//	}
//
//	public static final String type(final String key) {
//		expire(key, EXPIRE);
//		return getRedis().type(key).getClass().getName();
//	}
//
//	/**
//	 * 在某段时间后失效
//	 *
//	 * @return
//	 */
//	public static final Boolean expire(final String key, final int seconds) {
//		return getRedis().expire(key, seconds, TimeUnit.SECONDS);
//	}
//
//	/**
//	 * 在某个时间点失效
//	 *
//	 * @param key
//	 * @param unixTime
//	 * @return
//	 */
//	public static final Boolean expireAt(final String key, final long unixTime) {
//		return getRedis().expireAt(key, new Date(unixTime));
//	}
//
//	public static final Long ttl(final String key) {
//		return getRedis().getExpire(key, TimeUnit.SECONDS);
//	}
//
//	public static final void setrange(final String key, final long offset, final String value) {
//		expire(key, EXPIRE);
//		getRedis().boundValueOps(key).set(value, offset);
//	}
//
//	public static final String getrange(final String key, final long startOffset, final long endOffset) {
//		expire(key, EXPIRE);
//		return getRedis().boundValueOps(key).get(startOffset, endOffset);
//	}
//
//	public static final Serializable getSet(final String key, final String value) {
//		expire(key, EXPIRE);
//		return getRedis().boundValueOps(key).getAndSet(value);
//	}
//
//	public static final Long incrByKey(String key) {
//		RedisAtomicLong counter = new RedisAtomicLong(key, getRedis().getConnectionFactory());
//		return counter.incrementAndGet();
//	}
//
//	public static final Long decrByKey(String key) {
//		RedisAtomicLong counter = new RedisAtomicLong(key, getRedis().getConnectionFactory());
//		return counter.decrementAndGet();
//	}
//
//	public static Set<Serializable> keys(String reg){
//		return getRedis().keys(reg);
//	}
//	// 未完，待续...
//}
