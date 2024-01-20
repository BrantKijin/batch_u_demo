package org.example.batch_u_demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;

@EnableBatchProcessing
@SpringBootApplication
@RequiredArgsConstructor
public class BatchUDemoApplication {

	public final JobBuilderFactory jobBuilderFactory;
	public final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step firstStep(){
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
	public Job firstJob(){
		return this.jobBuilderFactory.get("job1")
			.start(firstStep())
			.build();
	}
	public static void main(String[] args) {
		SpringApplication.run(BatchUDemoApplication.class, args);
	}

}