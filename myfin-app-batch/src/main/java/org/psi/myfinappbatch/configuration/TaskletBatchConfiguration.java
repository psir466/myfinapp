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
import org.springframework.batch.core.Job;

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
            // Your task logic here
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
