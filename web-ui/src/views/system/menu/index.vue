<template>
  <div class="menu-page">
    <!-- 操作栏 -->
    <el-card class="search-card" shadow="never">
      <el-form :inline="true">
        <el-form-item label="选择根节点">
          <el-tree-select
            v-model="rootId"
            :data="allMenuTree"
            :props="{ label: 'menuName', value: 'menuId' }"
            placeholder="请选择根节点菜单"
            check-strictly
            clearable
            style="width: 240px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadTreeData">
            <el-icon><Refresh /></el-icon>
            <span>刷新</span>
          </el-button>
          <el-button type="success" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            <span>新增</span>
          </el-button>
          <el-button @click="toggleExpand">
            <el-icon><component :is="isExpanded ? 'Fold' : 'Expand'" /></el-icon>
            <span>{{ isExpanded ? '折叠' : '展开' }}</span>
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 树形表格 -->
    <el-card class="table-card" shadow="never">
      <el-table 
        ref="tableRef"
        :data="tableData" 
        style="width: 100%" 
        row-key="menuId" 
        :tree-props="{ children: 'children' }"
        :default-expand-all="isExpanded"
      >
        <el-table-column prop="menuName" label="菜单名称" min-width="180" />
        <el-table-column prop="menuType" label="菜单类型" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.menuType === 'M'" type="info">目录</el-tag>
            <el-tag v-else-if="row.menuType === 'C'" type="success">菜单</el-tag>
            <el-tag v-else-if="row.menuType === 'F'" type="warning">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="icon" label="图标" width="100">
          <template #default="{ row }">
            <span v-if="row.icon && isValidIconName(row.icon)">
              <el-icon><component :is="row.icon" /></el-icon>
            </span>
            <span v-else-if="row.icon" class="icon-text">{{ row.icon }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="排序" width="80" />
        <el-table-column prop="perms" label="权限标识" min-width="150" show-overflow-tooltip />
        <el-table-column prop="path" label="路由地址" min-width="150" show-overflow-tooltip />
        <el-table-column prop="component" label="组件路径" min-width="180" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">
              {{ row.status === '0' ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="240" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="success" @click="handleAddChild(row)">新增</el-button>
            <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.menuId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
      <el-form :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <el-tree-select
                v-model="form.parentId"
                :data="allMenuTree"
                :props="{ label: 'menuName', value: 'menuId' }"
                placeholder="请选择上级菜单"
                check-strictly
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          
          <el-col :span="24">
            <el-form-item label="菜单类型" required>
              <el-radio-group v-model="form.menuType">
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
                <el-radio label="F">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <!-- 菜单图标 - 目录和菜单显示 -->
          <el-col :span="12" v-if="form.menuType !== 'F'">
            <el-form-item label="菜单图标">
              <el-input v-model="form.icon" placeholder="点击选择图标">
                <template #prefix>
                  <el-icon v-if="form.icon && isValidIconName(form.icon)">
                    <component :is="form.icon" />
                  </el-icon>
                  <el-icon v-else><Search /></el-icon>
                </template>
              </el-input>
            </el-form-item>
          </el-col>

          <!-- 显示排序 - 所有类型都显示 -->
          <el-col :span="form.menuType === 'F' ? 24 : 12">
            <el-form-item label="显示排序" required>
              <el-input-number v-model="form.orderNum" :min="0" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>

          <!-- 菜单名称 - 所有类型都显示 -->
          <el-col :span="form.menuType === 'C' ? 12 : 24">
            <el-form-item label="菜单名称" required>
              <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
            </el-form-item>
          </el-col>

          <!-- 是否外链 - 目录和菜单显示 -->
          <el-col :span="12" v-if="form.menuType !== 'F'">
            <el-form-item label="是否外链">
              <el-radio-group v-model="form.isFrame">
                <el-radio :label="0">是</el-radio>
                <el-radio :label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <!-- 路由地址 - 目录和菜单显示 -->
          <el-col :span="12" v-if="form.menuType !== 'F'">
            <el-form-item label="路由地址" :required="form.menuType !== 'F'">
              <el-input v-model="form.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>

          <!-- 组件路径 - 仅菜单显示 -->
          <el-col :span="12" v-if="form.menuType === 'C'">
            <el-form-item label="组件路径">
              <el-input v-model="form.component" placeholder="请输入组件路径" />
            </el-form-item>
          </el-col>

          <!-- 权限字符 - 菜单和按钮显示 -->
          <el-col :span="12" v-if="form.menuType !== 'M'">
            <el-form-item label="权限字符">
              <el-input v-model="form.perms" placeholder="请输入权限标识" />
            </el-form-item>
          </el-col>

          <!-- 路由参数 - 仅菜单显示 -->
          <el-col :span="12" v-if="form.menuType === 'C'">
            <el-form-item label="路由参数">
              <el-input v-model="form.query" placeholder="请输入路由参数" />
            </el-form-item>
          </el-col>

          <!-- 是否缓存 - 仅菜单显示 -->
          <el-col :span="12" v-if="form.menuType === 'C'">
            <el-form-item label="是否缓存">
              <el-radio-group v-model="form.isCache">
                <el-radio :label="0">缓存</el-radio>
                <el-radio :label="1">不缓存</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <!-- 显示状态 - 目录和菜单显示 -->
          <el-col :span="12" v-if="form.menuType !== 'F'">
            <el-form-item label="显示状态">
              <el-radio-group v-model="form.visible">
                <el-radio label="0">显示</el-radio>
                <el-radio label="1">隐藏</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <!-- 菜单状态 - 所有类型都显示 -->
          <el-col :span="12">
            <el-form-item label="菜单状态">
              <el-radio-group v-model="form.status">
                <el-radio label="0">正常</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMenuTreeByRoot, addMenu, updateMenu, deleteMenu } from '@/api/system/menu'

const tableData = ref([])
const allMenuTree = ref([]) // 所有菜单树，用于根节点选择
const dialogVisible = ref(false)
const dialogTitle = ref('新增菜单')
const rootId = ref(null)
const tableRef = ref(null)
const isExpanded = ref(false)

const form = reactive({
  menuId: null,
  parentId: 0,
  menuName: '',
  menuType: 'M',
  orderNum: 0,
  path: '',
  component: '',
  perms: '',
  query: '',
  icon: '',
  isFrame: 1,
  isCache: 0,
  visible: '0',
  status: '0'
})

// 加载所有菜单树（用于根节点选择）
const loadAllMenuTree = async () => {
  try {
    const response = await getMenuTreeByRoot(0)
    if (response.code === 200) {
        // const menu = ;
      allMenuTree.value = [{ menuId: 0, menuName: '主类目', children: response.data || [] }]
    }
  } catch (error) {
    console.error('加载菜单树失败', error)
  }
}

const loadTreeData = async () => {
  try {
    const response = await getMenuTreeByRoot(rootId.value|0)
    if (response.code === 200) {
      tableData.value = response.data || []//[{ menuId: 0, menuName: '主类目', children: response.data || [] }]
    }
  } catch (error) {
    ElMessage.error('加载数据失败: ' + (error.message || '未知错误'))
  }
}

const handleAdd = () => {
  dialogTitle.value = '新增菜单'
  Object.assign(form, { 
    menuId: null, 
    parentId: 0, 
    menuName: '', 
    menuType: 'M', 
    orderNum: 0, 
    path: '', 
    component: '',
    perms: '', 
    query: '',
    icon: '', 
    isFrame: 1,
    isCache: 0,
    visible: '0',
    status: '0' 
  })
  dialogVisible.value = true
}

const handleAddChild = (row) => {
  dialogTitle.value = '新增子菜单'
  Object.assign(form, { 
    menuId: null, 
    parentId: row.menuId, 
    menuName: '', 
    menuType: 'M', 
    orderNum: 0, 
    path: '', 
    component: '',
    perms: '', 
    query: '',
    icon: '', 
    isFrame: 1,
    isCache: 0,
    visible: '0',
    status: '0' 
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑菜单'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    if (form.menuId) {
      await updateMenu(form)
      ElMessage.success('修改成功')
    } else {
      await addMenu(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadTreeData()
  } catch (error) {
    ElMessage.error('操作失败: ' + (error.message || '未知错误'))
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除吗？', '提示', { type: 'warning' })
    await deleteMenu(id)
    ElMessage.success('删除成功')
    loadTreeData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败: ' + (error.message || '未知错误'))
    }
  }
}

// 验证图标名称是否有效
const isValidIconName = (iconName) => {
  if (!iconName || typeof iconName !== 'string') return false
  const invalidChars = /[#<>/\\]/
  return !invalidChars.test(iconName) && iconName.length > 0
}

// 切换展开/折叠
const toggleExpand = () => {
  isExpanded.value = !isExpanded.value
  if (tableRef.value && tableData.value.length > 0) {
    tableData.value.forEach(row => {
      toggleRowExpansion(row, isExpanded.value)
    })
  }
}

// 递归展开/折叠所有行
const toggleRowExpansion = (row, expanded) => {
  tableRef.value.toggleRowExpansion(row, expanded)
  if (row.children && row.children.length > 0) {
    row.children.forEach(child => {
      toggleRowExpansion(child, expanded)
    })
  }
}

onMounted(() => {
  loadAllMenuTree()
  loadTreeData()
})
</script>

<style lang="less" scoped>
.menu-page {
  .search-card {
    margin-bottom: 20px;
  }

  .icon-text {
    color: #909399;
    font-size: 12px;
  }
}
</style>
