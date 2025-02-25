package org.educa.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;
import org.educa.settings.DatabaseSettings;
import org.educa.wrappers.InfoPasajero;
import org.educa.wrappers.VueloWithPrecio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;

public class ResevasDAOImpl implements ReservasDAO {
    private final Gson gson = new GsonBuilder().create();
    private static final String RESERVAS = "reservas";


    @Override
    public ReservaEntity findById(int id) {
        try(MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(RESERVAS);
            Document document = mongoCollection.find(eq("_id", id)).first();
            if (document != null) {
                return gson.fromJson(document.toJson(),ReservaEntity.class);
            } else {
                return null;
            }
        }
    }

    @Override
    public Long update(ReservaEntity reservaToUpdate) {
        try(MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(RESERVAS);
            Bson filter = eq("_id",reservaToUpdate.getId());
            List<Bson> update = List.of(
                    Updates.set("estado",reservaToUpdate.getEstado()),
                    Updates.set("asiento",reservaToUpdate.getAsiento()),
                    Updates.set("precio",reservaToUpdate.getPrecio())
            );
            UpdateResult updateResult = mongoCollection.updateMany(filter,update);
            return updateResult.getModifiedCount();
        }
    }

    @Override
    public Long delete(int id) {
        try(MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(RESERVAS);
            Bson filter = eq("_id",id);
            DeleteResult deleteResult = mongoCollection.deleteOne(filter);
            return deleteResult.getDeletedCount();
        }
    }

    @Override
    public Integer save(ReservaEntity reserva) {
        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(RESERVAS);
            Document doc = Document.parse(gson.toJson(reserva));
            InsertOneResult insertOneResult = mongoCollection.insertOne(doc);
            return Objects.requireNonNull(insertOneResult.getInsertedId()).asInt32().intValue();
        }
    }

    @Override
    public List<ReservaEntity> findAll() {
        List<ReservaEntity> lista = new ArrayList<>();
        try(MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(RESERVAS);
            FindIterable<Document> findIterable = mongoCollection.find();
            for (Document doc : findIterable) {
                lista.add(gson.fromJson(doc.toJson(), ReservaEntity.class));
            }
            return lista;
        }
    }

    @Override
    public List<ReservaWithRelations> findReservasByCantidad(BigDecimal cantidad) {
        List<ReservaWithRelations> lista = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(RESERVAS);
            List<Bson> pipeLine = List.of(
                    match(gte("precio",cantidad)),
                    lookup("pasajeros","pasajero_id","_id","pasajero"),
                    unwind("$pasajero"),
                    lookup("vuelos","vuelo_id","_id","vuelo"),
                    unwind("$vuelo")
            );

            AggregateIterable<Document> findIterable = mongoCollection.aggregate(pipeLine);
            for(Document doc : findIterable){
                lista.add(gson.fromJson(doc.toJson(),ReservaWithRelations.class));
            }
        }
        return lista;
    }

    @Override
    public InfoPasajero findReservasByPasaporte(String pasaporte) {
        InfoPasajero infoPasajero = new InfoPasajero();
        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> collection = mongoDatabase.getCollection(RESERVAS);

            List<Bson> bsonList = List.of(
                    lookup("pasajeros", "pasajero_id", "_id", "pasajeros"),
                    unwind("$pasajeros"),
                    match(eq("pasajeros.pasaporte", pasaporte)),
                    lookup("vuelos", "vuelo_id", "_id", "vuelos"),
                    unwind("$vuelos")
            );

            infoPasajero.setVuelos(new ArrayList<>());
            AggregateIterable<Document> aggregateIterable = collection.aggregate(bsonList);
            ReservaWithRelations reserva;

            for (Document document : aggregateIterable) {
                document.put("pasajero", document.get("pasajeros"));
                document.put("vuelo", document.get("vuelos"));

                reserva = gson.fromJson(document.toJson(), ReservaWithRelations.class);
                if (reserva != null) {
                    VueloWithPrecio vuelo = new VueloWithPrecio(reserva.getVuelo(),reserva.getPrecio(),reserva.getEstado());
                    infoPasajero.getVuelos().add(vuelo);
                    infoPasajero.setPasajero(reserva.getPasajero());
                }
            }
        }
        return infoPasajero;
    }

    @Override
    public List<ReservaEntity> findReservasByVueloId(Integer vueloId) {
        List<ReservaEntity> reservaEntities = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> collection = mongoDatabase.getCollection(RESERVAS);

            List<Bson> bson = List.of(
                    match(eq("vuelo_id", vueloId)),
                    unwind("$vuelo_id")
            );

            AggregateIterable<Document> aggregateIterable = collection.aggregate(bson);
            for (Document doc : aggregateIterable) {
                ReservaEntity reserva = gson.fromJson(doc.toJson(), ReservaEntity.class);
                reservaEntities.add(reserva);
            }
        }
        return reservaEntities;
    }
}
