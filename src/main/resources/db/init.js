db = db.getSiblingDB("admin");
db.auth("rootuser", "rootpass");
db = db.getSiblingDB("project_db");

db.createCollection("clients");
db.createCollection("books");
db.createCollection("authors");
db.createCollection("orders");

db.clients.insertMany([
    {
        _id: 1,
        login: "client",
        password: "123",
        role: "CLIENT_ROLE",
    },
    {
        _id: 2,
        login: "librarian",
        password: "123",
        role: "LIBRARIAN_ROLE",
    },
    {
        _id: 3,
        login: "programmer",
        password: "123",
        role: "PROGRAMMER_ROLE",
    },
    {
        _id: 4,
        login: "test",
        password: "123",
        role: "LIBRARIAN_ROLE",
    },
]);

db.books.insertMany([
    {
        _id: 1,
        title: "Ведьмак. Последнее желание",
        countPage: 350,
        amount: 50,
        genre: "Фэнтези"
    },
    {
        _id: 2,
        title: "Шестерка воронов",
        countPage: 328,
        amount: 13,
        genre: "Подростковая литература"
    },
    {
        _id: 3,
        title: "Продажное королевство",
        countPage: 418,
        amount: 24,
        genre: "Роман"
    },
    {
        _id: 4,
        title: "Ведьмак. Башня ласточки",
        countPage: 352,
        amount: 123,
        genre: "Фэнтези"
    },
    {
        _id: 5,
        title: "Совиная башня",
        countPage: 790,
        amount: 45,
        genre: "Романтическое фэнтези"
    },
]);

db.authors.insertMany([
    {
        _id: 1,
        surname: "Сапковский",
        name: "Анджей",
        dateOfBorn: new Date("1948-06-21"),
        experience: 38
    },
    {
        _id: 2,
        surname: "Бардуго",
        name: "Ли",
        dateOfBorn: new Date("1975-04-06"),
        experience: 12
    },
    {
        _id: 3,
        surname: "Черкасова",
        name: "Ульяна",
        dateOfBorn: new Date("1989-01-15"),
        experience: 4
    },
]);

db.orders.insertMany([
    {
        _id: 1,
        dateOrder: new Date("2024-01-18"),
        idClient: 1,
        idBook: 2
    },
    {
        _id: 2,
        dateOrder: new Date("2024-03-18"),
        idClient: 2,
        idBook: 1
    },
    {
        _id: 3,
        dateOrder: new Date("2024-04-19"),
        idClient: 1,
        idBook: 4
    },
    {
        _id: 4,
        dateOrder: new Date("2024-04-26"),
        idClient: 1,
        idBook: 2
    },
    {
        _id: 5,
        dateOrder: new Date("2024-12-18"),
        idClient: 1,
        idBook: 4
    },
    {
        _id: 6,
        dateOrder: new Date("2024-12-18"),
        idClient: 1,
        idBook: 5
    },
    {
        _id: 7,
        dateOrder: new Date("2024-12-18"),
        idClient: 3,
        idBook: 2
    },
    {
        _id: 8,
        dateOrder: new Date("2024-12-18"),
        idClient: 3,
        idBook: 2
    },
    {
        _id: 9,
        dateOrder: new Date("2024-12-18"),
        idClient: 3,
        idBook: 4
    },
    {
        _id: 10,
        dateOrder: new Date("2024-12-30"),
        idClient: 1,
        idBook: 1
    },
    {
        _id: 11,
        dateOrder: new Date("2024-12-30"),
        idClient: 2,
        idBook: 4
    },
    {
        _id: 12,
        dateOrder: new Date("2024-12-30"),
        idClient: 2,
        idBook: 5
    },
]);

db.authors.createIndexes([{"surname" : 1}, {"surname" : 1, "name": 1}, {"surname" : 1, "dateOfBorn": 1}]);
db.books.createIndexes([{"title" : 1}, {"amount" : 1}, {"genre" : 1}, {"title" : 1, "amount" : 1}, {"title" : 1, "genre" : 1}, {"title" : 1, "amount" : 1, "genre" : 1}]);
db.clients.createIndexes([{"login" : 1, "password" : 1}]);
db.orders.createIndexes([{"dateOrder" : 1}, {"idClient" : 1}, {"idBook" : 1}, {"idClient" : 1, "dateOrder" : 1}]);

db = db.getSiblingDB("admin");

db.createRole(
    {
      role: "CLIENT_ROLE", 
      privileges: [
        {
          actions: [ "changeStream", "collStats", "dbHash", "dbStats", "find", "killCursors", "listCollections", "listIndexes", "listSearchIndexes"],
          resource: { db: "project_db", collection: "books" }
        },
        {
          actions: [ "changeStream", "collStats", "dbHash", "dbStats", "find", "killCursors", "listCollections", "listIndexes", "listSearchIndexes"],
          resource: { db: "project_db", collection: "authors" }
        }
      ],
      roles: []
    }
);

db.createRole(
    {
      role: "LIBRARIAN_ROLE", 
      privileges: [],
      roles: [
        {
            "role" : "readWrite",
            "db" : "project_db"
        }
      ]
    }
);

db.createRole(
    {
      role: "PROGRAMMER_ROLE", 
      privileges: [],
      roles: [
        {
            "role" : "dbOwner",
            "db" : "project_db"
        }
      ]
    }
);

db.createUser({"user" : "client",
    "pwd" : "123",
    roles : [
        {
        "role" : "CLIENT_ROLE",
        "db" : "project_db"
        }
    ]
});
 
db.createUser({"user" : "librarian",
    "pwd" : "123",
    roles : [
        {
        "role" : "LIBRARIAN_ROLE",
        "db" : "project_db"
        }
    ]
});

db.createUser({"user" : "programmer",
    "pwd" : "123",
    roles : [
        {
        "role" : "PROGRAMMER_ROLE",
        "db" : "project_db"
        }
    ]
});

db.createUser({"user" : "test",
    "pwd" : "123",
    roles : [
        {
        "role" : "CLIENT_ROLE",
        "db" : "project_db"
        }
    ]
});

exit;