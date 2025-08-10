package org.psi.myfinappfrontapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// pour gérer recheminement vers index.htlm lors du refresh F5 des app angular (à noter que j'ai mis des # dans le URL de l'app angular donc la class n'est pas utile ici)
@Configuration
public class Webconfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirige toutes les requêtes vers l'index.html, sauf celles qui ont une extension de fichier
      registry.addViewController("/{path:[^\\.]*}").setViewName("forward:/index.html");
    }
}
