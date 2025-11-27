<template>
  <div class="user-page">
    <!-- 搜索栏 -->
    <el-card class="search-card" shadow="never">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="用户名称">
          <el-input v-model="searchForm.userName" placeholder="请输入用户名称" clearable />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="searchForm.phonenumber" placeholder="请输入手机号码" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="用户状态" clearable style="width: 180px;">
            <el-option v-for="dict in sys_normal_disable" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue" />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker v-model="createTimeRange" type="datetimerange" range-separator="至" start-placeholder="开始时间"
            end-placeholder="结束时间" format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" style="width: 380px;"
            @change="handleCreateTimeChange" />
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

    <!-- 主体内容区域 -->
    <el-row :gutter="20">
      <!-- 左侧部门树 -->
      <el-col :span="6">
        <el-card class="dept-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>部门列表</span>
            </div>
          </template>
          <el-tree :data="deptTree" :props="{ children: 'children', label: 'deptName' }" node-key="deptId"
            :highlight-current="true" :expand-on-click-node="false" :default-expand-all="true"
            @node-click="handleDeptClick" />
        </el-card>
      </el-col>

      <!-- 右侧表格 -->
      <el-col :span="18">
        <el-card class="table-card" shadow="never">
          <el-table :data="tableData" style="width: 100%">
            <el-table-column type="selection" width="55" />
            <el-table-column prop="userId" label="用户ID" width="80" />
            <el-table-column prop="userName" label="用户名称" min-width="120" />
            <el-table-column prop="nickName" label="用户昵称" min-width="120" />
            <el-table-column prop="phonenumber" label="手机号码" width="120" />
            <el-table-column prop="sex" label="性别" width="120">
              <template #default="{ row }">
                <dict-tag :options="sys_user_sex" :value="row.sex"></dict-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-switch v-model="row.status" active-value="0" inactive-value="1" @change="handleStatusChange(row)" />
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180" />
            <el-table-column label="操作" width="180" fixed="right">
              <template #default="{ row }">
                <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDelete(row.userId)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination-wrapper">
            <el-pagination v-model:current-page="pagination.page" v-model:page-size="pagination.pageSize"
              :page-sizes="[10, 20, 50, 100]" :total="pagination.total" layout="total, sizes, prev, pager, next, jumper"
              @size-change="loadData" @current-change="loadData" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="用户名称" required>
          <el-input v-model="form.userName" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="用户昵称" required>
          <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="form.phonenumber" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio v-for="dict in sys_user_sex" :key="dict.dictValue" :label="dict.dictValue">
              {{ dict.dictLabel }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in sys_normal_disable" :key="dict.dictValue" :label="dict.dictValue">
              {{ dict.dictLabel }}
            </el-radio>
          </el-radio-group>
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserPage, addUser, updateUser, deleteUser, getUserInfoData } from '@/api/system/user'
import { getDeptTreeByRoot } from '@/api/system/dept'

const { sys_user_sex, sys_normal_disable } = useDict('sys_user_sex', 'sys_normal_disable')
// const {} = useDict('')

const searchForm = reactive({
  userName: null,
  phonenumber: null,
  status: null,
  deptId: null,
  createTimeBegin: null,
  createTimeEnd: null
})

const createTimeRange = ref([])

const deptTree = ref([])

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增用户')

const form = reactive({
  userId: null,
  userName: '',
  nickName: '',
  phonenumber: '',
  email: '',
  sex: '0',
  status: '0'
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
    const response = await getUserPage(params)
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
  Object.assign(searchForm, { userName: '', phonenumber: '', status: '', deptId: null, createTimeBegin: null, createTimeEnd: null })
  createTimeRange.value = []
  handleSearch()
}

const handleCreateTimeChange = (value) => {
  if (value && value.length === 2) {
    searchForm.beginCreateTime = value[0]
    searchForm.endCreateTime = value[1]
  } else {
    searchForm.beginCreateTime = null
    searchForm.endCreateTime = null
  }
}

const handleDeptClick = (data) => {
  searchForm.deptId = data.deptId
  handleSearch()
}

const loadDeptTree = async () => {
  try {
    const response = await getDeptTreeByRoot(0)
    if (response.code === 200 && response.data) {
      deptTree.value = response.data
    }
  } catch (error) {
    ElMessage.error('加载部门数据失败: ' + (error.message || '未知错误'))
  }
}

const handleAdd = () => {
  dialogTitle.value = '新增用户'
  getUserInfoData().then(res => {
    console.log(res)
    Object.assign(form, { userId: null, userName: '', nickName: '', phonenumber: '', email: '', sex: '0', status: '0' })
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑用户'
  getUserInfoData(row.userId).then(res => {
    console.log(res)
    Object.assign(form, row)
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    if (form.userId) {
      await updateUser(form)
      ElMessage.success('修改成功')
    } else {
      await addUser(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('操作失败: ' + (error.message || '未知错误'))
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除吗？', '提示', { type: 'warning' })
    await deleteUser(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败: ' + (error.message || '未知错误'))
  }
}

const handleStatusChange = async (row) => {
  try {
    const statusDict = sys_normal_disable.value.find(dict => dict.value === row.status)
    const statusText = statusDict ? statusDict.label : (row.status === '0' ? '启用' : '停用')
    await ElMessageBox.confirm(`确定要${statusText}该用户吗？`, '提示', { type: 'warning' })

    await updateUser({
      userId: row.userId,
      status: row.status
    })

    ElMessage.success(`${statusText}成功`)
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      // 如果操作失败，恢复原状态
      row.status = row.status === '0' ? '1' : '0'
      ElMessage.error('操作失败: ' + (error.message || '未知错误'))
    } else {
      // 用户取消操作，恢复原状态
      row.status = row.status === '0' ? '1' : '0'
    }
  }
}

onMounted(() => {
  loadDeptTree()
  loadData()
})
</script>

<style lang="less" scoped>
.user-page {
  .search-card {
    margin-bottom: 20px;
  }

  .dept-card {
    .card-header {
      font-weight: 600;
    }
  }

  .pagination-wrapper {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
}
</style>
