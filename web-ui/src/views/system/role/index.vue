<template>
  <div class="role-page">
    <!-- 搜索栏 -->
    <el-card class="search-card" shadow="never">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="角色名称">
          <el-input v-model="searchForm.roleName" placeholder="请输入角色名称" clearable />
        </el-form-item>
        <el-form-item label="权限字符">
          <el-input v-model="searchForm.roleKey" placeholder="请输入权限字符" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="角色状态" clearable style="width: 180px;">
            <el-option v-for="dict in sys_normal_disable" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon>
              <Search />
            </el-icon>
            <span>搜索</span>
          </el-button>
          <el-button @click="handleReset">
            <el-icon>
              <Refresh />
            </el-icon>
            <span>重置</span>
          </el-button>
          <el-button type="success" @click="handleAdd">
            <el-icon>
              <Plus />
            </el-icon>
            <span>新增</span>
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格 -->
    <el-card class="table-card" shadow="never">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="roleId" label="角色ID" width="80" />
        <el-table-column prop="roleName" label="角色名称" min-width="150" />
        <el-table-column prop="roleKey" label="权限字符" min-width="150" />
        <el-table-column prop="roleSort" label="显示顺序" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-switch v-model="row.status" active-value="0" inactive-value="1" @change="handleStatusChange(row)" />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="primary" text @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" text @click="handleDelete(row.roleId)">删除</el-button>
            <el-dropdown @command="(command) => handleCommand(command, row)">
              <el-button size="small" text>
                更多
                <el-icon class="el-icon--right">
                  <ArrowDown />
                </el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <!-- <el-dropdown-item command="resetPassword">重置密码</el-dropdown-item> -->
                  <el-dropdown-item command="handleAuthUser">分配用户</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination v-model:current-page="pagination.page" v-model:page-size="pagination.pageSize"
          :page-sizes="PAGE_SIZES" :total="pagination.total" :layout="PAGINATION_LAYOUT" @size-change="loadData"
          @current-change="loadData" />
      </div>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="角色名称" required>
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="权限字符" required>
          <el-input v-model="form.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="显示顺序" required>
          <el-input-number v-model="form.roleSort" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单权限">
          <div class="menu-tree-wrapper">
            <div class="tree-actions">
              <el-checkbox v-model="expandAll" @change="handleExpandChange">展开/折叠</el-checkbox>
              <el-checkbox v-model="checkAll" @change="handleCheckAllChange">全选/全不选</el-checkbox>
              <el-checkbox v-model="checkStrictly">父子联动</el-checkbox>
            </div>
            <el-tree ref="menuTreeRef" :data="menuTreeData" :props="{ label: 'menuName', children: 'children' }"
              :check-strictly="!checkStrictly" node-key="menuId" show-checkbox />
          </div>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { getMenuTreeByRoot } from '@/api/system/menu'
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRolePage, addRole, updateRole, deleteRole, getRoleInfo } from '@/api/system/role'
import { PAGE_SIZES, PAGINATION_LAYOUT } from '@/config/pagination'
import router from '@/router'

const { sys_normal_disable } = useDict('sys_normal_disable')

const searchForm = reactive({
  roleName: '',
  roleKey: '',
  status: ''
})

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增角色')
const menuTreeData = ref([])
const menuTreeRef = ref(null)
const expandAll = ref(false)
const checkAll = ref(false)
const checkStrictly = ref(true)

const form = reactive({
  roleId: null,
  roleName: '',
  roleKey: '',
  roleSort: 0,
  status: '0',
  remark: '',
  menuIds: []
})

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const loadData = async () => {
  try {
    const params = {
      current: pagination.page,
      size: pagination.pageSize,
      ...searchForm
    }
    const response = await getRolePage(params)
    if (response.code === 200 && response.data) {
      tableData.value = response.data.records || []
      pagination.total = response.data.total || 0
    }
  } catch (error) {
    ElMessage.error('加载数据失败: ' + (error.message || '未知错误'))
  }
}

const handleSearch = () => {
  pagination.page = 1
  loadData()
}

const handleReset = () => {
  Object.assign(searchForm, { roleName: '', roleKey: '', status: '' })
  handleSearch()
}

const loadMenuTree = async () => {
  try {
    const response = await getMenuTreeByRoot(0)
    if (response.code === 200) {
      menuTreeData.value = response.data || []
    }
  } catch (error) {
    console.error('加载菜单树失败', error)
  }
}

const handleExpandChange = (val) => {
  if (!menuTreeRef.value) return
  const nodes = menuTreeRef.value.store._getAllNodes()
  nodes.forEach(node => {
    node.expanded = val
  })
}

const handleCheckAllChange = (val) => {
  if (!menuTreeRef.value) return
  if (val) {
    const allKeys = getAllMenuIds(menuTreeData.value)
    menuTreeRef.value.setCheckedKeys(allKeys)
  } else {
    menuTreeRef.value.setCheckedKeys([])
  }
}

const getAllMenuIds = (menus) => {
  let ids = []
  menus.forEach(menu => {
    ids.push(menu.menuId)
    if (menu.children && menu.children.length > 0) {
      ids = ids.concat(getAllMenuIds(menu.children))
    }
  })
  return ids
}

const handleAdd = () => {
  dialogTitle.value = '新增角色'
  Object.assign(form, { roleId: null, roleName: '', roleKey: '', roleSort: 0, status: '0', remark: '', menuIds: [] })
  loadMenuTree()
  dialogVisible.value = true
  setTimeout(() => {
    if (menuTreeRef.value) {
      menuTreeRef.value.setCheckedKeys([])
    }
  }, 100)
}

const handleEdit = async (row) => {
  dialogTitle.value = '编辑角色'
  try {
    // 从后端获取完整的角色信息（包含 menuIds）
    const response = await getRoleInfo(row.roleId)
    if (response.code === 200 && response.data) {
      Object.assign(form, response.data)
      loadMenuTree()
      dialogVisible.value = true
      setTimeout(() => {
        if (menuTreeRef.value && response.data.menuIds) {
          menuTreeRef.value.setCheckedKeys(response.data.menuIds)
        }
      }, 100)
    }
  } catch (error) {
    ElMessage.error('获取角色信息失败: ' + (error.message || '未知错误'))
  }
}

const handleSubmit = async () => {
  try {
    // 获取选中的菜单ID（只获取完全选中的节点，不包括半选中的父节点）
    if (menuTreeRef.value) {
      const checkedKeys = menuTreeRef.value.getCheckedKeys()
      form.menuIds = checkedKeys
    }

    if (form.roleId) {
      await updateRole(form)
      ElMessage.success('修改成功')
    } else {
      await addRole(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('操作失败: ' + (error.message || '未知错误'))
  }
}

const handleStatusChange = async (row) => {
  try {
    await updateRole({ roleId: row.roleId, status: row.status })
    ElMessage.success('状态修改成功')
    loadData()
  } catch (error) {
    ElMessage.error('状态修改失败: ' + (error.message || '未知错误'))
    loadData()
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除吗？', '提示', { type: 'warning' })
    await deleteRole(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败: ' + (error.message || '未知错误'))
  }
}

const handleCommand = (command, row) => {
  if (command === 'handleAuthUser') {
    // 分配用户逻辑
    router.push({ path: `/system/role-auth/user/${row.roleId}` });
  }
}

onMounted(() => {
  loadData()
})
</script>

<style lang="less" scoped>
.role-page {
  .search-card {
    margin-bottom: 20px;
  }

  .pagination-wrapper {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
}

.menu-tree-wrapper {
  width: 100%;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 10px;
  max-height: 300px;
  overflow-y: auto;

  .tree-actions {
    display: flex;
    gap: 15px;
    margin-bottom: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #ebeef5;
  }
}
</style>
