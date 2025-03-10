<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="88px" size="small">
      <el-form-item label="任务id" prop="taskDefId">
        <el-input v-model="queryParams.data.taskDefId" clearable placeholder="请输入任务id"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="任务内容" prop="instanceContent">
        <el-input v-model="queryParams.data.instanceContent" clearable placeholder="请输入任务内容"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="执行状态 " prop="taskExecStatus">
        <el-input v-model="queryParams.data.taskExecStatus" clearable placeholder="请输入执行状态 "
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="异常描述" prop="exceptionMsg">
        <el-input v-model="queryParams.data.exceptionMsg" clearable placeholder="请输入异常描述"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="耗时" prop="useTime">
        <el-input v-model="queryParams.data.useTime" clearable placeholder="请输入耗时"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="次数" prop="execLoop">
        <el-input v-model="queryParams.data.execLoop" clearable placeholder="请输入次数"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="检查次数" prop="checkLoop">
        <el-input v-model="queryParams.data.checkLoop" clearable placeholder="请输入检查次数"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="检查状态" prop="checkExecStatus">
        <el-input v-model="queryParams.data.checkExecStatus" clearable placeholder="请输入检查状态"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="检查异常信息" prop="checkExceptionMsg">
        <el-input v-model="queryParams.data.checkExceptionMsg" clearable placeholder="请输入检查异常信息"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="执行日期" prop="execDate">
        <el-input v-model="queryParams.data.execDate" clearable placeholder="请输入执行日期"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button icon="el-icon-plus" plain size="mini" type="primary" @click="handleAdd"></el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button :disabled="multiple" icon="el-icon-delete" plain size="mini" type="danger"
                   @click="handleDelete"></el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskInstanceList" @selection-change="handleSelectionChange">
      <el-table-column align="center" label="全选" prop="id" type="selection" width="50"/>
      <el-table-column v-for="(item,index) in  tableHeaderList" :key="index" :label="item.showName"
                       :prop="item.fieldName" align="center" :width="item.width+'px'"/>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button icon="el-icon-edit" size="mini" type="text" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button icon="el-icon-delete" size="mini" type="text" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :limit.sync="queryParams.pageSize"
        :page.sync="queryParams.pageNum"
        :total="total"
        @pagination="getList"
    />

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">

        <el-form-item label="任务id" prop="taskDefId">
          <el-input v-model="form.taskDefId" clearable placeholder="请输入任务id"/>
        </el-form-item>
        <el-form-item label="任务内容" prop="instanceContent">
          <el-input v-model="form.instanceContent" clearable placeholder="请输入任务内容"/>
        </el-form-item>
        <el-form-item label="执行状态 " prop="taskExecStatus">
          <el-input v-model="form.taskExecStatus" clearable placeholder="请输入执行状态 "/>
        </el-form-item>
        <el-form-item label="异常描述" prop="exceptionMsg">
          <el-input v-model="form.exceptionMsg" clearable placeholder="请输入异常描述"/>
        </el-form-item>
        <el-form-item label="耗时" prop="useTime">
          <el-input v-model="form.useTime" clearable placeholder="请输入耗时"/>
        </el-form-item>
        <el-form-item label="次数" prop="execLoop">
          <el-input v-model="form.execLoop" clearable placeholder="请输入次数"/>
        </el-form-item>
        <el-form-item label="检查次数" prop="checkLoop">
          <el-input v-model="form.checkLoop" clearable placeholder="请输入检查次数"/>
        </el-form-item>
        <el-form-item label="检查状态" prop="checkExecStatus">
          <el-input v-model="form.checkExecStatus" clearable placeholder="请输入检查状态"/>
        </el-form-item>
        <el-form-item label="检查异常信息" prop="checkExceptionMsg">
          <el-input v-model="form.checkExceptionMsg" clearable placeholder="请输入检查异常信息"/>
        </el-form-item>
        <el-form-item label="执行日期" prop="execDate">
          <el-input v-model="form.execDate" clearable placeholder="请输入执行日期"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>

import {add, insetOrUpdate, deleteList, deleteByIdList, getById, queryPageList, updateById} from '@/api/common'
import {getFactoryList} from '@/api/factory'

export default {
  name: 'tenantName',
  data() {

    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      taskInstanceList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        data: {}
      },
      // 表单参数
      form: {
        taskDefId: undefined,
        instanceContent: undefined,
        taskExecStatus: undefined,
        exceptionMsg: undefined,
        useTime: undefined,
        execLoop: undefined,
        checkLoop: undefined,
        checkExecStatus: undefined,
        checkExceptionMsg: undefined,
        execDate: undefined,
        id: undefined
      },
      // 表单校验
      rules: {
        taskDefId: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
        instanceContent: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
        taskExecStatus: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
        exceptionMsg: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
        useTime: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
        execLoop: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
        checkLoop: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
        checkExecStatus: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
        checkExceptionMsg: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
        execDate: [{required: true, message: "不能为空", trigger: "blur"}, {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }],
      },
      tableHeaderList: []
    }
  },
  created() {
    document['pagePath'] = '/taskInstance'
    this.getList()
  },
  methods: {
    /** 查询公告列表 */
    getList() {
      this.loading = true
      queryPageList(this.queryParams).then(response => {
        response = response.data
        this.tableHeaderList = response.headerList
        this.taskInstanceList = response.dataList
        this.total = parseInt(response.total)
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      let fid = this.form.id
      this.form = {
        taskDefId: undefined,
        instanceContent: undefined,
        taskExecStatus: undefined,
        exceptionMsg: undefined,
        useTime: undefined,
        execLoop: undefined,
        checkLoop: undefined,
        checkExecStatus: undefined,
        checkExceptionMsg: undefined,
        execDate: undefined,
        id: fid
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.title = '添加任务流程实例'
      this.open = true
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      let req = {idList: [row.id], pageSize: 1, pageNum: 1}
      getById(req).then(response => {
        this.form = response.data.dataList[0]
        this.title = '修改任务流程实例'
        this.open = true
      })

    },

    /** 提交按钮 */
    submitForm: function () {
      insetOrUpdate(this);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      deleteList(row, this.ids, this.getList());
    }
  }

}


</script>

