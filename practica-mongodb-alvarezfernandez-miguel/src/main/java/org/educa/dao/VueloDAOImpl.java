package org.educa.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.educa.entity.VueloWithRelations;
import org.educa.settings.DatabaseSettings;
import org.educa.wrappers.BeneficioVuelo;

import java.util.ArrayList;
import java.util.List;

public class VueloDAOImpl implements VueloDAO {

    public static final String VUELOS = "vuelos";
    private final Gson gson = new GsonBuilder().create();
    @Override
    public List<BeneficioVuelo> getBeneficioVuelo() {

        VueloWithRelations vueloWithRelations;
        List<BeneficioVuelo> beneficioVueloList = new ArrayList<>();


        try(MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(VUELOS);
            List<Bson> bson = List.of(
                    Aggregates.lookup("aeropuertos","destino_id","_id","destino"),
                    Aggregates.unwind("$destino"),
                    Aggregates.lookup("aeropuertos","origen_id","_id","origen"),
                    Aggregates.unwind("$origen")
            );
            AggregateIterable<Document> aggregateIterable = mongoCollection.aggregate(bson);
            for(Document doc : aggregateIterable){
                vueloWithRelations = gson.fromJson(doc.toJson(),VueloWithRelations.class);
                BeneficioVuelo beneficioVuelo = new BeneficioVuelo();
                beneficioVuelo.setCodigoVuelo(vueloWithRelations.getCodigoVuelo());
                beneficioVuelo.setOrigen(vueloWithRelations.getOrigen().getNombre());
                beneficioVuelo.setDestino(vueloWithRelations.getDestino().getNombre());
                beneficioVuelo.setCoste(vueloWithRelations.getCoste());
                beneficioVuelo.setVueloId(vueloWithRelations.getId());
                beneficioVueloList.add(beneficioVuelo);
            }
        }
        return beneficioVueloList;
    }
}
