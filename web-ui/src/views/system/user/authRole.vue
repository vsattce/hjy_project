<template>
  <div class="auth-role-page">
    <el-card class="user-info-card" shadow="never">
      <template #header>
        <div class="card-header">
          <span>基本信息</span>
        </div>
      </template>
      <el-form label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户昵称">
              <el-input v-model="userInfo.nickName" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录账号">
              <el-input v-model="userInfo.userName" disabled />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <el-card class="role-card" shadow="never">
      <template #header>
        <div class="card-header">
          <span>角色信息</span>
        </div>
      </template>
      
      <el-table ref="roleTableRef" :data="roleList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="roleId" label="角色编号" width="100" />
        <el-table-column prop="roleName" label="角色名称" min-width="120" />
        <el-table-column prop="roleKey" label="权限字符" min-width="120" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.pageSize"
          :page-sizes="PAGE_SIZES"
          :total="pagination.total"
          :layout="PAGINATION_LAYOUT"
          @size-change="loadRoleList"
          @current-change="loadRoleList"
        />
      </div>

      <div class="button-wrapper">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getUserAuthRole, updateAuthRole } from '@/api/system/user'
import { getRolePage } from '@/api/system/role'
import { PAGE_SIZES, PAGINATION_LAYOUT } from '@/config/pagination'

const route = useRoute()
const router = useRouter()

const userId = ref(null)
const userInfo = reactive({
  userName: '',
  nickName: ''
})

const roleList = ref([])
const selectedRoleIds = ref([])
const roleTableRef = ref(null)
const isInitializing = ref(false) // 标志位，防止初始化时触发selection-change

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 加载用户授权角色信息
const loadUserAuthRole = async () => {
  try {
    const response = await getUserAuthRole(userId.value)
    if (response.code === 200) {
      Object.assign(userInfo, {
        userName: response.user.userName,
        nickName: response.user.nickName
      })
      selectedRoleIds.value = response.roleIds || []
    }
  } catch (error) {
    ElMessage.error('加载用户信息失败: ' + (error.message || '未知错误'))
  }
}

// 加载角色列表
const loadRoleList = async () => {
  try {
    const response = await getRolePage({
      current: pagination.page,
      size: pagination.pageSize
    })
    if (response.code === 200 && response.data) {
      roleList.value = response.data.records || []
      pagination.total = response.data.total || 0
      
      // 设置默认选中的角色
      await loadUserAuthRole()
      setTimeout(() => {
        setSelectedRoles()
      }, 100)
    }
  } catch (error) {
    ElMessage.error('加载角色列表失败: ' + (error.message || '未知错误'))
  }
}

// 设置选中的角色
const setSelectedRoles = () => {
  if (roleTableRef.value) {
    isInitializing.value = true // 开始初始化
    roleList.value.forEach(role => {
      if (selectedRoleIds.value.includes(role.roleId)) {
        roleTableRef.value.toggleRowSelection(role, true)
      }
    })
    // 延迟重置标志位，确保所有selection-change事件都被忽略
    setTimeout(() => {
      isInitializing.value = false
    }, 50)
  }
}

// 表格选择变化
const handleSelectionChange = (selection) => {
  // 如果正在初始化，不更新selectedRoleIds
  if (!isInitializing.value) {
    selectedRoleIds.value = selection.map(item => item.roleId)
  }
}



// 提交授权
const handleSubmit = async () => {
  try {
    await updateAuthRole(userId.value, selectedRoleIds.value)
    ElMessage.success('授权成功')
    router.back()
  } catch (error) {
    ElMessage.error('授权失败: ' + (error.message || '未知错误'))
  }
}

// 取消
const handleCancel = () => {
  router.back()
}

onMounted(() => {
  userId.value = route.params.userId
  if (userId.value) {
    loadRoleList()
  } else {
    ElMessage.error('缺少用户ID参数')
  }
})
</script>

<style lang="less" scoped>
.auth-role-page {
  .user-info-card {
    margin-bottom: 20px;
  }

  .card-header {
    font-weight: 600;
  }

  .pagination-wrapper {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }

  .button-wrapper {
    margin-top: 20px;
    text-align: center;
  }
}
</style>
