SPARQL Client webapp
====================

The sprotocol package comes from https://github.com/mischat/sprotocol. It has
been modified in SparqlProtocolClientUtils.java to send GET requests.

Look at src/uk/me/mmt/sprotocol/SparqlQueryProtocolClientExample.java for
API usage.

Deployment as Docker container
------------------------------

The application can be deployed as a Docker container. To do so you first build the image using Maven:

```
mvn -Pdocker clean install docker:push
```
Then you can deploy it with docker-compose using a YAML file derived from the one you see in the top directory.

