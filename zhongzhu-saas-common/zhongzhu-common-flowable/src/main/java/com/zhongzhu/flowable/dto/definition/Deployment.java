package com.zhongzhu.flowable.dto.definition;

import com.zhongzhu.core.event.AggregateRoot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author shihao.liu
 */
@Data
@SuperBuilder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@Schema(name = "Deployment", description = "部署")
public class Deployment extends AggregateRoot<Long> {

	private static final String BPMN_FILE_SUFFIX = ".bpmn";

	@Schema(name = "key", description = "键")
	private InputStream inputStream;

	@Schema(name = "inputStream", description = "输入流")
	private String key;

	@Schema(name = "name", description = "名称")
	private String name;

	@SneakyThrows
	public Deployment(MultipartFile file) {
		this.inputStream = file.getInputStream();
	}

	public void modify(String id, String name) {
		this.key = id;
		this.name = name + BPMN_FILE_SUFFIX;
	}

	public void checkKey(long count) {
		if (count > 0) {
			throw new RuntimeException("流程已存在，请更换流程图并上传");
		}
	}

}
