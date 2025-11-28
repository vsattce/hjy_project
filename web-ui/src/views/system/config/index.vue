<template>
  <div class="config-page">
    <!-- 搜索栏 -->
    <el-card class="search-card" shadow="never">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="配置名称">
          <el-input
            v-model="searchForm.configName"
            placeholder="请输入配置名称"
            clearable
            @keyup.enter="handleSearch"
          />
        </el-form-item>
        <el-form-item label="配置键名">
          <el-input
            v-model="searchForm.configKey"
            placeholder="请输入配置键名"
            clearable
            @keyup.enter="handleSearch"
          />
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
          <el-button type="success" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            <span>新增</span>
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格 -->
    <el-card class="table-card" shadow="never">
      <el-table
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="configId" label="配置ID" width="80" />
        <el-table-column prop="configName" label="配置名称" min-width="150" show-overflow-tooltip />
        <el-table-column prop="configKey" label="配置键名" min-width="150" show-overflow-tooltip />
        <el-table-column prop="configValue" label="配置键值" min-width="150" show-overflow-tooltip />
        <el-table-column prop="configType" label="系统内置" width="100">
          <template #default="{ row }">
            <dict-tag :options="sys_yes_no" :value="row.configType" />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button
              size="small"
              type="primary"
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(row.configId)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="paginationReactive.page"
          v-model:page-size="paginationReactive.pageSize"
          :page-sizes="PAGE_SIZES"
          :total="paginationReactive.itemCount"
          :layout="PAGINATION_LAYOUT"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>

      <!-- 批量删除按钮 -->
      <div v-if="selectedRows.length > 0" class="batch-actions">
        <el-button type="danger" @click="handleBatchDelete">
          <el-icon><Delete /></el-icon>
          <span>批量删除 ({{ selectedRows.length }})</span>
        </el-button>
      </div>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
    >
      <el-form :model="form" label-width="100px" ref="formRef">
        <el-form-item label="配置名称" required>
          <el-input v-model="form.configName" placeholder="请输入配置名称" />
        </el-form-item>
        <el-form-item label="配置键名" required>
          <el-input v-model="form.configKey" placeholder="请输入配置键名" />
        </el-form-item>
        <el-form-item label="配置键值" required>
          <el-input v-model="form.configValue" placeholder="请输入配置键值" />
        </el-form-item>
        <el-form-item label="系统内置">
          <el-radio-group v-model="form.configType">
            <el-radio label="Y">是</el-radio>
            <el-radio label="N">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="form.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入备注"
          />
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
import { getConfigPage, addConfig, updateConfig, deleteConfig } from '@/api/system/config'
import { PAGE_SIZES, PAGINATION_LAYOUT } from '@/config/pagination'
// import { useDict } from '@/hooks/useDict'

const searchForm = reactive({
  configName: '',
  configKey: ''
})

const {sys_yes_no} = useDict("sys_yes_no")

const tableData = ref([])
const selectedRows = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增配置')
const formRef = ref(null)

const form = reactive({
  configId: null,
  configName: '',
  configKey: '',
  configValue: '',
  configType: 'N',
  remark: ''
})

const paginationReactive = reactive({
  page: 1,
  pageSize: 10,
  itemCount: 0
})

const loadData = async () => {
  console.log(sys_yes_no.value)
  try {
    const params = {
      current: paginationReactive.page,
      size: paginationReactive.pageSize,
      ...searchForm
    }
    const response = await getConfigPage(params)
    if (response.code === 200 && response.data) {
      tableData.value = response.data.records || []
      paginationReactive.itemCount = response.data.total || 0
    }
  } catch (error) {
    ElMessage.error('加载数据失败: ' + (error.message || '未知错误'))
  }
}

const handleSearch = () => {
  paginationReactive.page = 1
  loadData()
}

const handleReset = () => {
  searchForm.configName = ''
  searchForm.configKey = ''
  paginationReactive.page = 1
  loadData()
}

const handleAdd = () => {
  dialogTitle.value = '新增配置'
  Object.assign(form, {
    configId: null,
    configName: '',
    configKey: '',
    configValue: '',
    configType: 'N',
    remark: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑配置'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!form.configName || !form.configKey || !form.configValue) {
    ElMessage.warning('请填写必填项')
    return
  }

  try {
    if (form.configId) {
      await updateConfig(form)
      ElMessage.success('修改成功')
    } else {
      await addConfig(form)
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
    await ElMessageBox.confirm('确定要删除这条配置吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteConfig(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败: ' + (error.message || '未知错误'))
    }
  }
}

const handleBatchDelete = async () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请选择要删除的数据')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedRows.value.length} 条配置吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    const ids = selectedRows.value.map(row => row.configId).join(',')
    await deleteConfig(ids)
    ElMessage.success('批量删除成功')
    selectedRows.value = []
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量删除失败: ' + (error.message || '未知错误'))
    }
  }
}

const handleSelectionChange = (selection) => {
  selectedRows.value = selection
}

const handleSizeChange = (pageSize) => {
  paginationReactive.pageSize = pageSize
  paginationReactive.page = 1
  loadData()
}

const handleCurrentChange = (page) => {
  paginationReactive.page = page
  loadData()
}

onMounted(() => {
  loadData()
})
</script>

<style lang="less" scoped>
.config-page {
  .search-card {
    margin-bottom: 20px;
  }

  .table-card {
    .pagination-wrapper {
      margin-top: 20px;
      display: flex;
      justify-content: flex-end;
    }

    .batch-actions {
      margin-top: 16px;
    }
  }
}
</style>
