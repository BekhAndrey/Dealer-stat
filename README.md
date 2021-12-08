
# Dealer-stat
## The application allow traders to list their game objects and recieve comments from anonymous users.
## Comments can be created only by logged users.
## Game objects can be added only by traders, deleted and updated only by object owner.
## Each comment and game object has to be approved by the admin user.
## Each new user has to confirm their email by entering the verification code sent to it.
### Endpoints.
#### Login:
*  `POST: /registration` - create account;
*  `POST: /registration/confirm-email` - confirm email;
*  `POST: /auth/forgot-password` - reset user password;
### Comments:
*  `GET: /users/{traderId}/comments` - get comments for specific trader;
*  `GET: /users/{traderId}/comments/{id}` - view comment;
*  `POST : /users/{traderId}/comments/add` - add new comment;
*  `PUT: /users/{traderId}/comments/{id}/edit` - edit comment
*  `DELETE: /users/{traderId}/comments/{id}` - delete comment
### Games:
*  `GET: /games` - get all games
*  `POST: /games/add` - add new game
*  `PUT: /games/edit` - edit game
### Game Objects:
*  `GET: /objects` - get all objects
*  `GET: /objects/my` - get logged trader objects
*  `POST: /objects/add` - add new game object 
*  `PUT: /objects/{id}/edit` - edit object
*  `DELETE /objects/{id}` - delete object
### Admin options:
*  `GET: /admin/comments` - get not approved comments
*  `PUT: /admin/comments/{id}/approve` - approve selected comment
*  `GET: /admin/objects` - get not approved game objects
*  `PUT: /admin/objects/{id}/approve` - approve selected game object
