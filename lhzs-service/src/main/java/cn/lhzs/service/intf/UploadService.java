package cn.lhzs.service.intf;

import java.io.InputStream;

/**
 * Created by IBA-EDV on 2017/4/26.
 */
public interface UploadService {
    String getExcell(String fileName, InputStream inputStream, String type);
}
