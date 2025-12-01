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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getUserAllocatedList } from '@/api/system/role.js'
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
  console.log('添加用户到角色:', { roleId: roleId.value })
  ElMessage.info('添加用户功能待实现')
}

const handleRemove = async (row) => {
  console.log('取消授权用户:', { roleId: roleId.value, userId: row.userId, userName: row.userName })
  ElMessage.info('取消授权功能待实现')
}

const handleBatchRemove = () => {
  if (selectedUsers.value.length === 0) {
    ElMessage.warning('请选择要取消授权的用户')
    return
  }
  
  const userIds = selectedUsers.value.map(user => user.userId)
  console.log('批量取消授权:', { roleId: roleId.value, userIds, count: selectedUsers.value.length })
  ElMessage.info('批量取消授权功能待实现')
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