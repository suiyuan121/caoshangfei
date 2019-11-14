package com.zj.caoshangfei.service;

import com.zj.caoshangfei.model.Question;
import com.zj.caoshangfei.service.repository.VideoEntityRepository;
import com.zj.caoshangfei.utils.JacksonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjin
 * @date 2018-06-27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

	@Autowired
	private QuestionRepository questionRepository;


	@Test
	public void save() {
		Question question = new Question();
		question.setCategory("shuyang");
		question.setSort(1f);
		question.setTitle("第一题");
		question.setType(Question.Type.dialect);

		Question.QuestionOption questionOption1 = new Question().new QuestionOption();
		questionOption1.setContent("选项一");


		Question.QuestionOption questionOption2 = new Question().new QuestionOption();
		questionOption2.setContent("选项二");

		Question.QuestionOption questionOption3 = new Question().new QuestionOption();
		questionOption3.setContent("选项三");


		List<Question.QuestionOption> questionList = new ArrayList<>();
		questionList.add(questionOption1);
		questionList.add(questionOption2);
		questionList.add(questionOption3);

		question.setOptions(JacksonUtils.beanToJson(questionList));


		questionRepository.save(question);


		Question question2 = new Question();
		question2.setCategory("shuyang");
		question2.setSort(2f);
		question2.setTitle("第二题");
		question2.setType(Question.Type.dialect);


		List<Question.QuestionOption> questionList2 = new ArrayList<>();
		questionList2.add(questionOption1);
		questionList2.add(questionOption2);
		questionList2.add(questionOption3);

		question2.setOptions(JacksonUtils.beanToJson(questionList));


		questionRepository.save(question2);
	}


	@Test
	public void get() {
		Question question = questionRepository.findOne(2);
		System.out.print(question);
	}


}
