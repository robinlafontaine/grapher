# Grapher


### Description
This project is a simple graph viewer implementation in Java. It uses the Java Spring framework to serve a REST API and a Thymeleaf (server-side Java template engine) frontend to display the graph. 
The graph is stored in a PostgreSQL database using Spring Data JPA and is updated in real-time when the user uploads or deletes JSON formated graphs. 
The user can also search for the shortest path between two given nodes in the graph using Dijkstra's shortest path algorithm. 
The graph is displayed using the [cytoscape.js](https://github.com/cytoscape/cytoscape.js) library.

### Technologies
- Java (Spring Maven)
- HTML (Thymeleaf)
- JavaScript (cytoscape.js)
- PostgreSQL
- Spring Data JPA
- Docker

### How to run
1. Clone the repository.
2. Setup your environment variables (see Environment)
3. Run `docker-compose -f compose.yaml up -d`.
5. Go to 'http://localhost:8080/' in your browser to access the graph viewer.
6. There are example graphs to upload in '/examples'.

### Screenshots
![Screenshot](imgs/main.png)

### Environment
You can set them directly in the 'compose.yaml' environment or use a '.env' file. Whatever floats your boat :)\
Here are the following environment variables that you must set in order for the project to work :
```yaml
GRAPHER_PORT
POSTGRES_DB
POSTGRES_HOSTNAME
POSTGRES_PORT
POSTGRES_USER
POSTGRES_PASSWORD
```

### Remarks

- I am aware that this project contains many bugs and probably vulnerabilities. This is my first time working with Java Spring, so I am still learning.
- The project is a PoC and should not be used in production. I do not plan to maintain it.
- This was my first time working with Spring, Thymeleaf, and Spring Data JPA. I am still learning, and I am aware that the code is not perfect.
