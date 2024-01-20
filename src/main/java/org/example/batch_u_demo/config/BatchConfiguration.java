package org.example.batch_u_demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

	public final JobBuilderFactory jobBuilderFactory;
	public final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step step1(){
		return this.stepBuilderFactory.get("step1")
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws
					Exception {
					System.out.println("step1 executed!!");
					return RepeatStatus.FINISHED;
				}
			})
			.build();

	}

	@Bean
	public Step step2(){
		return this.stepBuilderFactory.get("step2")
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws
					Exception {
					System.out.println("step1 executed!!");
					return RepeatStatus.FINISHED;
				}
			})
			.build();

	}
	@Bean
	public Step step3(){
		return this.stepBuilderFactory.get("step3")
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws
					Exception {
					System.out.println("step1 executed!!");
					return RepeatStatus.FINISHED;
				}
			})
			.build();
	}


	@Bean
	public Job firstJob(){
		return this.jobBuilderFactory.get("job1")
			.start(step1())
			.next(step2())
			.next(step3())
			.build();
	}
}
