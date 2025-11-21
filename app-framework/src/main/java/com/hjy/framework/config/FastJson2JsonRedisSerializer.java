package com.hjy.framework.config;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import com.hjy.common.constant.Constants;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T>
{
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    static final Filter AUTO_TYPE_FILTER = JSONReader.autoTypeFilter(Constants.JSON_WHITELIST_STR);

    private Class<T> clazz;

    public FastJson2JsonRedisSerializer(Class<T> clazz)
    {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException
    {
        if (t == null)
        {
            return new byte[0];
        }
        return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException
    {
        if (bytes == null || bytes.length <= 0)
        {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        // 【修改重点】：这里不再传入 filter，而是直接传入 SupportAutoType 特性
        // 它的意思是：只要 JSON 里有 @type，且类存在，我就帮你还原，别管那么多了。
        // 同时，它也能完美识别 103L 这种带 L 后缀的 FastJson 特有格式。
        return JSON.parseObject(str, clazz, JSONReader.Feature.SupportAutoType);
//        if (bytes == null || bytes.length <= 0)
//        {
//            return null;
//        }
//        String str = new String(bytes, DEFAULT_CHARSET);
//
//        return JSON.parseObject(str, clazz, AUTO_TYPE_FILTER);
    }
}
