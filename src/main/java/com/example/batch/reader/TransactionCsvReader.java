package com.example.batch.reader;


import com.example.batch.dto.TransactionCsv;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class TransactionCsvReader
        extends FlatFileItemReader<TransactionCsv> {

    public TransactionCsvReader(
            @Value("#{jobParameters['filePath']}") String filePath
    ) {
        setResource(new FileSystemResource(filePath));
        setLinesToSkip(1);

        DelimitedLineTokenizer tokenizer =
                new DelimitedLineTokenizer();
        tokenizer.setNames("id", "description", "amount");
        tokenizer.setStrict(false);

        BeanWrapperFieldSetMapper<TransactionCsv> mapper =
                new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(TransactionCsv.class);

        DefaultLineMapper<TransactionCsv> lineMapper =
                new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);

        setLineMapper(lineMapper);
    }
}
