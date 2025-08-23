package org.psi.myfinappfrontapp.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CronService {


    private final SimpMessagingTemplate messagingTemplate;

    private final WebScrapingService webScrapingService;;

    @Autowired
    public CronService(SimpMessagingTemplate messagingTemplate, WebScrapingService webScrapingService) {
        this.messagingTemplate = messagingTemplate;
        this.webScrapingService = webScrapingService;
    }



     //@Scheduled(fixedRate = 1000000)
     @Scheduled(fixedRate = 30000)
     public void executeTask() {
        System.out.println("La tâche est en cours d'exécution toutes les 30 secondes !");


        this.messagingTemplate.convertAndSend("/topic/data-updates", this.webScrapingService.scrapeWebsite());

    }


      public static int genererNombreAleatoire() {
        // Crée une instance de la classe Random
        Random random = new Random();
        
        // Génère un nombre aléatoire entre 0 (inclus) et 99 (inclus)
        // La méthode nextInt(100) génère des nombres de 0 à 99
        int nombre = random.nextInt(100);
        
        // Ajoute 1 au nombre pour décaler la plage de 1 à 100
        return nombre + 1;
    }

}
