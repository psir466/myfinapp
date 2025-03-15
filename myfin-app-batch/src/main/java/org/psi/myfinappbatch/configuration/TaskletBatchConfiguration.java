package org.psi.myfinappbatch.configuration;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.batch.core.Job;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

@Configuration
//@EnableBatchProcessing (nee pas mettre dans spring boot 3 car sinon le job ne démarre pas au démarrage de l'application )
public class TaskletBatchConfiguration {

      @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Tasklet myTasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("Executing my tasklet...");
            

            FileInputStream file = new FileInputStream("C:\\Users\\psir1\\Documents\\Perso\\budget\\budget2.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);
                if (row != null) {
                    for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                        XSSFCell cell = row.getCell(cellIndex);
                        if (cell != null) {
                            System.out.print(cell.toString() + "\t");
                        }
                    }
                    System.out.println();
                }
            }

            workbook.close();
            file.close();

            System.out.println("Tasklet execution complete.");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step taskletStep(Tasklet myTasklet) {
        return new StepBuilder("taskletStep", jobRepository)
                .tasklet(myTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job taskletJob(Step taskletStep) {
        return new JobBuilder("taskletJob", jobRepository)
                .start(taskletStep)
                .build();
    }

}
