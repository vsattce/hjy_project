<template>
  <div class="dict-page">
    <el-row :gutter="20">
      <!-- 左侧：字典类型 -->
      <el-col :span="8">
        <el-card shadow="never">
          <template #header>
            <div class="card-header">
              <span>字典类型</span>
              <el-button type="primary" size="small" @click="handleAddType">
                <el-icon><Plus /></el-icon>
                <span>新增</span>
              </el-button>
            </div>
          </template>

          <!-- 搜索 -->
          <el-input
            v-model="typeSearchForm.dictName"
            placeholder="请输入字典名称"
            clearable
            @keyup.enter="loadTypeData"
            style="margin-bottom: 15px"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>

          <!-- 字典类型列表 -->
          <el-table
            :data="typeTableData"
            style="width: 100%"
            highlight-current-row
            @current-change="handleTypeSelect"
            max-height="600"
          >
            <el-table-column prop="dictName" label="字典名称" show-overflow-tooltip />
            <el-table-column prop="dictType" label="字典类型" show-overflow-tooltip />
            <el-table-column prop="status" label="状态" width="70">
              <template #default="{ row }">
                <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">
                  {{ row.status === '0' ? '正常' : '停用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120" fixed="right">
              <template #default="{ row }">
                <el-button size="small" type="primary" link @click="handleEditType(row)">编辑</el-button>
                <el-button size="small" type="danger" link @click="handleDeleteType(row.dictId)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页 -->
          <div class="pagination-wrapper">
            <el-pagination
              v-model:current-page="typePagination.page"
              v-model:page-size="typePagination.pageSize"
              :page-sizes="PAGE_SIZES"
              :total="typePagination.total"
              layout="total, prev, pager, next"
              small
              @size-change="loadTypeData"
              @current-change="loadTypeData"
            />
          </div>
        </el-card>
      </el-col>

      <!-- 右侧：字典数据 -->
      <el-col :span="16">
        <el-card shadow="never">
          <template #header>
            <div class="card-header">
              <span>字典数据 {{ currentDictType ? `(${currentDictType})` : '' }}</span>
              <el-button
                type="primary"
                size="small"
                @click="handleAddData"
                :disabled="!currentDictType"
              >
                <el-icon><Plus /></el-icon>
                <span>新增</span>
              </el-button>
            </div>
          </template>

          <!-- 搜索栏 -->
          <el-form :inline="true" :model="dataSearchForm" style="margin-bottom: 15px">
            <el-form-item label="字典标签">
              <el-input v-model="dataSearchForm.dictLabel" placeholder="请输入字典标签" clearable />
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="dataSearchForm.status" placeholder="状态" clearable>
                <el-option label="正常" value="0" />
                <el-option label="停用" value="1" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleDataSearch">
                <el-icon><Search /></el-icon>
                <span>搜索</span>
              </el-button>
              <el-button @click="handleDataReset">
                <el-icon><Refresh /></el-icon>
                <span>重置</span>
              </el-button>
            </el-form-item>
          </el-form>

          <!-- 字典数据表格 -->
          <el-table :data="dataTableData" style="width: 100%">
            <el-table-column prop="dictCode" label="字典编码" width="100" />
            <el-table-column prop="dictLabel" label="字典标签" min-width="120" />
            <el-table-column prop="dictValue" label="字典键值" min-width="120" />
            <el-table-column prop="dictSort" label="排序" width="80" />
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }">
                <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">
                  {{ row.status === '0' ? '正常' : '停用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="160" />
            <el-table-column label="操作" width="150" fixed="right">
              <template #default="{ row }">
                <el-button size="small" type="primary" @click="handleEditData(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDeleteData(row.dictCode)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页 -->
          <div class="pagination-wrapper">
            <el-pagination
              v-model:current-page="dataPagination.page"
              v-model:page-size="dataPagination.pageSize"
              :page-sizes="PAGE_SIZES"
              :total="dataPagination.total"
              :layout="PAGINATION_LAYOUT"
              @size-change="loadDataData"
              @current-change="loadDataData"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 字典类型对话框 -->
    <el-dialog v-model="typeDialogVisible" :title="typeDialogTitle" width="600px">
      <el-form :model="typeForm" label-width="100px">
        <el-form-item label="字典名称" required>
          <el-input v-model="typeForm.dictName" placeholder="请输入字典名称" />
        </el-form-item>
        <el-form-item label="字典类型" required>
          <el-input v-model="typeForm.dictType" placeholder="请输入字典类型" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="typeForm.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="typeForm.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="typeDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleTypeSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 字典数据对话框 -->
    <el-dialog v-model="dataDialogVisible" :title="dataDialogTitle" width="600px">
      <el-form :model="dataForm" label-width="100px">
        <el-form-item label="字典类型">
          <el-input v-model="dataForm.dictType" disabled />
        </el-form-item>
        <el-form-item label="字典标签" required>
          <el-input v-model="dataForm.dictLabel" placeholder="请输入字典标签" />
        </el-form-item>
        <el-form-item label="字典键值" required>
          <el-input v-model="dataForm.dictValue" placeholder="请输入字典键值" />
        </el-form-item>
        <el-form-item label="显示排序" required>
          <el-input-number v-model="dataForm.dictSort" :min="0" />
        </el-form-item>
        <el-form-item label="回显样式">
          <el-select v-model="dataForm.listClass" placeholder="请选择">
            <el-option label="默认" value="default" />
            <el-option label="主要" value="primary" />
            <el-option label="成功" value="success" />
            <el-option label="信息" value="info" />
            <el-option label="警告" value="warning" />
            <el-option label="危险" value="danger" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="dataForm.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="dataForm.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dataDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleDataSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getDictTypePage,
  addDictType,
  updateDictType,
  deleteDictType,
  getDictDataPage,
  addDictData,
  updateDictData,
  deleteDictData
} from '@/api/system/dict'
import { PAGE_SIZES, PAGINATION_LAYOUT } from '@/config/pagination'

// 字典类型相关
const typeSearchForm = reactive({
  dictName: ''
})

const typeTableData = ref([])
const typeDialogVisible = ref(false)
const typeDialogTitle = ref('新增字典类型')
const currentDictType = ref('')

const typeForm = reactive({
  dictId: null,
  dictName: '',
  dictType: '',
  status: '0',
  remark: ''
})

const typePagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 字典数据相关
const dataSearchForm = reactive({
  dictLabel: '',
  status: ''
})

const dataTableData = ref([])
const dataDialogVisible = ref(false)
const dataDialogTitle = ref('新增字典数据')

const dataForm = reactive({
  dictCode: null,
  dictType: '',
  dictLabel: '',
  dictValue: '',
  dictSort: 0,
  listClass: 'default',
  status: '0',
  remark: ''
})

const dataPagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 加载字典类型数据
const loadTypeData = async () => {
  try {
    const params = {
      current: typePagination.page,
      size: typePagination.pageSize,
      ...typeSearchForm
    }
    const response = await getDictTypePage(params)
    if (response.code === 200 && response.data) {
      typeTableData.value = response.data.records || []
      typePagination.total = response.data.total || 0
    }
  } catch (error) {
    ElMessage.error('加载字典类型失败: ' + (error.message || '未知错误'))
  }
}

// 选择字典类型
const handleTypeSelect = (row) => {
  if (row) {
    currentDictType.value = row.dictType
    dataSearchForm.dictLabel = ''
    dataSearchForm.status = ''
    dataPagination.page = 1
    loadDataData()
  }
}

// 加载字典数据
const loadDataData = async () => {
  if (!currentDictType.value) return
  
  try {
    const params = {
      current: dataPagination.page,
      size: dataPagination.pageSize,
      dictType: currentDictType.value,
      ...dataSearchForm
    }
    const response = await getDictDataPage(params)
    if (response.code === 200 && response.data) {
      dataTableData.value = response.data.records || []
      dataPagination.total = response.data.total || 0
    }
  } catch (error) {
    ElMessage.error('加载字典数据失败: ' + (error.message || '未知错误'))
  }
}

// 字典类型操作
const handleAddType = () => {
  typeDialogTitle.value = '新增字典类型'
  Object.assign(typeForm, { dictId: null, dictName: '', dictType: '', status: '0', remark: '' })
  typeDialogVisible.value = true
}

const handleEditType = (row) => {
  typeDialogTitle.value = '编辑字典类型'
  Object.assign(typeForm, row)
  typeDialogVisible.value = true
}

const handleTypeSubmit = async () => {
  if (!typeForm.dictName || !typeForm.dictType) {
    ElMessage.warning('请填写必填项')
    return
  }

  try {
    if (typeForm.dictId) {
      await updateDictType(typeForm)
      ElMessage.success('修改成功')
    } else {
      await addDictType(typeForm)
      ElMessage.success('新增成功')
    }
    typeDialogVisible.value = false
    loadTypeData()
  } catch (error) {
    ElMessage.error('操作失败: ' + (error.message || '未知错误'))
  }
}

const handleDeleteType = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该字典类型吗？', '提示', { type: 'warning' })
    await deleteDictType(id)
    ElMessage.success('删除成功')
    currentDictType.value = ''
    dataTableData.value = []
    loadTypeData()
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败: ' + (error.message || '未知错误'))
  }
}

// 字典数据操作
const handleAddData = () => {
  dataDialogTitle.value = '新增字典数据'
  Object.assign(dataForm, {
    dictCode: null,
    dictType: currentDictType.value,
    dictLabel: '',
    dictValue: '',
    dictSort: 0,
    listClass: 'default',
    status: '0',
    remark: ''
  })
  dataDialogVisible.value = true
}

const handleEditData = (row) => {
  dataDialogTitle.value = '编辑字典数据'
  Object.assign(dataForm, row)
  dataDialogVisible.value = true
}

const handleDataSubmit = async () => {
  if (!dataForm.dictLabel || !dataForm.dictValue) {
    ElMessage.warning('请填写必填项')
    return
  }

  try {
    if (dataForm.dictCode) {
      await updateDictData(dataForm)
      ElMessage.success('修改成功')
    } else {
      await addDictData(dataForm)
      ElMessage.success('新增成功')
    }
    dataDialogVisible.value = false
    loadDataData()
  } catch (error) {
    ElMessage.error('操作失败: ' + (error.message || '未知错误'))
  }
}

const handleDeleteData = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该字典数据吗？', '提示', { type: 'warning' })
    await deleteDictData(id)
    ElMessage.success('删除成功')
    loadDataData()
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败: ' + (error.message || '未知错误'))
  }
}

const handleDataSearch = () => {
  dataPagination.page = 1
  loadDataData()
}

const handleDataReset = () => {
  dataSearchForm.dictLabel = ''
  dataSearchForm.status = ''
  handleDataSearch()
}

onMounted(() => {
  loadTypeData()
})
</script>

<style lang="less" scoped>
.dict-page {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .pagination-wrapper {
    margin-top: 15px;
    display: flex;
    justify-content: flex-end;
  }
}
</style>
