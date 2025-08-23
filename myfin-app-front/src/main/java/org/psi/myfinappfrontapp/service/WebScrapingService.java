package org.psi.myfinappfrontapp.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebScrapingService {

    public ScrapBean scrapeWebsite() {
        try {


            // Définir un User-Agent de navigateur. C'est la clé.
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36";

            // Se connecter à l'URL et récupérer le document CAC40
            Document doc = Jsoup.connect("https://www.boursorama.com/bourse/indices/cours/1rPCAC/")
            .userAgent(userAgent)
            .get();

            // Sélectionner les éléments que tu veux récupérer
            Element cours = doc.select("span.c-instrument.c-instrument--last").first();

            Element variation = doc.select("span.c-instrument.c-instrument--variation").first();

            // Parcourir les éléments et les afficher
            System.out.println("Titre trouvé : " + cours.text());
            System.out.println("Variation trouvée : " + variation.text());

            return new ScrapBean(cours.text(), variation.text());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
