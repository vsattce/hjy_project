<template>
  <div class="dept-page">
    <!-- 操作栏 -->
    <el-card class="search-card" shadow="never">
      <el-form :inline="true">
        <el-form-item label="选择根节点">
          <el-tree-select
            v-model="rootId"
            :data="allDeptTree"
            :props="{ label: 'deptName', value: 'deptId' }"
            placeholder="请选择根节点部门"
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
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 树形表格 -->
    <el-card class="table-card" shadow="never">
      <el-table 
        :data="tableData" 
        style="width: 100%" 
        row-key="deptId" 
        :tree-props="{ children: 'children' }"
      >
        <el-table-column prop="deptName" label="部门名称" min-width="200" />
        <el-table-column prop="orderNum" label="排序" width="100" />
        <el-table-column prop="leader" label="负责人" width="120" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === '0' ? 'success' : 'danger'">
              {{ row.status === '0' ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.deptId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="上级部门">
          <el-tree-select
            v-model="form.parentId"
            :data="tableData"
            :props="{ label: 'deptName', value: 'deptId' }"
            placeholder="请选择上级部门"
            check-strictly
          />
        </el-form-item>
        <el-form-item label="部门名称" required>
          <el-input v-model="form.deptName" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="显示排序" required>
          <el-input-number v-model="form.orderNum" :min="0" />
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="form.leader" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
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
import { getDeptTreeByRoot, addDept, updateDept, deleteDept } from '@/api/dept'

const tableData = ref([])
const allDeptTree = ref([]) // 所有部门树，用于根节点选择
const dialogVisible = ref(false)
const dialogTitle = ref('新增部门')
const rootId = ref(null)

const form = reactive({
  deptId: null,
  parentId: 0,
  deptName: '',
  orderNum: 0,
  leader: '',
  phone: '',
  email: '',
  status: '0'
})

// 加载所有部门树（用于根节点选择）
const loadAllDeptTree = async () => {
  try {
    const response = await getDeptTreeByRoot(0)
    if (response.code === 200) {
      allDeptTree.value = response.data || []
    }
  } catch (error) {
    console.error('加载部门树失败', error)
  }
}

const loadTreeData = async () => {
  try {
    const response = await getDeptTreeByRoot(rootId.value||0)
    if (response.code === 200) {
      tableData.value = response.data || []
    }
  } catch (error) {
    ElMessage.error('加载数据失败: ' + (error.message || '未知错误'))
  }
}

const handleAdd = () => {
  dialogTitle.value = '新增部门'
  Object.assign(form, { 
    deptId: null, 
    parentId: 0, 
    deptName: '', 
    orderNum: 0, 
    leader: '', 
    phone: '', 
    email: '', 
    status: '0' 
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑部门'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    if (form.deptId) {
      await updateDept(form)
      ElMessage.success('修改成功')
    } else {
      await addDept(form)
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
    await deleteDept(id)
    ElMessage.success('删除成功')
    loadTreeData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败: ' + (error.message || '未知错误'))
    }
  }
}

onMounted(() => {
  loadAllDeptTree()
  loadTreeData()
})
</script>

<style lang="less" scoped>
.dept-page {
  .search-card {
    margin-bottom: 20px;
  }
}
</style>
