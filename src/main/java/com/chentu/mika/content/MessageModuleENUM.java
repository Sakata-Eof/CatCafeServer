package com.chentu.mika.content;

import lombok.Getter;

/**
 * @Description: 模块枚举
 */
@Getter
public enum MessageModuleENUM {
	
	JOB_APPROVAL("jobApproval", "职位审批"),
	JOB_INTERVIEW("jobInterview", "职位面试"),
	RESUME_SUBMIT("resumeSubmit", "简历提交"),
	RESUME_VIEW("resumeView", "简历浏览"),
	RESUME_DOWNLOAD("resumeDownload", "简历下载"),
	RESUME_APPROVAL("resumeApproval", "简历审批"),
	RESUME_REJECT("resumeReject", "简历拒绝"),
	;
	
	private final String module;
	private final String desc;
	
	MessageModuleENUM( String module,String desc) {
		this.module = module;
		this.desc = desc;
	}
	public static String getDesc(String module) {
		for (MessageModuleENUM item : MessageModuleENUM.values()) {
			if (item.getModule().equals(module)) {
				return item.getDesc();
			}
		}
		return null;
	}
}
