package cn.lhzs.base;

import cn.lhzs.data.base.BaseModel;
import cn.lhzs.data.base.Mapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import tk.mybatis.mapper.entity.Condition;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 * 业务逻辑层基类<br/>
 * 继承基类后必须配置CacheConfig(cacheNames="")
 *
 * @author sonic.liu
 */
public abstract class AbstractBaseService<T extends BaseModel> implements IBaseService<T> {

    private static final String SEQ_PRE = "SEQ:";
    public static final Long INIT_ID = 10000L;

    @Autowired
    protected Mapper<T> mapper;

//    @Autowired
//    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    @SuppressWarnings("unchecked")
    public AbstractBaseService() {
//        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
//        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public T save(T model) {
        return null;
//        Assert.notNull(model, "MODEL");
//        model.setCreatedBy(WebUtil.getCurrentUser());
//        model.setUpdatedBy(WebUtil.getCurrentUser());
//        return update(model);
    }

    public void save(List<T> models) {
//        Assert.notNull(models, "MODEL");
//        models.forEach(this::update);
//        mapper.insertList(models);
    }

    @Override
    public void deleteById(Long id) {
//        Assert.notNull(id, "ID");
//        mapper.deleteByPrimaryKey(id);
//        redisTemplate.delete(getCacheKey(id));
    }

    @Override
    public void deleteByIds(String ids) {
//        Assert.notNull(ids, "ID");
//        String[] idsArray = ids.split(",");
//        for (String id : idsArray) {
//            redisTemplate.delete(getCacheKey(id));
//        }
//        mapper.deleteByIds(ids);
    }

    @Override
    public T update(T model) {
//        Assert.notNull(model, "ID");
//        try {
//            model.setUpdateTime(new Date());
//            Long currentUser = WebUtil.getCurrentUser();
//            if (currentUser != null) model.setUpdatedBy(currentUser);
//            if (model.getId() == null) {
//                Long generateId = getMaxId() + 1;
//                redisSet(getSeqCacheKey(), generateId);
//                model.setId(generateId);
//                if (model.getCreateTime() == null) {
//                    model.setCreateTime(new Date());
//                }
//                if (model.getInUse() == null) model.setInUse(true);
//                if (currentUser != null) model.setCreatedBy(currentUser);
//                mapper.insert(model);
//            } else {
//                mapper.updateByPrimaryKey(model);
//            }
//            redisSet(getCacheKey(model.getId()), model);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
//        return model;
        return null;
    }

    @Override
    public void enable(Long id, boolean inUse) {
//        Assert.notNull(id, "ID");
//        Assert.notNull(inUse, "inUse");
//        try {
//            T model = findById(id);
//            model.setInUse(inUse);
//            model.setUpdateTime(new Date());
//            model.setUpdatedBy(WebUtil.getCurrentUser());
//            mapper.updateByPrimaryKey(model);
//            redisSet(getCacheKey(id), model);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(Long id) {
//        Assert.notNull(id, "ID");
//        try {
//            String key = getCacheKey(id);
//            T model = (T) redisGet(key);
//            if (model == null) {
//                model = mapper.selectByPrimaryKey(id);
//                redisSet(key, model);
//            }
//            return model;
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
        return null;
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
//        try {
//            T model = modelClass.newInstance();
//            Field field = modelClass.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            field.set(model, value);
//            return mapper.selectOne(model);
//        } catch (ReflectiveOperationException e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }

        return null;
    }

    @Override
    public List<T> findByIds(String ids) {
//        Assert.notNull(ids, "ID");
//        return mapper.selectByIds(ids);
        return null;
    }

    @Override
    public List<T> findByCondition(Condition condition) {
//        return mapper.selectByCondition(condition);
        return null;
    }

    @Override
    public List<T> findAll() {
//        return mapper.selectAll();
        return null;
    }

    @Override
    public List<T> select(T t) {
//        return mapper.select(t);
        return null;
    }

    @Override
    public Long getMaxId() {
//        Long maxId = generate();
//        if (maxId <= 1) {
//            maxId = mapper.selectMaxId();
//            if (maxId == null || maxId == 0L) {
//                maxId = INIT_ID;
//            }
//        }
//        return maxId;
        return null;
    }

    /**
     * 获取缓存键值
     */
    private String getCacheKey(Object id) {
//        return getCacheName() + ":" + id;
        return null;
    }

    private String getCacheName() {
//        String cacheName;
//        CacheConfig cacheConfig = getClass().getAnnotation(CacheConfig.class);
//        if (cacheConfig == null || cacheConfig.cacheNames().length < 1) {
//            cacheName = getClass().getName();
//        } else {
//            cacheName = cacheConfig.cacheNames()[0];
//        }
//        return cacheName;
        return null;
    }

    private String getSeqCacheKey() {
//        return SEQ_PRE + getCacheName();
        return null;
    }


    /**
     * 使用RedisAtomicLong原子锁自增id.
     */
    protected long generate() {
//        RedisAtomicLong counter = new RedisAtomicLong(getSeqCacheKey(), redisTemplate.getConnectionFactory());
//        return counter.incrementAndGet();
        return 0;
    }

    protected void redisSet(String key, Serializable value) {
//        redisTemplate.boundValueOps(key).set(value);
////        redisTemplate.expire(key, 0, TimeUnit.SECONDS);
    }

    protected Serializable redisGet(String key) {
//        return redisTemplate.opsForValue().get(key);
        return null;
    }

}
