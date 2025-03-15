package com.olivia.peanut.task.api.entity.taskInstance;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 任务流程实例(TaskInstance)查询对象返回
 *
 * @author makejava
 * @since 2025-03-09 14:13:53
 */
@Accessors(chain = true)
@Getter
@Setter
@SuppressWarnings("serial")
public class TaskInstanceQueryByIdListRes {
  /***
   * 返回对象列表
   */
  private List<TaskInstanceDto> dataList;


}

