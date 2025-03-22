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
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;

import controller.CustomApiApi;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.batch.core.Job;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.psi.myfinappfrontapp.model.AccountHeaderDTO;
import org.psi.myfinappfrontapp.model.AccountLineDTO;
import org.psi.myfinappfrontapp.model.AmountOfMoney;
import org.psi.myfinappfrontapp.model.AccountHeaderDTO.AccountTypeEnum;
import org.psi.myfinappfrontapp.model.AmountOfMoney.CurrencyEnum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

@Configuration
//@EnableBatchProcessing (nee pas mettre dans spring boot 3 car sinon le job ne démarre pas au démarrage de l'application )
public class TaskletBatchConfiguration {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    

    private CustomApiApi customApiApi = new CustomApiApi();

   

    @Bean
    public Tasklet myTasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("Executing my tasklet...");
            
            List<AccountHeaderDTO> accountList = new ArrayList<>();

            FileInputStream file = new FileInputStream("C:\\Users\\psir1\\Documents\\Perso\\budget\\budget2.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
           

   

        for (int indexSheet = 0; indexSheet < workbook.getNumberOfSheets(); indexSheet++){

            XSSFSheet sheet = workbook.getSheetAt(indexSheet);

          /* System.out.println("*******************" + sheet.getSheetName() + "*********************");
            System.out.println("*******************" + sheet.getSheetName().substring(4, sheet.getSheetName().length()) + "*********************");
            System.out.println("*******************" + sheet.getSheetName().substring(2, 4) + "*********************");*/


            String year = sheet.getSheetName().substring(4, sheet.getSheetName().length());
            String month = sheet.getSheetName().substring(2, 4);


            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);
                if (row != null) {

                    AccountHeaderDTO account = null;

                    for (int cellIndex = 0; cellIndex < 2; cellIndex++) {
                        XSSFCell cell = row.getCell(cellIndex);
                       // if (cell != null) {
                          

                            if(cellIndex == 0 && (cell == null || cell.toString() == null || cell.toString().isEmpty() || cell.toString().isBlank())){
                                break;
                            }
                            //System.out.print(cell.toString() + "\t");
                           // System.out.print(cell.toString());

                            if(cellIndex == 0){
                                Optional<AccountHeaderDTO> opAccount = accountList.stream().filter(a -> a.getName().equals(cell.toString())).findAny();
                                if(opAccount.isPresent()){

                                    account = opAccount.get();

                                }else{

                                    account = new AccountHeaderDTO();
                                    account.setName(cell.toString());

                                    getaccountName(account, cell.toString());

                                    accountList.add(account);

                                }
                            }

                            if(account == null){

                                System.out.println("***************** NULLL ***************");
                            }
                                                       
                            if(cellIndex == 1){
                                AccountLineDTO accountLineDTO = new AccountLineDTO();
                                AmountOfMoney amountOfMoney = new AmountOfMoney();
                                amountOfMoney.setCurrency(CurrencyEnum.EUR);
                                amountOfMoney.setAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
                                accountLineDTO.setSolde(amountOfMoney);
                                accountLineDTO.setDate(LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), 1));
                                account.getAccountLines().add(accountLineDTO);
                            }
                                
                      //  }
                    }
                }
            }
        

        }

        workbook.close();
        file.close();

            System.out.print("****************" + accountList.size());

            System.out.print(accountList.get(0));

            System.out.print(accountList.get(5));

           Mono<String> ms = this.customApiApi.postAccountsToDataBase(accountList);

           ms.subscribe();
           

            System.out.println("Tasklet execution complete.");
            return RepeatStatus.FINISHED;
        };
    }

    private void getaccountName(AccountHeaderDTO account, String cellToString){
        if(cellToString.equals("Cachmir2")){
            account.setAccountType(AccountTypeEnum.ASSURVIE);
        }else if(cellToString.equals("PEE + PER")){
            account.setAccountType(AccountTypeEnum.EPARGNESALARIALE);
        }else if(cellToString.equals("PEA")){
            account.setAccountType(AccountTypeEnum.PEA);
        }else if(cellToString.equals("PEA PME")){
            account.setAccountType(AccountTypeEnum.PEAPME);
        }else if(cellToString.equals("CTO")){
            account.setAccountType(AccountTypeEnum.CTO);
        }else if(cellToString.equals("Mutavie")){
            account.setAccountType(AccountTypeEnum.ASSURVIE);
        }else if(cellToString.equals("Spirica")){
            account.setAccountType(AccountTypeEnum.ASSURVIE);
        }else if(cellToString.equals("LLD")){
            account.setAccountType(AccountTypeEnum.EPARGNESALARIALE);
        }else if(cellToString.equals("Livret A")){
            account.setAccountType(AccountTypeEnum.EPARGNESALARIALE);
        }else if(cellToString.equals("CEL")){
            account.setAccountType(AccountTypeEnum.EPARGNESALARIALE);
        }else if(cellToString.equals("Compte courant La poste")){
            account.setAccountType(AccountTypeEnum.EPARGNESALARIALE);
        }else if(cellToString.equals("Assurance vie Fortunéo")){
            account.setAccountType(AccountTypeEnum.EPARGNESALARIALE);
        }else if(cellToString.equals("Compte courant Fortuneo")){
            account.setAccountType(AccountTypeEnum.EPARGNESALARIALE);
        }else if(cellToString.equals("assurance vie Linxea")){
            account.setAccountType(AccountTypeEnum.EPARGNESALARIALE);
        }else if(cellToString.equals("prêt immo")){
            account.setAccountType(AccountTypeEnum.EPARGNESALARIALE);
        }

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
