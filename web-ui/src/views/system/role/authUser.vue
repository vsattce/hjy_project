<template>
  <div class="auth-user-page">
    <!-- 搜索栏 -->
    <el-card class="search-card" shadow="never">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="用户名称">
          <el-input v-model="searchForm.userName" placeholder="请输入用户名称" clearable />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="searchForm.phonenumber" placeholder="请输入手机号码" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            <span>搜索</span>
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            <span>重置</span>
          </el-button>
          <el-button type="success" @click="handleAddUser">
            <el-icon><Plus /></el-icon>
            <span>添加用户</span>
          </el-button>
          <el-button type="danger" @click="handleBatchRemove">
            <el-icon><Delete /></el-icon>
            <span>批量取消授权</span>
          </el-button>
          <el-button @click="handleClose">
            <el-icon><Close /></el-icon>
            <span>关闭</span>
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格 -->
    <el-card class="table-card" shadow="never">
      <el-table :data="tableData" @selection-change="handleSelectionChange" style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="userId" label="用户编号" width="100" />
        <el-table-column prop="userName" label="用户名称" min-width="120" />
        <el-table-column prop="nickName" label="用户昵称" min-width="120" />
        <el-table-column prop="email" label="邮箱" min-width="150" />
        <el-table-column prop="phonenumber" label="手机号码" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <dict-tag :options="sys_normal_disable" :value="row.status" />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="danger" text @click="handleRemove(row)">取消授权</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination 
          v-model:current-page="pagination.page" 
          v-model:page-size="pagination.pageSize"
          :page-sizes="PAGE_SIZES" 
          :total="pagination.total" 
          :layout="PAGINATION_LAYOUT"
          @size-change="loadData" 
          @current-change="loadData" 
        />
      </div>
    </el-card>

    <!-- 选择用户对话框 -->
    <el-dialog v-model="selectUserVisible" title="选择用户" width="1000px">
      <el-form :inline="true" :model="selectUserForm">
        <el-form-item label="用户名称">
          <el-input v-model="selectUserForm.userName" placeholder="请输入用户名称" clearable />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="selectUserForm.phonenumber" placeholder="请输入手机号码" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSelectUserSearch">
            <el-icon><Search /></el-icon>
            <span>搜索</span>
          </el-button>
          <el-button @click="handleSelectUserReset">
            <el-icon><Refresh /></el-icon>
            <span>重置</span>
          </el-button>
        </el-form-item>
      </el-form>

      <el-table :data="selectUserTableData" @selection-change="handleSelectUserChange" style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="userId" label="用户编号" width="100" />
        <el-table-column prop="userName" label="用户名称" min-width="120" />
        <el-table-column prop="nickName" label="用户昵称" min-width="120" />
        <el-table-column prop="email" label="邮箱" min-width="150" />
        <el-table-column prop="phonenumber" label="手机号码" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <dict-tag :options="sys_normal_disable" :value="row.status" />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination 
          v-model:current-page="selectUserPagination.page" 
          v-model:page-size="selectUserPagination.pageSize"
          :page-sizes="PAGE_SIZES" 
          :total="selectUserPagination.total" 
          :layout="PAGINATION_LAYOUT"
          @size-change="loadUnallocatedUsers" 
          @current-change="loadUnallocatedUsers" 
        />
      </div>

      <template #footer>
        <el-button @click="selectUserVisible = false">取消</el-button>
        <el-button type="primary" @click="handleConfirmSelectUser">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserAllocatedList, getUserUnallocatedList, cancelAuthUser, cancelAuthUserAll, selectAuthUserAll } from '@/api/system/role.js'
import { PAGE_SIZES, PAGINATION_LAYOUT } from '@/config/pagination'

const route = useRoute()
const router = useRouter()

const { sys_normal_disable } = useDict('sys_normal_disable')

const roleId = ref(route.params.roleId)
const selectedUsers = ref([])

const searchForm = reactive({
  userName: '',
  phonenumber: ''
})

const tableData = ref([])

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 选择用户对话框相关
const selectUserVisible = ref(false)
const selectUserForm = reactive({
  userName: '',
  phonenumber: ''
})
const selectUserTableData = ref([])
const selectedUnallocatedUsers = ref([])
const selectUserPagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const loadData = async () => {
  try {
    const params = {
      current: pagination.page,
      size: pagination.pageSize,
      userName: searchForm.userName,
      phonenumber: searchForm.phonenumber,
      roleId: roleId.value
    }
    const response = await getUserAllocatedList(params)
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
  Object.assign(searchForm, { userName: '', phonenumber: '' })
  handleSearch()
}

const handleSelectionChange = (selection) => {
  selectedUsers.value = selection
}

const handleAddUser = () => {
  selectUserVisible.value = true
  selectUserPagination.page = 1
  loadUnallocatedUsers()
}

const loadUnallocatedUsers = async () => {
  try {
    const params = {
      current: selectUserPagination.page,
      size: selectUserPagination.pageSize,
      userName: selectUserForm.userName,
      phonenumber: selectUserForm.phonenumber,
      roleId: roleId.value
    }
    const response = await getUserUnallocatedList(params)
    if (response.code === 200 && response.data) {
      selectUserTableData.value = response.data.records || []
      selectUserPagination.total = response.data.total || 0
    }
  } catch (error) {
    ElMessage.error('加载未分配用户失败: ' + (error.message || '未知错误'))
  }
}

const handleSelectUserSearch = () => {
  selectUserPagination.page = 1
  loadUnallocatedUsers()
}

const handleSelectUserReset = () => {
  Object.assign(selectUserForm, { userName: '', phonenumber: '' })
  handleSelectUserSearch()
}

const handleSelectUserChange = (selection) => {
  selectedUnallocatedUsers.value = selection
}

const handleConfirmSelectUser = async () => {
  if (selectedUnallocatedUsers.value.length === 0) {
    ElMessage.warning('请选择要添加的用户')
    return
  }
  
  try {
    const userIds = selectedUnallocatedUsers.value.map(user => user.userId)
    await selectAuthUserAll(roleId.value, userIds)
    ElMessage.success('添加用户成功')
    selectUserVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('添加用户失败: ' + (error.message || '未知错误'))
  }
}

const handleRemove = async (row) => {
  try {
    await ElMessageBox.confirm(`确定要取消用户"${row.userName}"的授权吗？`, '提示', { type: 'warning' })
    await cancelAuthUser({ roleId: roleId.value, userId: row.userId })
    ElMessage.success('取消授权成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消授权失败: ' + (error.message || '未知错误'))
    }
  }
}

const handleBatchRemove = async () => {
  if (selectedUsers.value.length === 0) {
    ElMessage.warning('请选择要取消授权的用户')
    return
  }
  
  try {
    await ElMessageBox.confirm(`确定要取消选中的 ${selectedUsers.value.length} 个用户的授权吗？`, '提示', { type: 'warning' })
    const userIds = selectedUsers.value.map(user => user.userId)
    await cancelAuthUserAll(roleId.value, userIds)
    ElMessage.success('批量取消授权成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量取消授权失败: ' + (error.message || '未知错误'))
    }
  }
}

const handleClose = () => {
  router.back()
}

onMounted(() => {
  loadData()
})
</script>

<style lang="less" scoped>
.auth-user-page {
  .search-card {
    margin-bottom: 20px;
  }

  .pagination-wrapper {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
}
</style>