.. _data-service:

============
Data Service
============

The data service provides information about data that the server is
publishing. See `Working with Data`_ for information about data
configuration.

List all data objects
~~~~~~~~~~~~~~~~~~~~~

::

    GET /data

Response
^^^^^^^^

::

    HTTP/1.0 200 OK
    Content-Type: application/json

    {
      "ne1": {
        "type": "workspace",
        "driver": "GeoPackage"
      },
      "states": {
        "type": "dataset",
        "driver": "GeoJSON"
      },
      "style": {
        "type": "style",
        "driver": "CartoCSS"
      },
      "va": {
        "type": "workspace",
        "driver": "GeoPackage"
      }
    }

Get a single data object
~~~~~~~~~~~~~~~~~~~~~~~~

::

    GET /data[/{workspace}]/{name}

Parameters
^^^^^^^^^^

-  **name**

*Required* ``string`` specifying the name of the data object.

-  **workspace**

*Optional* ``string`` specifying the name of the workspace containing
the data object.

Response
^^^^^^^^

::

    HTTP/1.0 200 OK
    Content-Type: application/json

    {
      "name": "states",
      "type": "vector",
      "driver": "GeoJSON",
      "bbox": [
        -124.731422,
        24.955967,
        -66.969849,
        49.371735
      ],
      "crs": [
        "+proj=longlat",
        "+datum=WGS84",
        "+no_defs"
      ],
      "count": 49,
      "schema": {
        "STATE_NAME": "String",
        "STATE_ABBR": "String",
        "PERSONS": "Double",
        "geometry": "Polygon"
      },
      "features": "/features/states.json"
    }

Tileset Object Response
'''''''''''''''''''''''

::

    {
      "name": "tiles",
      "type": "tile",
      "driver": "GeoPackage",
      "bbox": [
        -180.0,
        -90.0,
        180.0,
        90.0
      ],
      "crs": [
        "+proj=longlat",
        "+datum=WGS84",
        "+no_defs"
      ],
      "tilesize": [
        256,
        256
      ],
      "grids": [
        {
          "zoom": 0,
          "width": 2,
          "height": 1,
          "res": [
            0.703125,
            0.703125
          ]
        },
        {
          "zoom": 1,
          "width": 4,
          "height": 2,
          "res": [
            0.3515625,
            0.3515625
          ]
        }
      ],
      "tiles": "/tiles/ne1/tiles"
    }

Errors
'''''''''

No such object with specified name exists.

::

    HTTP/1.0 404 Not Found



