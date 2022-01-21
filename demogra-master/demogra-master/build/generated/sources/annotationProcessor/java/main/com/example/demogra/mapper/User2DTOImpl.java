package com.example.demogra.mapper;

import com.example.demogra.entity.Client;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-21T13:58:51-0600",
    comments = "version: 1.4.0.Beta2, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.3.2.jar, environment: Java 17.0.1 (Oracle Corporation)"
)
public class User2DTOImpl implements User2DTO {

    @Override
    public Client clientToClient(Client cl) {
        if ( cl == null ) {
            return null;
        }

        Client client = new Client();

        return client;
    }
}
