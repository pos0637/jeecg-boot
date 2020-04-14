<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'description', validatorRules.description]" placeholder="请输入描述"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="机器人分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['robotCategory', validatorRules.robotCategory]" :trigger-change="true" dictCode="robot_category" placeholder="请选择机器人分类"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="检测项目" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="robotTestingTemplateItemTable.loading"
            :columns="robotTestingTemplateItemTable.columns"
            :dataSource="robotTestingTemplateItemTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'RobotTestingTemplateModal',
    mixins: [JEditableTableMixin],
    components: {
      JDictSelectTag,
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          name: {rules: [
            {required: true, message: '请输入名称!'},
          ]},
          description: {rules: [
          ]},
          robotCategory: {rules: [
          ]},
        },
        refKeys: ['robotTestingTemplateItem', ],
        tableKeys:['robotTestingTemplateItem', ],
        activeKey: 'robotTestingTemplateItem',
        // 检测项目
        robotTestingTemplateItemTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '项目类型',
              key: 'category',
              type: FormTypes.select,
              dictCode:"testing_category",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '参数配置',
              key: 'configuration',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/testingsystem/robotTestingTemplate/add",
          edit: "/testingsystem/robotTestingTemplate/edit",
          robotTestingTemplateItem: {
            list: '/testingsystem/robotTestingTemplate/queryRobotTestingTemplateItemByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'name','description','robotCategory')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.robotTestingTemplateItem.list, params, this.robotTestingTemplateItemTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          robotTestingTemplateItemList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'name','description','robotCategory'))
     },

    }
  }
</script>

<style scoped>
</style>