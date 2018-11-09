package net.jeebiz.boot.authz.rbac0.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "AuthzRolePaginationVo", description = "角色信息分页查询参数Vo")
public class AuthzRolePaginationVo extends AbstractPaginationVo {
	
	/**
	 * 角色ID
	 */
	@ApiModelProperty(value = "id", dataType = "Integer", notes = "角色ID")
	private String id;
	/**
	 * 角色状态(0:不可用|1:正常)
	 */
	@ApiModelProperty(value = "status", dataType = "String", notes = "角色状态：0:不可用|1:正常")
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
