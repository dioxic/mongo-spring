package org.mongodb.spring.multi.config;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "org.mongodb.spring.multi.repository.cluster1",
        mongoTemplateRef = Cluster1MongoConfig.MONGO_TEMPLATE
)
public class Cluster1MongoConfig extends AbstractMongoMultiClientConfiguration {
    public static final String PREFIX = "cluster1";
    public static final String CLIENT = PREFIX + "Client";
    public static final String MONGO_TEMPLATE = PREFIX + "MongoTemplate";
    public static final String GRIDFS_TEMPLATE = PREFIX + "GridFsTemplate";
    public static final String FACTORY = PREFIX + "Factory";
    public static final String PROPERTIES = PREFIX + "Properties";
    public static final String CONTEXT = PREFIX + "Context";
    public static final String CONVERTER = PREFIX + "Converter";
    public static final String CONVERSIONS = PREFIX + "Conversions";

    @Override
    @Bean(CLIENT)
    public MongoClient mongoClient() {
        return super.mongoClient();
    }

    @Bean(PROPERTIES)
    @ConfigurationProperties(prefix = "spring.data.mongodb.cluster1")
    public MongoProperties mongoProperties() {
        return new MongoProperties();
    }

    @Override
    @Bean(MONGO_TEMPLATE)
    public MongoTemplate mongoTemplate(@Qualifier(FACTORY) MongoDatabaseFactory databaseFactory,
                                       @Qualifier(CONVERTER) MappingMongoConverter converter) {
        return super.mongoTemplate(databaseFactory, converter);
    }

    @Override
    @Bean(FACTORY)
    public MongoDatabaseFactory mongoDbFactory() {
        return super.mongoDbFactory();
    }

    @Override
    @Bean(CONVERTER)
    public MappingMongoConverter mappingMongoConverter(@Qualifier(FACTORY) MongoDatabaseFactory databaseFactory,
                                                       @Qualifier(CONVERSIONS) MongoCustomConversions customConversions,
                                                       @Qualifier(CONTEXT) MongoMappingContext mappingContext) {
        return super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);
    }

    @Override
    @Bean(CONTEXT)
    public MongoMappingContext mongoMappingContext(MongoCustomConversions customConversions) throws ClassNotFoundException {
        return super.mongoMappingContext(customConversions);
    }

    @Override
    @Bean(CONVERSIONS)
    public MongoCustomConversions customConversions() {
        return super.customConversions();
    }

    @Override
    @Bean(GRIDFS_TEMPLATE)
    public GridFsTemplate gridFsTemplate(
            @Qualifier(FACTORY) MongoDatabaseFactory factory,
            @Qualifier(MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        return super.gridFsTemplate(factory, mongoTemplate);
    }
}
