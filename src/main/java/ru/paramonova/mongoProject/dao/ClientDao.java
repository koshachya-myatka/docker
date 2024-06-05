package ru.paramonova.mongoProject.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ru.paramonova.mongoProject.models.Client;
import ru.paramonova.mongoProject.models.ModelDB;

import java.util.List;
@Component
@RequiredArgsConstructor
public class ClientDao {
    @Setter
    private ModelDB modelDB;
    private final ModelMapper modelMapper;

    public List<Client> findAll() {
        return modelDB.getTemplate().findAll(Client.class, "clients");
    }

    public Client findById(Integer id) {
        return modelDB.getTemplate().findById(id, Client.class, "clients");
    }

    public List<Client> findByLogin(String login) {
        Query query = new Query();
        query.addCriteria(Criteria.where("login").regex(login.toLowerCase(), "i"));
        return modelDB.getTemplate().find(query, Client.class, "clients");
    }

    public Client save(Client client) {
        if (client.getId() == null) {
            MongoDatabase db =  modelDB.getTemplate().getDb();
            BasicDBObject command = new BasicDBObject();
            command.put("createUser", client.getLogin());
            command.put("pwd", client.getPassword());
            command.put("roles", new String[]{client.getRole()});
            db.runCommand(command);
            int number = (int) modelDB.getTemplate().getCollection("clients").countDocuments();
            client.setId(number + 1);
            modelDB.getTemplate().save(client, "clients");
        } else {
            // тут доделать update в случае чего
            if (modelDB.getTemplate().findById(client.getId(), Client.class, "clients") == null) {
                throw new IllegalArgumentException();
            }
            Client oldClient = modelMapper.map(client, Client.class);
            modelDB.getTemplate().save(oldClient, "clients");
        }
        return client;
    }

    public void deleteById(Integer id) {
        Client client = modelDB.getTemplate().findById(id, Client.class, "clients");
        if (client != null) {
            MongoDatabase db =  modelDB.getTemplate().getDb();
            BasicDBObject command = new BasicDBObject();
            command.put("dropUser", client.getLogin());
            db.runCommand(command);
            modelDB.getTemplate().remove(client, "clients");
        }
    }
}
