package cn.lhzs.common.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author sonic.liu
 */
public class ObjectMappingCustomer extends ObjectMapper {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectMappingCustomer(){
        super();
        // 字段和值都加引号
//        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 数字也加引号
//        this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
//        this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        //日期格式
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
        {
            @Override
            public void serialize(
                    Object value,
                    JsonGenerator jg,
                    SerializerProvider sp) throws IOException, JsonProcessingException
            {
                if(value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof BigDecimal){
                    jg.writeNumber(0);
                }else if(value instanceof Boolean){
                    jg.writeBoolean(false);
                }else{
                    jg.writeString("");
                }
            }
        });
    }
}
