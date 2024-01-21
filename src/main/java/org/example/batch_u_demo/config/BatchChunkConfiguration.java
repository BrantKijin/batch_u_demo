package org.example.batch_u_demo.config;

import java.util.ArrayList;
import java.util.List;

import org.example.batch_u_demo.reader.ProductNameItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BatchChunkConfiguration {

	public final JobBuilderFactory jobBuilderFactory;
	public final StepBuilderFactory stepBuilderFactory;


	@Bean
	public ItemReader<String> itemReader(){

		List<String> productList = new ArrayList<>();
		productList.add("Product 1");
		productList.add("Product 2");
		productList.add("Product 3");
		productList.add("Product 4");
		productList.add("Product 5");
		productList.add("Product 6");
		productList.add("Product 7");
		productList.add("Product 8");

		return new ProductNameItemReader(productList);

	};
	@Bean
	public Step chunkStep1(){

		return this.stepBuilderFactory.get("chunkBasedStep1")
			.<String,String>chunk(3)
			.reader(itemReader())
			.writer(new ItemWriter<String>() {
				@Override
				public void write(List<? extends String> list) throws Exception {
					System.out.println("Chunk-processing Started");
					list.forEach(System.out::println);
					System.out.println("Chunk-processingEnded");
				}
			})
			.build();

	}



	@Bean
	public Job firstJob(){
		return this.jobBuilderFactory.get("chunkJob")
			.start(chunkStep1())

			.build();
	}
}
