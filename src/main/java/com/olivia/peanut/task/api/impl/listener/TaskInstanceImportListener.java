package com.olivia.peanut.task.api.impl.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.olivia.peanut.task.api.entity.taskInstance.TaskInstanceImportReq;
import com.olivia.sdk.listener.AbstractImportListener;
import com.olivia.sdk.utils.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务流程实例(TaskInstance)文件导入监听
 *
 * @author makejava
 * @since 2025-03-09 14:13:52
 */
@Slf4j
public class TaskInstanceImportListener extends AbstractImportListener<TaskInstanceImportReq> {

  @Override
  public void invoke(TaskInstanceImportReq data, AnalysisContext analysisContext) {
    //  文件校验
    log.info("TaskInstanceImportListener invoke data:{}", JSON.toJSONString(data));
    checkData(data, analysisContext);

  }

}
