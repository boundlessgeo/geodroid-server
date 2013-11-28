.. _feature-service:

Feature Service
===============

The feature service provides access to vector (feature) data.

Get a feature collection
------------------------

    GET /features/{workspace}/{name}[.{format}]

Parameters
..........

* **name**

  *Required* ``string`` specifying the name of the feature dataset.

* **workspace**

  *Required* ``string`` specifying the name of workspace containing the feature dataset.

* **format**

  *Optional* ``string`` specifying format of which to return feature dataset. Supported values include:

  * **json** - GeoJSON feature collection with ``Content-type: application/json``. This is the default.
  * **png** - Rendered PNG image with ``Content-type: image/png``.
  * **html** - OpenLayers map with ``Content-type: text/html``.

Query string
............

* **bbox**

   Four comma-separated ``float`` values in ``west,south,east,north`` order specifying spatial bounding box constraint. Example: ``-180,-90,180,90``. Values should be in the same crs as the feature data, unless the **srs** parameter is also specified in the request in which case the bbox values must be in the same crs.

* **srs**

  ``string`` specifying spatial reference system identifier to reproject (if necessary) feature data to.

* **filter**

  ``string`` specifying a CQL attribute filter to apply to resulting feature data.

* **limit**

  ``int`` specifying the maximum size of the resulting feature data.

* **offset**

   ``int`` specifying the offset into the resulting feature data from which to return features from.

* **style**

    ``string`` specifying the name of the style to apply to the resulting feature data. This is only used for png output.

Get a single feature
--------------------

    GET /features/{workspace}/{name}/{id}

Parameters
..........

* **id**

  *Required* ``string`` specifying the identifier of the feature

* **name**

  *Required* ``string`` specifying the name of the feature dataset.

* **workspace**

  *Required* ``string`` specifying the name of workspace containing the feature dataset.

Query string
............

* **srs**

  ``string`` specifying spatial reference system identifier to reproject (if necessary) feature data to.

Response
~~~~~~~~

    HTTP/1.0 200 OK
    Content-Type: application/json

Errors
~~~~~~

No such feature with specified id exists.

    HTTP/1.0 404 Not Found

Edit a single feature
---------------------

    PUT /features/{workspace}/{name}/{id}

* **id**

  *Required* ``string`` specifying the identifier of the feature

* **name**

  *Required* ``string`` specifying the name of the feature dataset.

* **workspace**

  *Required* ``string`` specifying the name of workspace containing the feature dataset.

Request Body
............

::

    {
      "type" : "Feature",
      "geometry" : {
        "type" : "Point",
        "coordinates" : [
          1.2,
          3.4
        ]
      },
      "properties" : {
        "NAMEASCII" : "Hagersville",
        "MIN_BBXMIN" : 42.42,
        "SCALERANK" : 42
      }
    }

Response
........

    HTTP/1.0 200 OK
    Content-Type: text/plain

Delete a single feature
-----------------------

    DELETE /features/{workspace}/{name}/{id}

* **id**

  *Required* ``string`` specifying the identifier of the feature

* **name**

  *Required* ``string`` specifying the name of the feature dataset.

* **workspace**

  *Required* ``string`` specifying the name of workspace containing the feature dataset.

Response
........

    HTTP/1.0 200 OK
    Content-Type: text/plain
