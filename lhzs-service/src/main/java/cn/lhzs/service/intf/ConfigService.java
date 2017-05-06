package cn.lhzs.service.intf;

import cn.lhzs.data.bean.Config;
import cn.lhzs.data.bean.Product;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ZHX on 2017/5/7.
 */
@Service
@Transactional(readOnly = true)
public interface ConfigService {

    Config getConfigByConfId(String confId);
}
