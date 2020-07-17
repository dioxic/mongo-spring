package org.mongodb.spring.multi.config;

import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Objects;

@Configuration
public abstract class AbstractMongoMultiClientConfiguration extends AbstractMongoClientConfiguration {

    @Autowired
    private Environment environment;

    @Override
    protected MongoClient createMongoClient(MongoClientSettings settings) {
        MongoProperties mongoProperties = mongoProperties();
        return new MongoClientFactory(mongoProperties, environment, Collections.emptyList())
                .createMongoClient(mongoClientSettings());
    }

    @Override
    protected FieldNamingStrategy fieldNamingStrategy() {
        Class<?> strategyClass = mongoProperties().getFieldNamingStrategy();
        if (strategyClass != null) {
            return (FieldNamingStrategy) BeanUtils.instantiateClass(strategyClass);
        }
        return super.fieldNamingStrategy();
    }

    @Override
    protected boolean autoIndexCreation() {
        return Objects.requireNonNullElse(mongoProperties().isAutoIndexCreation(), false);
    }

    @Override
    protected String getDatabaseName() {
        return mongoProperties().getMongoClientDatabase();
    }

    @Override
    protected MongoClientSettings mongoClientSettings() {
        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        builder.uuidRepresentation(UuidRepresentation.STANDARD);
        configureClientSettings(builder);
        return builder.build();
    }

    protected abstract MongoProperties mongoProperties();

    public GridFsTemplate gridFsTemplate(MongoDatabaseFactory factory, MongoTemplate mongoTemplate) {
        return new GridFsTemplate(new GridFsMongoDatabaseFactory(factory, mongoProperties()),
                mongoTemplate.getConverter());
    }

    /**
     * {@link MongoDatabaseFactory} decorator to respect
     * {@link MongoProperties#getGridFsDatabase()} if set.
     */
    static class GridFsMongoDatabaseFactory implements MongoDatabaseFactory {

        private final MongoDatabaseFactory mongoDatabaseFactory;

        private final MongoProperties properties;

        GridFsMongoDatabaseFactory(MongoDatabaseFactory mongoDatabaseFactory, MongoProperties properties) {
            Assert.notNull(mongoDatabaseFactory, "MongoDatabaseFactory must not be null");
            Assert.notNull(properties, "Properties must not be null");
            this.mongoDatabaseFactory = mongoDatabaseFactory;
            this.properties = properties;
        }

        @Override
        public MongoDatabase getMongoDatabase() throws DataAccessException {
            String gridFsDatabase = this.properties.getGridFsDatabase();
            if (StringUtils.hasText(gridFsDatabase)) {
                return this.mongoDatabaseFactory.getMongoDatabase(gridFsDatabase);
            }
            return this.mongoDatabaseFactory.getMongoDatabase();
        }

        @Override
        public MongoDatabase getMongoDatabase(String dbName) throws DataAccessException {
            return this.mongoDatabaseFactory.getMongoDatabase(dbName);
        }

        @Override
        public PersistenceExceptionTranslator getExceptionTranslator() {
            return this.mongoDatabaseFactory.getExceptionTranslator();
        }

        @Override
        public ClientSession getSession(ClientSessionOptions options) {
            return this.mongoDatabaseFactory.getSession(options);
        }

        @Override
        public MongoDatabaseFactory withSession(ClientSession session) {
            return this.mongoDatabaseFactory.withSession(session);
        }
    }

}
