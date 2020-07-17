package org.mongodb.spring.multi.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@Configuration
//@EnableMongoRepositories(basePackages = "org.mongodb.spring.multi.repository.cluster1")
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.cluster1.uri}")
    private String connectionUri;

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applicationName("wfmt-POC")
                .retryWrites(true)
                .retryReads(true)
                .writeConcern(WriteConcern.MAJORITY)
                .readConcern(ReadConcern.MAJORITY)
                .applyConnectionString(new ConnectionString(connectionUri));
    }

}