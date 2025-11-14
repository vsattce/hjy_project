package com.hjy.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hjy.common.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class QueryWrapperUtils {
    private static final String LIKE = "Like";
    private static final String NOT_LIKE = "NotLike";

    //同时支持begin，end 或者gt，lt
    private static final String BEGIN = "begin";//在属性前面
    private static final String END = "end";//在属性前面

    private static final String GT = "Gt";//大于，在属性后面
    private static final String LT = "Lt";//小于，在属性后面


    private static final String ORDER = "order";
    private static final String BY = "by";
    private static final String ORDER_BY = "orderBy";

    private static final String BLANK_SPACE = " ";

    private static final String DESC = "DESC";
    //private static final String ASC = "ASC";
    private static final String COMMA = ",";


    private static final String IS_NULL = "isNull";
    private static final String IS_NOT_NULL = "isNotNull";

    private static final String IN = "In";
    private static final String NOT_IN = "NotIn";

    private static final String EQ = "Eq";
    private static final String NOT_EQ = "NotEq";

    private static final String GROUP_BY = "groupBy";

    private static final String[] filterFields = {"serialVersionUID", "searchValue","log"};

    private static final String UNDERLINE = "_";

    /**
     * entity和parameters转查询条件
     *
     * @param entity
     * @param parameters
     * @return QueryWrapper<E>
     */
    public static <E> QueryWrapper<E> entity2Wrapper(E entity, Map<String, Object> parameters) {
        return entity2Wrapper(new QueryWrapper<>(), entity, parameters);
    }
//
    /**
     * entity和parameters转查询条件
     *
     * @param wrapper
     * @param entity
     * @param parameters
     * @return QueryWrapper<E>
     */
    public static <E> QueryWrapper<E> entity2Wrapper(QueryWrapper<E> wrapper, E entity, Map<String, Object> parameters) {
        if (Objects.isNull(wrapper)) {
            wrapper = new QueryWrapper<>();
        }

        if (Objects.isNull(wrapper.getEntityClass())) {
            wrapper.setEntityClass((Class<E>) entity.getClass());
        }
        Field[] fields = ReflectUtil.getFields(wrapper.getEntityClass());
        if (ArrayUtil.isEmpty(fields)) {
            return wrapper;
        }

        //设置实体类会导致多一个表达式
        if (Objects.isNull(wrapper.getEntity())) {
            wrapper.setEntity(entity);
        }
        //叠加外界的Map
        if (CollUtil.isNotEmpty(parameters)) {
            map2Wrapper(wrapper, parameters);
        }
        return wrapper;
    }
    /**
     * map转查询条件
     *
     * @param wrapper
     * @param parameters
     */

    private static <E> void map2Wrapper(QueryWrapper<E> wrapper, Map<String, Object> parameters) {
        if (CollUtil.isEmpty(parameters)) {
            return;
        }
        if (Objects.isNull(wrapper)) {
            wrapper = new QueryWrapper<E>();
        }
        //去掉重复参数
        //parameters=ParametersUtils.filter(parameters, wrapper.getEntityClass());
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (Objects.isNull(value)) {
                continue;
            }
            //固定开头写死的，前面跟着的是属性名，否则会出错
            parseLikeOrNot(wrapper, key, value);
            //其它大于小于类型不限制
            parseBeginEnd(wrapper, key, value);
            parseGtLt(wrapper, key, value);
            //null条件
            parseNullOrNot(wrapper, key, value);
            //in条件
            parseInOrNot(wrapper, key, value);
            //eq条件
            parseEqOrNot(wrapper, key, value);
            //排序
            parseOrderBy(wrapper, key, value);
            //分组
            parseGroupBy(wrapper, key, value);
        }
    }

    /**
     * 根据字段得到数据库列名
     *
     * @param field 字段
     * @return
     */
    private static String getTableFieldName(Field field) {
        if (Objects.isNull(field)) {
            return null;
        }
        //获取属性上的注解
        TableField fieldAnnotation = field.getAnnotation(TableField.class);
        TableId idAnnotation = field.getAnnotation(TableId.class);
        //考虑注解没写的情况
        if (Objects.isNull(fieldAnnotation) && Objects.isNull(idAnnotation)) {
//            return null;
            //找不到就直接转下划线,没有注解的不应该返回和匹配,例如 静态变量
            return StringUtils.toUnderScoreCase(field.getName());
        }
        return Objects.nonNull(fieldAnnotation) ? fieldAnnotation.value() : idAnnotation.value();
    }

    /**
     * 得到数据库列名
     *
     * @param key
     * @param sqlKeywords   注意前后顺序
     * @param isKeywordLast 是否再最后面
     * @param clazz
     * @return
     */

    private static <E> String getTableFieldName(String key, String[] sqlKeywords, Boolean isKeywordLast, Class<E> clazz) {
        String fieldName = key;
        for (String sqlKeyword : sqlKeywords) {
            if (isKeywordLast) {
                fieldName = StringUtils.removeEndIgnoreCase(fieldName, sqlKeyword);
            } else {
                fieldName = StringUtils.removeStartIgnoreCase(fieldName, sqlKeyword);
            }
        }
        return getTableFieldName(fieldName, clazz);
    }
    /**
     * 获取数据库字段名称
     *
     * @param fieldName
     * @param clazz
     * @return
     */
    private static <E> String getTableFieldName(String fieldName, Class<E> clazz) {
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        //转驼峰，且首字母小写
        if(fieldName.contains(UNDERLINE)){
            fieldName=StringUtils.toCamelCase(fieldName.trim());
        }
        fieldName=StringUtils.uncapitalize(fieldName);
        //不能用糊涂工具包，否则会用别名
        // Field field = ReflectUtil.getField(clazz, StringUtils.uncapitalize(StringUtils.toCamelCase(fieldName.trim())));
        Field[] fields = ReflectUtil.getFields(clazz,e->!StringUtils.equalsAny(e.getName(), filterFields));
        String finalFieldName = fieldName;
        Field field= (Field)ArrayUtil.firstMatch((fieldTemp) -> finalFieldName.equals(fieldTemp.getName()), fields);
        if (Objects.isNull(field)) {
            return null;
        }
        return getTableFieldName(field);
    }


    /**
     * 解析模糊匹配或者非模糊匹配
     *
     * @param wrapper 加入的的对象
     * @param key     关键字
     * @param value   值
     */
    private static <E> void parseLikeOrNot(QueryWrapper<E> wrapper, String key, Object value) {
        if (StringUtils.endsWithIgnoreCase(key, LIKE)) {
            String columnName = getTableFieldName(key, new String[]{NOT_LIKE, LIKE}, true, wrapper.getEntityClass());
            if (StringUtils.isBlank(columnName)) {
                return;
            }
            //一定是string类型才能模糊查询,先判断NotLike，否则会重复
            if (StringUtils.endsWithIgnoreCase(key, NOT_LIKE)) {
                wrapper.notLike(StringUtils.isNotBlank((String) value), columnName, value);
                return;
            }
            if (StringUtils.endsWithIgnoreCase(key, LIKE)) {
                wrapper.like(StringUtils.isNotBlank((String) value), columnName, value);
            }
        }
    }

    /**
     * 解析大于小于
     *
     * @param wrapper 加入的的对象
     * @param key     关键字
     * @param value   值
     */
    private static <E> void parseBeginEnd(QueryWrapper<E> wrapper, String key, Object value) {
        if (StringUtils.startsWithIgnoreCase(key, BEGIN) || StringUtils.startsWithIgnoreCase(key, END)) {
            String columnName = getTableFieldName(key, new String[]{BEGIN, END}, false,  wrapper.getEntityClass());
            if (StringUtils.isBlank(columnName)) {
                return;
            }
            if (StringUtils.startsWithIgnoreCase(key, BEGIN)) {
                wrapper.ge(Objects.nonNull(value), columnName, value);
            }
            if (StringUtils.startsWithIgnoreCase(key, END)) {
                wrapper.le(Objects.nonNull(value), columnName, value);
            }
        }
    }


    /**
     * 解析大于小于2
     *
     * @param wrapper 加入的的对象
     * @param key     关键字
     * @param value   值
     */
    private static <E> void parseGtLt(QueryWrapper<E> wrapper, String key, Object value) {
        if (StringUtils.endsWithIgnoreCase(key, GT) || StringUtils.endsWithIgnoreCase(key, LT)) {
            String columnName = getTableFieldName(key, new String[]{GT, LT}, true,  wrapper.getEntityClass());
            if (StringUtils.isBlank(columnName)) {
                return;
            }
            //可以考虑根据类型转换数据
            if (StringUtils.endsWithIgnoreCase(key, GT)) {
                wrapper.ge(Objects.nonNull(value), columnName, value);
            }
            if (StringUtils.endsWithIgnoreCase(key, LT)) {
                wrapper.le(Objects.nonNull(value), columnName, value);
            }
        }
    }

    /**
     * 解析in和notin，传入要大写
     *
     * @param wrapper 加入的的对象
     * @param key     关键字
     * @param value   值
     * @return
     */
    private static <E> void parseInOrNot(QueryWrapper<E> wrapper, String key, Object value) {
        if (StringUtils.endsWithIgnoreCase(key, IN)) {
            String columnName = getTableFieldName(key, new String[]{NOT_IN, IN}, true, wrapper.getEntityClass());
            if (StringUtils.isBlank(columnName)) {
                return;
            }
            //例如：     student.getParams().put("studentName in", new String[]{"姓名20","姓名0"});
            Object[] entityects = (value instanceof String) ? ((String) value).split(COMMA) : (Object[]) value;
            if (StringUtils.endsWithIgnoreCase(key, NOT_IN)) {
                wrapper.notIn(Objects.nonNull(entityects), columnName, entityects);
                return;
            }
            if (StringUtils.endsWithIgnoreCase(key, IN)) {
                wrapper.in(Objects.nonNull(entityects), columnName, entityects);
            }
        }
    }

    /**
     * 解析空或者不为空
     *
     * @param wrapper 加入的的对象
     * @param key     关键字
     * @param value   值
     */
    private static <E> void parseNullOrNot(QueryWrapper<E> wrapper, String key, Object value) {
        if (key.equalsIgnoreCase(IS_NULL) || key.equalsIgnoreCase(IS_NOT_NULL)) {
            //多个字段的情况key=isnotnull,value=字段数据都逗号或者数组，例如： student.getParams().put("isnotnull", "studentName,student_id");
            String[] valueArr = (value instanceof String) ? ((String) value).split(COMMA) : (String[]) value;
            for (String columnNameTemp : valueArr) {
                String columnName = getTableFieldName(columnNameTemp, wrapper.getEntityClass());
                if (StringUtils.isBlank(columnName)) {
                    continue;
                }
                if (key.equalsIgnoreCase(IS_NULL)) {
                    wrapper.isNull(columnName);
                }
                if (key.equalsIgnoreCase(IS_NOT_NULL)) {
                    wrapper.isNotNull(columnName);
                }
            }
        }
    }

    /**
     * 解析排序
     *
     * @param wrapper 加入的的对象
     * @param key     关键字
     * @param value   值
     * @return
     */
    private static <E> void parseOrderBy(QueryWrapper<E> wrapper, String key, Object value) {
        if (Objects.isNull(value)) {
            return;
        }
        //key=orderBy,value=字段+空格+排序的形式（可以逗号隔开）或者数组，例如 insAlarmRule.getParams().put("orderBy", "alarmRank desc,其它字段 asc");
        if (StringUtils.equalsIgnoreCase(key, ORDER_BY)) {
            //  String[] valueArr = valueStr.split(COMMA);
            String[] valueArr = (value instanceof String) ? ((String) value).split(COMMA) : (String[]) value;
            for (String columnNameOrder : valueArr) {
                String[] columnNameOrderArr = columnNameOrder.split(BLANK_SPACE); //两个一组
                String columnName = getTableFieldName(columnNameOrderArr[0] , wrapper.getEntityClass());
                if (columnNameOrderArr.length >= 2 && columnNameOrderArr[1].trim().equalsIgnoreCase(DESC)) {
                    wrapper.orderByDesc(columnName);
                    continue;
                }
                wrapper.orderByAsc(columnName);
            }
            return;
        }
        //key=orderBy字段,false/true的形式，例如 insAlarmRule.getParams().put("orderBy AlarmRank", false);
        if (StringUtils.startsWithIgnoreCase(key, ORDER_BY)) {
            String columnName = getTableFieldName(key, new String[]{ORDER_BY}, false,wrapper.getEntityClass());
            if (Boolean.parseBoolean(value + "")) {
                wrapper.orderByAsc(columnName);
            } else {
                wrapper.orderByDesc(columnName);
            }
            return;
        }
        //key=order By 原生字段,false/true的形式,例如： insAlarmRule.getParams().put("order by alarm_rank", false)或者insAlarmRule.getParams().put("order by alarmRank", false);
        if (StringUtils.equalsIgnoreCase(key, ORDER) && StringUtils.equalsIgnoreCase(key, BY)) {
            String columnName = getTableFieldName(key, new String[]{ORDER, BY}, false, wrapper.getEntityClass());
            if (Boolean.parseBoolean(value + "")) {
                wrapper.orderByAsc(columnName);
            } else {
                wrapper.orderByDesc(columnName);
            }
        }
    }

    /**
     * 解析等号匹配或者非等号匹配
     *
     * @param wrapper 加入的的对象
     * @param key     关键字
     * @param value   值
     */
    private static <E> void parseEqOrNot(QueryWrapper<E> wrapper, String key, Object value) {
        if (StringUtils.endsWithIgnoreCase(key, EQ)) {
            String columnName = getTableFieldName(key, new String[]{NOT_EQ, EQ}, true, wrapper.getEntityClass());
            if (StringUtils.isBlank(columnName)) {
                return;
            }
            if (StringUtils.endsWithIgnoreCase(key, NOT_EQ)) {
                wrapper.ne(Objects.nonNull(value), columnName, value);
                return;
            }
            if (StringUtils.endsWithIgnoreCase(key, EQ)) {
                wrapper.eq(Objects.nonNull(value), columnName, value);
            }
        }
    }
    /**
     * 解析分组
     *
     * @param wrapper 加入的的对象
     * @param key     关键字
     * @param value   值
     * @return
     */
    private static <E> void parseGroupBy(QueryWrapper<E> wrapper, String key, Object value) {
        if (Objects.isNull(value)) {
            return;
        }
        //key=GROUP_BY,value=字段+可以逗号隔开 或者数组，例如 insAlarmRule.getParams().put("groupBy", "alarmRank,其它字段或者数组");
        if (StringUtils.equalsIgnoreCase(key, GROUP_BY)) {
            String[] valueArr = (value instanceof String) ? ((String) value).split(COMMA) : (String[]) value;
            wrapper.groupBy(Arrays.asList(valueArr));
        }
    }

}
