package com.aantivero.infi;

import com.aantivero.infi.config.ApplicationProperties;
import com.aantivero.infi.config.DefaultProfileUtil;

import com.aantivero.infi.domain.EntidadFinanciera;
import com.aantivero.infi.repository.EntidadFinancieraRepository;
import com.opencsv.CSVReader;
import io.github.jhipster.config.JHipsterConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@ComponentScan
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class InfiApp {

    private static final Logger log = LoggerFactory.getLogger(InfiApp.class);

    private final Environment env;

    public InfiApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes infi.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not" +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(InfiApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "External: \t{}://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            env.getActiveProfiles());
    }

}

@Component
class LoadInfiInitializer implements CommandLineRunner {

    private final EntidadFinancieraRepository eeffRepository;
    private final ResourceLoader resourceLoader;

    public LoadInfiInitializer(EntidadFinancieraRepository eeffRepository, ResourceLoader resourceLoader) {
        this.eeffRepository = eeffRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... strings) throws Exception {
        if (eeffRepository.count() == 0) {
            // Acá no funciona en heroku
            String eeffFileName = "classpath:com/aantivero/infi/eeff.csv";
            Resource resource = resourceLoader.getResource(eeffFileName);
            File eeffFile = resource.getFile();//new ClassPathResource(eeffFileName).getFile();
            CSVReader reader = new CSVReader(new FileReader(eeffFile), ';');

            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                //System.out.println(nextLine[0] + " - " + nextLine[1] + " - " + nextLine[2]);
                EntidadFinanciera eeff = new EntidadFinanciera();
                eeff.setCodigo(nextLine[0]);
                eeff.setCodigoNumerico(new Integer(nextLine[1]));
                eeff.setDenominacion(nextLine[2]);
                eeffRepository.save(eeff);
            }
        }
    }
}
