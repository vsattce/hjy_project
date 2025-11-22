package com.hjy.common.core.service;

import com.hjy.common.core.domain.TreeBaseEntity;

import java.util.List;

/**
 * ITreeService接口扩展了IService接口，为树形结构数据提供服务
 * 它继承了IService中的方法，并可能添加了与树结构相关的操作
 *
 * @param <T> 泛型参数，表示树节点的类型
 *
 * @author xucy
 * @date 2023/09/07
 */
public interface ITreeBaseService<T,ID>  extends IBaseService<T> {

    List<T> list4treeByRoot(ID root);

}
