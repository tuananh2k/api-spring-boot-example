package com.example.helloword.configs;

import com.example.helloword.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
@Configuration
public class CsvToDatabaseJob {
    public static final Logger logger = LoggerFactory.getLogger(CsvToDatabaseJob.class);
    private static final String INSERT_QUERY = """
      INSERT INTO users (email, password, name)
      values (:email, :password, :name)""";
    private final JobRepository jobRepository;
    public CsvToDatabaseJob(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    @Value("classpath:${file.input}")
    private Resource inputFeed;
    @Bean
    public Job insertIntoDbFromCsvJob(Step step1) {
        var name = "Users Import Job";
        var builder = new JobBuilder(name, jobRepository);
        return builder.start(step1).listener(new JobCompletionNotificationListener()).build();
    }
    @Bean
    public Step step1(ItemReader<User> reader,
                      ItemWriter<User> writer,
                      PlatformTransactionManager txManager) {
        var name = "INSERT CSV RECORDS To DB Step";
        var builder = new StepBuilder(name, jobRepository);
        return builder
                .<User, User>chunk(5, txManager)
                .reader(reader)
                .writer(writer)
                .build();
    }
    @Bean
    public FlatFileItemReader<User> csvFileReader(
            LineMapper<User> lineMapper) {
        var itemReader = new FlatFileItemReader<User>();
        itemReader.setLineMapper(lineMapper);
        itemReader.setResource(inputFeed);
        return itemReader;
    }
    @Bean
    public DefaultLineMapper<User> lineMapper(LineTokenizer tokenizer,
                                                FieldSetMapper<User> mapper) {
        var lineMapper = new DefaultLineMapper<User>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);
        return lineMapper;
    }
    @Bean
    public BeanWrapperFieldSetMapper<User> fieldSetMapper() {
        var fieldSetMapper = new BeanWrapperFieldSetMapper<User>();
        fieldSetMapper.setTargetType(User.class);
        return fieldSetMapper;
    }
    @Bean
    public DelimitedLineTokenizer tokenizer() {
        var tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("name", "password", "email");
        return tokenizer;
    }
    @Bean
    public JdbcBatchItemWriter<User> jdbcItemWriter(DataSource dataSource) {
        var provider = new BeanPropertyItemSqlParameterSourceProvider<User>();
        var itemWriter = new JdbcBatchItemWriter<User>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql(INSERT_QUERY);
        itemWriter.setItemSqlParameterSourceProvider(provider);
        return itemWriter;
    }
}