/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.boot.demo.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DemoDTO", description = "xxx数据传输对象")
@Data
public class DemoDTO {

	@ApiModelProperty(value = "xxID", required = true)
	private String id;
	
	@ApiModelProperty(value = "xx名称", required = true)
	@NotBlank(message = "名称必填")
	private String name;
	
	@ApiModelProperty(value = "xx描述", required = true)
	@NotBlank(message = "描述必填")
	private String text;

}
