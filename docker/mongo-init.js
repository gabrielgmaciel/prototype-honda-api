// This script runs as the root user during MongoDB container initialization.
// It creates the 'honda' database, an application user 'appuser', and an example index
// on the users collection. Adjust user/password and indexes as needed.

// Switch to 'honda' database
var db = db.getSiblingDB('honda');

// Create collection (optional)
try {
    db.createCollection('users');
} catch (e) {
    // ignore if already exists
}

// Create an application user with privileges on the 'honda' database
db.createUser({
    user: 'appuser',
    pwd: 'senha123',
    roles: [{role: 'dbOwner', db: 'honda'}]
});

// Create a unique index on users.email
try {
    db.users.createIndex({email: 1}, {unique: true});
} catch (e) {
    // ignore index creation errors during init if any
    print('Index creation error: ' + e);
}

