<template>
    <div v-if="!item.hidden">
        <template
            v-if="hasOneShowingChild(item.children, item) && (!onlyOneChild.children || onlyOneChild.noShowingChildren) && !item.alwaysShow">
            <el-menu-item :index="resolvePath(onlyOneChild.path)">
                <el-icon v-if="onlyOneChild.meta?.icon">
                    <component :is="onlyOneChild.meta.icon" />
                </el-icon>
                <template #title>
                    <span>{{ onlyOneChild.meta.title }}</span>
                </template>
            </el-menu-item>
        </template>

        <el-sub-menu v-else :index="resolvePath(item.path)" popper-append-to-body>
            <template #title>
                <el-icon v-if="item.meta?.icon">
                    <component :is="item.meta.icon" />
                </el-icon>
                <span>{{ item.meta.title }}</span>
            </template>
            <sidebar-item v-for="child in item.children" :key="child.path" :is-nest="true" :item="child"
                :base-path="resolvePath(child.path)" />
        </el-sub-menu>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
    item: { type: Object, required: true },
    basePath: { type: String, default: '' }
})

const onlyOneChild = ref(null)

const hasOneShowingChild = (children = [], parent) => {
    const showingChildren = children.filter(item => !item.hidden)
    if (showingChildren.length === 1) {
        onlyOneChild.value = showingChildren[0]
        return true
    }
    if (showingChildren.length === 0) {
        onlyOneChild.value = { ...parent, path: '', noShowingChildren: true }
        return true
    }
    return false
}

const resolvePath = (routePath) => {
    if (/^(https?:|mailto:|tel:)/.test(routePath)) return routePath
    const base = props.basePath === '/' ? '' : props.basePath
    const path = routePath ? '/' + routePath : ''
    return (base + path).replace(/\/+/g, '/')
}
</script>

<style scoped lang="less"></style>