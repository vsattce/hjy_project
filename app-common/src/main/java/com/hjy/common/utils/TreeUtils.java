package com.hjy.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeUtils {

    /**
     * 通用树构建方法 (O(N) 高效版)
     *
     * @param list           数据库查出来的扁平列表
     * @param idExtractor    告诉我去哪拿ID (例如: SysDept::getDeptId)
     * @param pidExtractor   告诉我去哪拿父ID (例如: SysDept::getParentId)
     * @param childrenSetter 告诉我怎么存子节点 (例如: SysDept::setChildren)
     * @param rootPid        根节点的父ID是多少 (例如: 0L 或 null)
     * @param <T>            实体类型
     * @param <ID>           ID类型 (Long/String等)
     * @return 树形结构列表
     */
    public static <T, ID> List<T> buildTree(List<T> list,
                                            Function<T, ID> idExtractor,
                                            Function<T, ID> pidExtractor,
                                            BiConsumer<T, List<T>> childrenSetter,
                                            ID rootPid) {
        // 1. 安全检查：如果列表是空的，直接返回空列表，别报错
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. 核心逻辑：一次性按 PID 分组
        // Key = 父ID, Value = 这个父ID下的所有子节点列表
        Map<ID, List<T>> childrenMap = list.stream()
                .collect(Collectors.groupingBy(pidExtractor));

        List<T> roots = new ArrayList<>();

        // 3. 再次遍历，组装数据
        for (T node : list) {
            // 获取当前节点的 ID 和 PID
            ID id = idExtractor.apply(node);
            ID pid = pidExtractor.apply(node);

            // A. 找孩子：去 Map 里拿属于自己的孩子列表
            List<T> children = childrenMap.get(id);
            if (children != null && !children.isEmpty()) {
                childrenSetter.accept(node, children);
            }

            // B. 找组织：判断自己是不是根节点
            // 使用 Objects.equals 防止空指针，且兼容 rootPid 为 null 的情况
            if (Objects.equals(pid, rootPid)) {
                roots.add(node);
            }
        }

        return roots;
    }
}