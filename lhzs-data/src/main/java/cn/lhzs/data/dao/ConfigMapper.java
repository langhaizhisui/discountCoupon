package cn.lhzs.data.dao;

import cn.lhzs.data.bean.Config;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ConfigMapper extends Mapper<Config> {
    void updateConfig(Config config);
}