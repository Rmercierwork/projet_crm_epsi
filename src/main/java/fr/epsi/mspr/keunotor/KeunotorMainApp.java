package fr.epsi.mspr.keunotor;
//TODO : Javadoc
//TODO : Remplir les fonctionnalités de base

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.epsi.mspr.keunotor")
public class KeunotorMainApp {
    public static void main(String[] args) {
        SpringApplication.run(KeunotorMainApp.class, args);
    }
}