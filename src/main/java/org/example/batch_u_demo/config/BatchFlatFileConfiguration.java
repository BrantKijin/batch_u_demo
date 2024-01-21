package org.example.batch_u_demo.config;

import java.util.ArrayList;
import java.util.List;

import org.example.batch_u_demo.reader.ProductNameItemReader;
import org.example.domain.Product;
import org.example.domain.ProductFieldSetMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BatchFlatFileConfiguration {

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
	public ItemReader<Product> flatFileItemReader(){
		FlatFileItemReader<Product> itemReader = new FlatFileItemReader<>();
		itemReader.setLinesToSkip(1);
		itemReader.setResource(new ClassPathResource("/data/Product_Details.csv"));
		DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("product_id","product_name","product_category","product_price");
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(new ProductFieldSetMapper());

		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}
	@Bean
	public Step chunkCSVStep1(){

		return this.stepBuilderFactory.get("chunkCSVStep1")
			.<Product,Product>chunk(3)
			.reader(flatFileItemReader())
			.writer(new ItemWriter<Product>() {
				@Override
				public void write(List<? extends Product> list) throws Exception {
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
			.start(chunkCSVStep1())

			.build();
	}
}
